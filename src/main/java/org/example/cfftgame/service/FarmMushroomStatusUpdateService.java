package org.example.cfftgame.service;

import org.example.cfftgame.domain.FarmMushroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class FarmMushroomStatusUpdateService {

    @Autowired
    private FarmMushroomService farmMushroomService;

    private final Random random = new Random();

    @Scheduled(fixedRate = 7200000) // 每2小时执行一次（7200000毫秒）
    public void updateFarmMushroomStatus() {
        // 查询所有farm_mushroom记录
        List<FarmMushroom> farmMushrooms = farmMushroomService.list();

        for (FarmMushroom farmMushroom : farmMushrooms) {
            // 随机设置status为0（正常）、1（需要浇水）、2（需要杀虫）
            int newStatus = random.nextInt(3); // 生成0, 1, 或 2
            farmMushroom.setStatus(newStatus);
            farmMushroomService.updateById(farmMushroom);
        }
    }
}
