package org.example.cfftgame.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cfftgame.common.vo.ResultVO;
import org.example.cfftgame.domain.*;
import org.example.cfftgame.service.MushroomService;
import org.example.cfftgame.service.PlayerInventoryService;
import org.example.cfftgame.service.PlayerService;
import org.example.cfftgame.service.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Inventory")
public class InventoryController {

    @Autowired
    private PlayerInventoryService playerInventoryService;
    @Autowired
    private ShopItemService shopItemService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MushroomService mushroomService;

    // 给玩家背包添加物品

    /**
     * 给玩家背包添加物品
     * @param playerId
     * @param rewardItemTypeJson
     */
    @PostMapping("/add")
    public void addRewardToPlayerInventory(int playerId, String rewardItemTypeJson) {
        // 使用 Jackson 将 JSON 转换为 RewardItem 列表
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<LevelExperience.RewardItem> rewardItems = objectMapper.readValue(rewardItemTypeJson, objectMapper.getTypeFactory().constructCollectionType(List.class, LevelExperience.RewardItem.class));

            // 遍历奖励道具并添加到玩家背包
            for (LevelExperience.RewardItem rewardItem : rewardItems) {
                // 检查物品是否已经存在于玩家背包中
                QueryWrapper<PlayerInventory> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("player_id", playerId).eq("item_id", rewardItem.getId()).eq("item_type", rewardItem.getType());
                PlayerInventory existingInventory = playerInventoryService.getOne(queryWrapper);

                if (existingInventory != null) {
                    // 如果物品已存在，增加数量
                    existingInventory.setQuantity(existingInventory.getQuantity() + rewardItem.getNumber());
                    playerInventoryService.updateById(existingInventory);
                } else {
                    // 如果物品不存在，添加新的记录
                    PlayerInventory playerInventory = new PlayerInventory();
                    playerInventory.setPlayerId(playerId);
                    playerInventory.setItemId(rewardItem.getId());
                    playerInventory.setItemType(rewardItem.getType());
                    playerInventory.setQuantity(rewardItem.getNumber());
                    playerInventoryService.save(playerInventory);
                }
            }
        } catch (JsonProcessingException e) {
            // 处理 JSON 解析异常
            e.printStackTrace();
            // 你可以选择在这里记录日志或进一步处理异常
        }
    }
    // 获取背包中指定类型的物品信息

    /**
     * 查询背包
     * @param playerId
     * @param category
     * @return
     */
    @GetMapping("/info/{playerId}")
    public ResultVO getMushroomInfo(@PathVariable int playerId, @RequestParam("category") String category) {
        // 验证 category 是否符合预期的 ENUM 类型值
        if (!Category.isValid(category)) {
            return ResultVO.error("Invalid category");
        }

        // 查询玩家背包中指定类型的物品
        QueryWrapper<PlayerInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId).eq("item_type", category);
        List<PlayerInventory> items = playerInventoryService.list(queryWrapper);

        if (items.isEmpty()) {
            return ResultVO.error("No items found in inventory for the specified category");
        }

        return ResultVO.success(items);
    }

    /**
     * 卖出背包中的物品
     * @param playerId
     * @param id
     * @param quantity
     * @return
     */
    @PostMapping("/sell")
    public ResultVO sellItem(@RequestParam int playerId, @RequestParam int id, @RequestParam int quantity) {
        // 检查物品是否存在于玩家背包中
        QueryWrapper<PlayerInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId).eq("id", id);
        PlayerInventory existingInventory = playerInventoryService.getOne(queryWrapper);

        if (existingInventory == null || existingInventory.getQuantity() < quantity) {
            return ResultVO.error("Item not found or insufficient quantity in inventory");
        }

        // 皮肤类型物品不可以卖
        if ("skin".equalsIgnoreCase(existingInventory.getItemType())) {
            return ResultVO.error("Skins cannot be sold");
        }

        int sellPrice;
        if ("mushroom".equalsIgnoreCase(existingInventory.getItemType())) {
            // 获取成熟蘑菇的价格
            QueryWrapper<Mushroom> mushroomQueryWrapper = new QueryWrapper<>();
            mushroomQueryWrapper.eq("id", existingInventory.getItemId());
            Mushroom mushroom = mushroomService.getOne(mushroomQueryWrapper);

            if (mushroom == null) {
                return ResultVO.error("Mature mushroom not found");
            }

            // 计算卖出价格
            sellPrice = mushroom.getSellPrice() * quantity;
        } else {
            // 获取商店中该物品的价格
            QueryWrapper<ShopItem> shopQueryWrapper = new QueryWrapper<>();
            shopQueryWrapper.eq("id", existingInventory.getItemId()).eq("item_type", existingInventory.getItemType());
            ShopItem shopItem = shopItemService.getOne(shopQueryWrapper);

            if (shopItem == null) {
                return ResultVO.error("Shop item not found");
            }

            // 计算卖出价格，取价格的70%向下取整
            sellPrice = (int) Math.floor(shopItem.getPrice() * 0.7) * quantity;
        }

        // 更新玩家背包，减少相应物品的数量
        if (existingInventory.getQuantity() == quantity) {
            // 如果卖出数量等于现有数量，删除该记录
            playerInventoryService.removeById(existingInventory.getId());
        } else {
            // 否则，减少数量
            existingInventory.setQuantity(existingInventory.getQuantity() - quantity);
            playerInventoryService.updateById(existingInventory);
        }

        // 增加玩家的金币数量
        Player player = playerService.getById(playerId);
        if (player != null) {
            player.setGold(player.getGold() + sellPrice);
            playerService.updateById(player);
            return ResultVO.success("Item sold successfully, received " + sellPrice + " gold");
        } else {
            return ResultVO.error("Player not found");
        }
    }

}
