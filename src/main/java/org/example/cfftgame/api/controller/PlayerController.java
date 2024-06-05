package org.example.cfftgame.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import jakarta.transaction.Transactional;
import org.example.cfftgame.common.vo.ResultVO;
import org.example.cfftgame.domain.*;
import org.example.cfftgame.domain.vo.PlayerInfoVO;
import org.example.cfftgame.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// REST 控制器，处理玩家相关的HTTP请求
@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private InventoryController inventoryController;
    @Autowired
    private LevelExperienceService levelExperienceService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private PlayerTaskService playerTaskService;

    @Autowired
    private PlotService plotService;
    private final TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

    // 设定延迟任务的方法


    // 分配随机任务方法


    // 获取玩家信息，根据玩家ID

    /**
     * 查询玩家信息及地块儿状态
     * @param playerId
     * @return
     */
    @GetMapping("/{playerId}")
    @CrossOrigin("*")
    public ResultVO getPlayerInfo(@PathVariable int playerId) {
        // 使用 QueryWrapper 查询玩家信息
        QueryWrapper<Player> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", playerId);
        Optional<Player> playerOpt = Optional.ofNullable(playerService.getOne(queryWrapper));

        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();

            // 查询下一级所需的经验值
            int nextLevel = player.getLevel() + 1;
            QueryWrapper<LevelExperience> levelQueryWrapper = new QueryWrapper<>();
            levelQueryWrapper.eq("level", nextLevel);
            LevelExperience nextLevelExperience = levelExperienceService.getOne(levelQueryWrapper);

            // 查询解锁的田块编号
            QueryWrapper<Plot> plotQueryWrapper = new QueryWrapper<>();
            plotQueryWrapper.eq("player_id", playerId).eq("is_unlocked", true);
            List<Plot> unlockedPlots = plotService.list(plotQueryWrapper);
            List<Integer> unlockedPlotNumbers = unlockedPlots.stream()
                    .map(Plot::getPlotNumber)
                    .collect(Collectors.toList());

            // 构建返回结果
            PlayerInfoVO playerInfoVO = new PlayerInfoVO();
            playerInfoVO.setPlayer(player);
            playerInfoVO.setNextLevelExperience(nextLevelExperience != null ? nextLevelExperience.getExperienceRequired() : null);
            playerInfoVO.setUnlockedPlots(unlockedPlotNumbers);

            return ResultVO.success(playerInfoVO);
        } else {
            return ResultVO.error("Player not found");
        }
    }

    /**
     * 升级获得奖励
     * @param playerId
     * @return
     */


    // 根据玩家经验值升级玩家
    @PostMapping("/{playerId}/upgrade")
    @Transactional
    @CrossOrigin("*")
    public ResultVO upgradePlayer(@PathVariable int playerId) {
        // 使用 QueryWrapper 查询玩家信息
        QueryWrapper<Player> playerQueryWrapper = new QueryWrapper<>();
        playerQueryWrapper.eq("id", playerId);
        Player player = playerService.getOne(playerQueryWrapper);

        if (player != null) {
            // 查询下一级的经验要求
            int nextLevel = player.getLevel() + 1;
            QueryWrapper<LevelExperience> levelQueryWrapper = new QueryWrapper<>();
            levelQueryWrapper.eq("level", nextLevel);
            LevelExperience nextLevelExperience = levelExperienceService.getOne(levelQueryWrapper);

            if (nextLevelExperience != null && player.getExperience() >= nextLevelExperience.getExperienceRequired()) {
                // 升级玩家等级
                player.setLevel(nextLevel);
                // 扣除升级所需的经验值
                player.setExperience(player.getExperience() - nextLevelExperience.getExperienceRequired());
                // 奖励金币
                player.setGold(player.getGold() + nextLevelExperience.getRewardGold());
                // 更新玩家信息到数据库
                boolean isUpdated = playerService.updateById(player);

                if (player.getExperience()>=0&&isUpdated) {
                    // 处理奖励道具
                    String rewardItemType = nextLevelExperience.getRewardItemType();

                    if (rewardItemType != null ) {
                        try {
                            inventoryController.addRewardToPlayerInventory(playerId, rewardItemType);
                            // 将奖励道具添加到玩家的背包中
                        } catch (Exception e) {
                            // 回滚事务
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultVO.error("Failed to add reward to inventory");
                        }
                    }
                    return ResultVO.success("Player upgraded successfully", player);
                } else {
                    // 回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultVO.error("Failed to update player");
                }
            } else {
                return ResultVO.error("Not enough experience to upgrade");
            }
        }
        return ResultVO.error("Upgrade failed");
    }

    /**
     * 获取任务列表
     * @param playerId
     * @return
     */




    // 获取玩家任务列表，根据玩家ID
    @GetMapping("/{playerId}/tasks")
    @CrossOrigin("*")
    public ResultVO getPlayerTasks(@PathVariable int playerId) {
        // 使用 QueryWrapper 查询玩家任务
        QueryWrapper<PlayerTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId)
                .eq("status","未完成");
        List<PlayerTask> tasks = playerTaskService.list(queryWrapper);
        return ResultVO.success(tasks);
    }

    /**
     * 获得完成任务奖励
     * @param playerId
     * @param taskId
     * @return
     */
    // 完成任务
    @PostMapping("/{playerId}/task/{taskId}/claim")
    @CrossOrigin("*")
    public ResultVO claimTaskReward(@PathVariable int playerId, @PathVariable int taskId) {
        // 使用 QueryWrapper 查询玩家任务
        QueryWrapper<PlayerTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId).eq("task_id", taskId);
        PlayerTask playerTask = playerTaskService.getOne(queryWrapper);

        if (playerTask != null && "已完成".equals(playerTask.getStatus())) {
            playerTask.setStatus("已领取");
            boolean isUpdated = playerTaskService.updateById(playerTask);

            if (isUpdated) {
                // 获取任务奖励
                Task task = taskService.getById(taskId);
                Player player = playerService.getById(playerId);
                player.setExperience(player.getExperience() + task.getRewardExperience());
                player.setGold(player.getGold() + task.getRewardGold());
                playerService.updateById(player);

                // 设定延迟任务，在5分钟后分配新任务
                scheduleNewTaskAssignment(playerId);

                return ResultVO.success("奖励领取成功");
            }
        }
        return ResultVO.error("任务未完成或奖励已领取");
    }

    private void scheduleNewTaskAssignment(int playerId) {
        taskScheduler.schedule(() -> assignNewTask(playerId), new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5))); // 5分钟后执行
    }

    private void assignNewTask(int playerId) {
        // 随机获取一个新的任务
        List<Task> tasks = taskService.list();
        Random random = new Random();
        Task newTask = tasks.get(random.nextInt(tasks.size()));

        // 创建新的玩家任务记录
        PlayerTask playerTask = new PlayerTask();
        playerTask.setPlayerId(playerId);
        playerTask.setTaskId(newTask.getId());
        playerTask.setStatus("未完成");
        playerTaskService.save(playerTask);
    }



    /**
     * 放弃任务
     * @param playerId
     * @param taskId
     * @return
     */
    @PostMapping("/{playerId}/task/{taskId}/abandon")
    @CrossOrigin("*")
    public ResultVO abandonTask(@PathVariable int playerId, @PathVariable int taskId) {
        // 使用 QueryWrapper 查询玩家任务
        QueryWrapper<PlayerTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId).eq("task_id", taskId);
        PlayerTask playerTask = playerTaskService.getOne(queryWrapper);

        if (playerTask != null && "未完成".equals(playerTask.getStatus())) {
            playerTask.setStatus("放弃");
            boolean isUpdated = playerTaskService.updateById(playerTask);

            if (isUpdated) {
                return ResultVO.success("successfully");
            }
        }
        return ResultVO.error("failed");
    }

}
