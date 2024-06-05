package org.example.cfftgame.domain;

import lombok.Getter;

@Getter
public enum ShopItemType {
    MUSHROOM_SEED("mushroom_seed"),
    FERTILIZER("fertilizer"),
    SKIN("skin");

    private final String type;

    ShopItemType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

    public static ShopItemType fromString(String type) {
        for (ShopItemType itemType : ShopItemType.values()) {
            if (itemType.getType().equalsIgnoreCase(type)) {
                return itemType;
            }
        }
        throw new IllegalArgumentException("Unknown item type: " + type);
    }
}
