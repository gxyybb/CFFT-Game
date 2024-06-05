package org.example.cfftgame.domain.vo;

import lombok.Data;

@Data
public class ShopItemVO {
    private int id;
    private String name;
    private String itemType;
    private int price;
    private Integer growthTime;
    private Integer minHarvestYield;
    private Integer maxHarvestYield;
    private Integer effect;
    private String imageUrl;
    private int unlockLevel;
}
