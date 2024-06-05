package org.example.cfftgame.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.example.cfftgame.domain.Player;
import org.example.cfftgame.mapper.PlayerMapper;
import org.example.cfftgame.service.PlayerService;
import org.springframework.stereotype.Service;

/**
* @author 14847
* @description 针对表【player】的数据库操作Service实现
* @createDate 2024-06-05 09:32:19
*/
@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player>
    implements PlayerService {

}



