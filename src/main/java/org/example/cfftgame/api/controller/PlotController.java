package org.example.cfftgame.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.cfftgame.common.vo.ResultVO;
import org.example.cfftgame.domain.*;
import org.example.cfftgame.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/plot")
public class PlotController {

    @Autowired
    private PlotService plotService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerInventoryService playerInventoryService;

    @Autowired
    private ShopMushroomService shopMushroomService;

    @Autowired
    private FarmMushroomService farmMushroomService;

    @Autowired
    private FertilizerService fertilizerService;

    /**
     * 买地
     * @param playerId
     * @param plotNumber
     * @return
     */
    // 购买解锁地块
    @PostMapping("/unlock")
    @CrossOrigin("*")
    public ResultVO unlockPlot(@RequestParam int playerId, @RequestParam int plotNumber) {
        // 检查该地块是否已经被解锁
        QueryWrapper<Plot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId).eq("plot_number", plotNumber);
        Optional<Plot> plotOpt = Optional.ofNullable(plotService.getOne(queryWrapper));

        if (plotOpt.isPresent() && plotOpt.get().getIsUnlocked() == 1) {
            return ResultVO.error("田块已经解锁");
        }

        Plot plot = plotOpt.orElse(new Plot());
        // 获取解锁价格
        int unlockPrice = plot.getPrice();

        // 检查玩家金币是否足够
        Optional<Player> playerOpt = Optional.ofNullable(playerService.getById(playerId));
        if (!playerOpt.isPresent()) {
            return ResultVO.error("玩家未找到");
        }
        Player player = playerOpt.get();
        if (player.getGold() < unlockPrice) {
            return ResultVO.error("金币不足，无法解锁田块");
        }

        // 扣除玩家的金币
        player.setGold(player.getGold() - unlockPrice);
        playerService.updateById(player);

        // 更新地块解锁状态
        plot.setIsUnlocked(1);
        plotService.updateById(plot);

