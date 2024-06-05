package org.example.cfftgame.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.cfftgame.common.vo.ResultVO;
import org.example.cfftgame.domain.*;
import org.example.cfftgame.domain.vo.ShopItemVO;
import org.example.cfftgame.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopItemService shopItemService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerInventoryService playerInventoryService;
    @Autowired
    private ShopMushroomService shopMushroomService;
    private ShopFertilizerService shopFertilizerService;
    private ShopSkinService shopSkinService;

    // 服务端上架商品
    @PostMapping("/addItem")
    @CrossOrigin("*")
    public ResultVO addItem(@RequestBody ShopItem shopItem) {
        try {
            ShopItemType.fromString(shopItem.getItemType().toString()); // 验证 itemType
            boolean isSaved = shopItemService.save(shopItem);
            if (isSaved) {
                return ResultVO.success("商品上架成功");
            } else {
                return ResultVO.error("商品上架失败");
            }
        } catch (IllegalArgumentException e) {
            return ResultVO.error("无效的商品类型: " + shopItem.getItemType());
        }
    }

    // 用户购买商品
    @PostMapping("/buy")
    @CrossOrigin("*")
    public ResultVO buyItem(@RequestParam int playerId, @RequestParam int itemId) {
        // 获取商品信息
        ShopItem shopItem = shopItemService.getById(itemId);
        if (shopItem == null) {
            return ResultVO.error("商品未找到");
        }

        // 获取玩家信息
        Player player = playerService.getById(playerId);
        if (player == null) {
            return ResultVO.error("玩家未找到");
        }

        // 检查玩家金币是否足够
        if (player.getGold() < shopItem.getPrice()) {
            return ResultVO.error("金币不足，无法购买商品");
        }

        // 扣除玩家的金币
        player.setGold(player.getGold() - shopItem.getPrice());
        playerService.updateById(player);

        // 添加商品到玩家背包
        QueryWrapper<PlayerInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId).eq("item_id", itemId).eq("item_type", shopItem.getItemType().toString());
        PlayerInventory existingInventory = playerInventoryService.getOne(queryWrapper);

        if (existingInventory != null) {
            // 如果物品已存在，增加数量
            existingInventory.setQuantity(existingInventory.getQuantity() + 1);
            playerInventoryService.updateById(existingInventory);
        } else {
            // 如果物品不存在，添加新的记录
            PlayerInventory playerInventory = new PlayerInventory();
            playerInventory.setPlayerId(playerId);
            playerInventory.setItemId(itemId);
            playerInventory.setItemType(String.valueOf(shopItem.getItemType()));
            playerInventory.setQuantity(1);
            playerInventoryService.save(playerInventory);
        }

        return ResultVO.success("购买成功");
    }
    @GetMapping("/items")
    @CrossOrigin("*")
    public ResultVO getShopItems(@RequestParam String itemType) {
        switch (itemType.toLowerCase()) {
            case "mushroom_seed":
                List<ShopMushroom> mushrooms = shopMushroomService.list();
                return ResultVO.success(mushrooms);
            case "fertilizer":
                List<ShopFertilizer> fertilizers = shopFertilizerService.list();
                return ResultVO.success(fertilizers);
            case "skin":
                List<ShopSkin> skins = shopSkinService.list();
                return ResultVO.success(skins);
            default:
                return ResultVO.error("无效的商品类型");
        }
    }

}