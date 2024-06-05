package org.example.cfftgame.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName farm_mushroom
 */
@TableName(value ="farm_mushroom")
public class FarmMushroom implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer plotId;

    /**
     * 
     */
    private Integer mushroomId;

    /**
     * 种植时间
     */
    private Date plantedAt;

    /**
     * 剩余生长时间（小时）
     */
    private Integer growthTime;

    /**
     * 状态（使用位运算表示浇水、施肥、虫害）
     */
    private Integer status;

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
    public Integer getPlotId() {
        return plotId;
    }

    /**
     * 
     */
    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }

    /**
     * 
     */
    public Integer getMushroomId() {
        return mushroomId;
    }

    /**
     * 
     */
    public void setMushroomId(Integer mushroomId) {
        this.mushroomId = mushroomId;
    }

    /**
     * 种植时间
     */
    public Date getPlantedAt() {
        return plantedAt;
    }

    /**
     * 种植时间
     */
    public void setPlantedAt(Date plantedAt) {
        this.plantedAt = plantedAt;
    }

    /**
     * 剩余生长时间（小时）
     */
    public Integer getGrowthTime() {
        return growthTime;
    }

    /**
     * 剩余生长时间（小时）
     */
    public void setGrowthTime(Integer growthTime) {
        this.growthTime = growthTime;
    }

    /**
     * 状态（使用位运算表示浇水、施肥、虫害）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态（使用位运算表示浇水、施肥、虫害）
     */
    public void setStatus(Integer status) {
        this.status = status;
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
        FarmMushroom other = (FarmMushroom) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlotId() == null ? other.getPlotId() == null : this.getPlotId().equals(other.getPlotId()))
            && (this.getMushroomId() == null ? other.getMushroomId() == null : this.getMushroomId().equals(other.getMushroomId()))
            && (this.getPlantedAt() == null ? other.getPlantedAt() == null : this.getPlantedAt().equals(other.getPlantedAt()))
            && (this.getGrowthTime() == null ? other.getGrowthTime() == null : this.getGrowthTime().equals(other.getGrowthTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlotId() == null) ? 0 : getPlotId().hashCode());
        result = prime * result + ((getMushroomId() == null) ? 0 : getMushroomId().hashCode());
        result = prime * result + ((getPlantedAt() == null) ? 0 : getPlantedAt().hashCode());
        result = prime * result + ((getGrowthTime() == null) ? 0 : getGrowthTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", plotId=").append(plotId);
        sb.append(", mushroomId=").append(mushroomId);
        sb.append(", plantedAt=").append(plantedAt);
        sb.append(", growthTime=").append(growthTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}