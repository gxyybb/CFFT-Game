package org.example.cfftgame.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.example.cfftgame.domain.Transaction;
import org.example.cfftgame.mapper.TransactionMapper;
import org.example.cfftgame.service.TransactionService;
import org.springframework.stereotype.Service;

/**
* @author 14847
* @description 针对表【transaction】的数据库操作Service实现
* @createDate 2024-06-05 09:32:19
*/
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction>
    implements TransactionService {

}




