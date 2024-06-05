package org.example.cfftgame.domain;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName plot
 */
@TableName(value ="plot")
@Data
public class Plot implements Serializable {
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
     * 田块编号
     */
    private Integer plotNumber;

    /**
     * 解锁价格
     */
    private Integer price;

    /**
     * 是否已解锁
     */
    private Integer isUnlocked;


}