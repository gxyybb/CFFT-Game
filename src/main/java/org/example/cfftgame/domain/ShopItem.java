package org.example.cfftgame.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName shop_item
 */
@Data
@TableName(value ="shop_item")
public class ShopItem implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 物品ID
     */
    private Integer itemId;

    /**
     * 物品类型（蘑菇、肥料、皮肤、消耗品）
     */
    private ShopItemType itemType;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}