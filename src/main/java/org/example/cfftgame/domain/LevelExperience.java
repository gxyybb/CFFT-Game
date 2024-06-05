package org.example.cfftgame.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName level_experience
 */
@TableName(value ="level_experience")
@Data
public class LevelExperience implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer level;

    /**
     * 升级所需经验
     */
    private Integer experienceRequired;

    /**
     * 升级奖励金币
     */
    private Integer rewardGold;

    /**
     * 升级奖励道具类型（蘑菇种子、肥料、杀虫剂）
     */
    private String rewardItemType;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RewardItem {
        private String type;
        private int id;
        private int number;
    }

    // 获取奖励信息列表
    public List<RewardItem> getRewardItems() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(rewardItemType, objectMapper.getTypeFactory().constructCollectionType(List.class, RewardItem.class));
    }

    // 设置奖励信息列表
    public void setRewardItems(List<RewardItem> rewardItems) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.rewardItemType = objectMapper.writeValueAsString(rewardItems);
    }
}