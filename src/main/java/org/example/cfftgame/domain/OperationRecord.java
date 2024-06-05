package org.example.cfftgame.domain;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName operation_record
 */
@TableName(value ="operation_record")
public class OperationRecord implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer farmMushroomId;

    /**
     * 
     */
    private Integer playerId;

    /**
     * 操作类型（浇水、施肥、抓虫）
     */
    private String operationType;

    /**
     * 操作时间
     */
    private Date operationTime;

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
    public Integer getFarmMushroomId() {
        return farmMushroomId;
    }

    /**
     * 
     */
    public void setFarmMushroomId(Integer farmMushroomId) {
        this.farmMushroomId = farmMushroomId;
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
     * 操作类型（浇水、施肥、抓虫）
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * 操作类型（浇水、施肥、抓虫）
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * 操作时间
     */
    public Date getOperationTime() {
        return operationTime;
    }

    /**
     * 操作时间
     */
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
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
        OperationRecord other = (OperationRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFarmMushroomId() == null ? other.getFarmMushroomId() == null : this.getFarmMushroomId().equals(other.getFarmMushroomId()))
            && (this.getPlayerId() == null ? other.getPlayerId() == null : this.getPlayerId().equals(other.getPlayerId()))
            && (this.getOperationType() == null ? other.getOperationType() == null : this.getOperationType().equals(other.getOperationType()))
            && (this.getOperationTime() == null ? other.getOperationTime() == null : this.getOperationTime().equals(other.getOperationTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFarmMushroomId() == null) ? 0 : getFarmMushroomId().hashCode());
        result = prime * result + ((getPlayerId() == null) ? 0 : getPlayerId().hashCode());
        result = prime * result + ((getOperationType() == null) ? 0 : getOperationType().hashCode());
        result = prime * result + ((getOperationTime() == null) ? 0 : getOperationTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", farmMushroomId=").append(farmMushroomId);
        sb.append(", playerId=").append(playerId);
        sb.append(", operationType=").append(operationType);
        sb.append(", operationTime=").append(operationTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}