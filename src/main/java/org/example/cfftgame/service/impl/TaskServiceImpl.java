package org.example.cfftgame.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.example.cfftgame.domain.Task;
import org.example.cfftgame.mapper.TaskMapper;
import org.example.cfftgame.service.TaskService;
import org.springframework.stereotype.Service;

/**
* @author 14847
* @description 针对表【task】的数据库操作Service实现
* @createDate 2024-06-05 09:32:19
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService {

}