        return ResultVO.success("田块解锁成功");
    }

    /**
     * 种植
     * @param playerId
     * @param plotNumber
     * @param inventoryId
     * @return
     */
    // 种植蘑菇
    @PostMapping("/plant")
    @CrossOrigin("*")
    public ResultVO plantMushroom(@RequestParam int playerId, @RequestParam int plotNumber, @RequestParam int inventoryId) {
        // 检查地块是否已解锁
        QueryWrapper<Plot> plotQueryWrapper = new QueryWrapper<>();
        plotQueryWrapper.eq("player_id", playerId).eq("plot_number", plotNumber);
        Optional<Plot> plotOpt = Optional.ofNullable(plotService.getOne(plotQueryWrapper));

        if (!plotOpt.isPresent() || plotOpt.get().getIsUnlocked() == 0) {
            return ResultVO.error("田块未解锁");
        }

        // 检查玩家背包中是否有足够的蘑菇种子
        Optional<PlayerInventory> inventoryOpt = Optional.ofNullable(playerInventoryService.getById(inventoryId));
        if (!inventoryOpt.isPresent() || inventoryOpt.get().getQuantity() < 1 || !"mushroom_seed".equals(inventoryOpt.get().getItemType())) {
            return ResultVO.error("背包中没有足够的蘑菇种子");
        }

        PlayerInventory inventory = inventoryOpt.get();
        // 获取蘑菇种子的详细信息
        Optional<ShopMushroom> shopMushroomOpt = Optional.ofNullable(shopMushroomService.getById(inventory.getItemId()));
        if (!shopMushroomOpt.isPresent()) {
            return ResultVO.error("商店中未找到该蘑菇种子");
        }

        ShopMushroom shopMushroom = shopMushroomOpt.get();
        // 减少玩家背包中的蘑菇种子数量
        inventory.setQuantity(inventory.getQuantity() - 1);
        playerInventoryService.updateById(inventory);

        // 保存种植信息
        FarmMushroom farmMushroom = new FarmMushroom();
        farmMushroom.setPlotId(plotOpt.get().getId());
        farmMushroom.setMushroomId(inventory.getItemId());
        farmMushroom.setPlantedAt(new Timestamp(System.currentTimeMillis()));
        farmMushroom.setGrowthTime(shopMushroom.getGrowthTime());
        farmMushroomService.save(farmMushroom);

        return ResultVO.success("种植成功");
    }

    /**
     * 浇水
     * @param playerId
     * @param farmMushroomId
     * @return
     */
    @PostMapping("/water")
    @CrossOrigin("*")
    public ResultVO waterMushroom(@RequestParam int playerId, @RequestParam int farmMushroomId) {
        // 获取农场蘑菇信息
        Optional<FarmMushroom> farmMushroomOpt = Optional.ofNullable(farmMushroomService.getById(farmMushroomId));
        if (!farmMushroomOpt.isPresent() || farmMushroomOpt.get().getStatus() != 1) {
            return ResultVO.error("蘑菇不需要浇水");
        }

        // 更新蘑菇状态
        FarmMushroom farmMushroom = farmMushroomOpt.get();
        farmMushroom.setStatus(0); // 将状态从1变为0
        farmMushroomService.updateById(farmMushroom);

        return ResultVO.success("浇水成功");
    }

    /**
     * 杀虫
     * @param playerId
     * @param farmMushroomId
     * @return
     */
    @PostMapping("/pesticide")
    @CrossOrigin("*")
    public ResultVO pesticideMushroom(@RequestParam int playerId, @RequestParam int farmMushroomId) {
        // 获取农场蘑菇信息
        Optional<FarmMushroom> farmMushroomOpt = Optional.ofNullable(farmMushroomService.getById(farmMushroomId));
        if (!farmMushroomOpt.isPresent() || farmMushroomOpt.get().getStatus() != 2) {
            return ResultVO.error("蘑菇不需要杀虫");
        }

        // 检查玩家背包中是否有杀虫剂
        QueryWrapper<PlayerInventory> inventoryQueryWrapper = new QueryWrapper<>();
        inventoryQueryWrapper.eq("player_id", playerId).eq("item_type", "pesticide");
        Optional<PlayerInventory> inventoryOpt = Optional.ofNullable(playerInventoryService.getOne(inventoryQueryWrapper));

        if (!inventoryOpt.isPresent() || inventoryOpt.get().getQuantity() < 1) {
            return ResultVO.error("背包中没有杀虫剂");
        }

        // 更新蘑菇状态
        FarmMushroom farmMushroom = farmMushroomOpt.get();
        farmMushroom.setStatus(0); // 将状态从2变为0
        farmMushroomService.updateById(farmMushroom);

        // 减少玩家背包中的杀虫剂数量
        PlayerInventory inventory = inventoryOpt.get();
        inventory.setQuantity(inventory.getQuantity() - 1);
        playerInventoryService.updateById(inventory);

        return ResultVO.success("杀虫成功");
    }

    /**
     * 施肥
     * @param farmMushroomId
     * @param fertilizerId
     * @return
     */
    @PostMapping("/fertilize")
    @CrossOrigin("*")
    public ResultVO fertilizeMushroom(@RequestParam int farmMushroomId, @RequestParam int fertilizerId) {
        // 获取农场蘑菇信息
        Optional<FarmMushroom> farmMushroomOpt = Optional.ofNullable(farmMushroomService.getById(farmMushroomId));
        if (!farmMushroomOpt.isPresent()) {
            return ResultVO.error("未找到蘑菇");
        }

        // 检查玩家背包中是否有肥料
        Optional<PlayerInventory> inventoryOpt = Optional.ofNullable(playerInventoryService.getById(fertilizerId));
        if (!inventoryOpt.isPresent() || !"fertilizer".equals(inventoryOpt.get().getItemType()) || inventoryOpt.get().getQuantity() < 1) {
            return ResultVO.error("背包中没有肥料");
        }

        // 获取肥料的详细信息
        Optional<Fertilizer> fertilizerOpt = Optional.ofNullable(fertilizerService.getById(inventoryOpt.get().getItemId()));
        if (!fertilizerOpt.isPresent()) {
            return ResultVO.error("未找到该肥料");
        }

        Fertilizer fertilizer = fertilizerOpt.get();
        // 更新蘑菇的生长时间
        FarmMushroom farmMushroom = farmMushroomOpt.get();
        farmMushroom.setGrowthTime(farmMushroom.getGrowthTime() - fertilizer.getEffect());
        farmMushroomService.updateById(farmMushroom);

        // 减少玩家背包中的肥料数量
        PlayerInventory inventory = inventoryOpt.get();
        inventory.setQuantity(inventory.getQuantity() - 1);
        playerInventoryService.updateById(inventory);

        return ResultVO.success("施肥成功");
    }
    /**
     * 收获蘑菇
     * @param playerId
     * @param farmMushroomId
     * @return
     */
    @PostMapping("/harvest")
    @CrossOrigin("*")
    public ResultVO harvestMushroom(@RequestParam int playerId, @RequestParam int farmMushroomId) {
        // 获取农场蘑菇信息
        Optional<FarmMushroom> farmMushroomOpt = Optional.ofNullable(farmMushroomService.getById(farmMushroomId));
        if (!farmMushroomOpt.isPresent()) {
            return ResultVO.error("未找到蘑菇");
        }

        FarmMushroom farmMushroom = farmMushroomOpt.get();

        // 检查蘑菇是否已成熟
        if (farmMushroom.getGrowthTime() > 0) {
            return ResultVO.error("蘑菇尚未成熟");
        }

        // 获取玩家信息
        Optional<Player> playerOpt = Optional.ofNullable(playerService.getById(playerId));
        if (!playerOpt.isPresent()) {
            return ResultVO.error("未找到玩家");
        }

        Player player = playerOpt.get();

        // 获取蘑菇详细信息
        Optional<ShopMushroom> shopMushroomOpt = Optional.ofNullable(shopMushroomService.getById(farmMushroom.getMushroomId()));
        if (!shopMushroomOpt.isPresent()) {
            return ResultVO.error("商店中未找到该蘑菇种类");
        }

        ShopMushroom shopMushroom = shopMushroomOpt.get();

        // 计算随机收获数量
        Random random = new Random();
        int harvestQuantity = random.nextInt(shopMushroom.getMaxHarvestYield() - shopMushroom.getMinHarvestYield() + 1)
                + shopMushroom.getMinHarvestYield();

        // 增加玩家背包中的成熟蘑菇数量
        QueryWrapper<PlayerInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId).eq("item_id", shopMushroom.getId()).eq("item_type", Category.MUSHROOM);
        PlayerInventory existingInventory = playerInventoryService.getOne(queryWrapper);

        if (existingInventory != null) {
            // 如果物品已存在，增加数量
            existingInventory.setQuantity(existingInventory.getQuantity() + harvestQuantity);
            playerInventoryService.updateById(existingInventory);
        } else {
            // 如果物品不存在，添加新的记录
            PlayerInventory playerInventory = new PlayerInventory();
            playerInventory.setPlayerId(playerId);
            playerInventory.setItemId(shopMushroom.getId());
            playerInventory.setItemType(String.valueOf(Category.MUSHROOM));
            playerInventory.setQuantity(harvestQuantity);
            playerInventoryService.save(playerInventory);
        }

        // 增加玩家的经验值
        player.setExperience(player.getExperience() + shopMushroom.getExperienceValue());
        playerService.updateById(player);

        // 删除农场中的已收获蘑菇记录
        farmMushroomService.removeById(farmMushroomId);

        return ResultVO.success("收获成功，收获数量：" + harvestQuantity);
    }
    /**
     * 锄地
     * @param playerId
     * @param farmMushroomId
     * @return
     */
    @PostMapping("/remove")
    @CrossOrigin("*")
    public ResultVO removeMushroom(@RequestParam int playerId, @RequestParam int farmMushroomId) {
        // 获取农场蘑菇信息
        Optional<FarmMushroom> farmMushroomOpt = Optional.ofNullable(farmMushroomService.getById(farmMushroomId));
        if (!farmMushroomOpt.isPresent()) {
            return ResultVO.error("未找到蘑菇");
        }

        // 删除农场中的蘑菇记录
        farmMushroomService.removeById(farmMushroomId);

        return ResultVO.success("蘑菇已被移除");
    }


}
