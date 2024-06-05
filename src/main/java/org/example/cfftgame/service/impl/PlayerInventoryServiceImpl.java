package org.example.cfftgame.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.example.cfftgame.domain.PlayerInventory;
import org.example.cfftgame.mapper.PlayerInventoryMapper;
import org.example.cfftgame.service.PlayerInventoryService;
import org.springframework.stereotype.Service;

/**
* @author 14847
* @description 针对表【player_inventory】的数据库操作Service实现
* @createDate 2024-06-05 09:32:19
*/
@Service
public class PlayerInventoryServiceImpl extends ServiceImpl<PlayerInventoryMapper, PlayerInventory>
    implements PlayerInventoryService {

}




