package org.example.cfftgame.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName player_inventory
 */
@TableName(value ="player_inventory")
@Data
public class PlayerInventory implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer playerId;

    /**
     * 物品ID
     */
    private Integer itemId;

    /**
     * 物品类型（蘑菇、肥料、皮肤、消耗品）
     */
    private String itemType;

    /**
     * 数量
     */
    private Integer quantity;


}