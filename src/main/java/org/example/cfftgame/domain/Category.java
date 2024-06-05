package org.example.cfftgame.domain;

import lombok.Getter;

@Getter
public enum Category {
    MUSHROOM_SEED("mushroom_seed"),
    FERTILIZER("fertilizer"),
    MUSHROOM("mushroom"),
    SKIN("skin");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public static boolean isValid(String value) {
        for (Category category : Category.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
