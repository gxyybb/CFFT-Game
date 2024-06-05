package org.example.cfftgame.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName task
 */
@TableName(value ="task")
public class Task implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 奖励经验值
     */
    private Integer rewardExperience;

    /**
     * 奖励金币
     */
    private Integer rewardGold;

    /**
     * 
     */
    private Date createdAt;

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
     * 任务描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 任务描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 奖励经验值
     */
    public Integer getRewardExperience() {
        return rewardExperience;
    }

    /**
     * 奖励经验值
     */
    public void setRewardExperience(Integer rewardExperience) {
        this.rewardExperience = rewardExperience;
    }

    /**
     * 奖励金币
     */
    public Integer getRewardGold() {
        return rewardGold;
    }

    /**
     * 奖励金币
     */
    public void setRewardGold(Integer rewardGold) {
        this.rewardGold = rewardGold;
    }

    /**
     * 
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        Task other = (Task) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getRewardExperience() == null ? other.getRewardExperience() == null : this.getRewardExperience().equals(other.getRewardExperience()))
            && (this.getRewardGold() == null ? other.getRewardGold() == null : this.getRewardGold().equals(other.getRewardGold()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getRewardExperience() == null) ? 0 : getRewardExperience().hashCode());
        result = prime * result + ((getRewardGold() == null) ? 0 : getRewardGold().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", description=").append(description);
        sb.append(", rewardExperience=").append(rewardExperience);
        sb.append(", rewardGold=").append(rewardGold);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}