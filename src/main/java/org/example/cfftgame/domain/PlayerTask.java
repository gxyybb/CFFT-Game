package org.example.cfftgame.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName player_task
 */
@TableName(value ="player_task")
public class PlayerTask implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer playerId;

    /**
     * 
     */
    private Integer taskId;

    /**
     * 任务状态（未完成、已完成）
     */
    private String status;

    /**
     * 
     */
    private Date completedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public Integer getPlayerId() {
        return playerId;
    }

    /**
     * 
     */
    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    /**
     * 
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * 
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * 任务状态（未完成、已完成）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 任务状态（未完成、已完成）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     */
    public Date getCompletedAt() {
        return completedAt;
    }

    /**
     * 
     */
    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PlayerTask other = (PlayerTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlayerId() == null ? other.getPlayerId() == null : this.getPlayerId().equals(other.getPlayerId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCompletedAt() == null ? other.getCompletedAt() == null : this.getCompletedAt().equals(other.getCompletedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlayerId() == null) ? 0 : getPlayerId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCompletedAt() == null) ? 0 : getCompletedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", playerId=").append(playerId);
        sb.append(", taskId=").append(taskId);
        sb.append(", status=").append(status);
        sb.append(", completedAt=").append(completedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}