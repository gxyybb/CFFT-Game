package org.example.cfftgame.domain.vo;



import lombok.Data;
import org.example.cfftgame.domain.FarmMushroom;
import org.example.cfftgame.domain.Player;

import java.util.List;

@Data
public class PlayerInfoVO {
    private Player player;
    private Integer nextLevelExperience;
    private List<Integer> unlockedPlots;
    private List<FarmMushroom> farmMushrooms;  // 添加种植的蘑菇信息
}
