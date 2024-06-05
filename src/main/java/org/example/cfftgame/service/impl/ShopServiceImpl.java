package org.example.cfftgame.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.cfftgame.domain.vo.ShopItemVO;
import org.example.cfftgame.mapper.ShopMapper;
import org.example.cfftgame.service.ShopService;
import org.springframework.stereotype.Service;

// 商店服务实现类，实现商店相关的操作
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, ShopItemVO> implements ShopService {
}
