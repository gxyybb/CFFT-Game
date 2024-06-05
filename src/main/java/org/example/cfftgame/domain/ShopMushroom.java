package org.example.cfftgame.domain;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName shop_mushroom
 */
@TableName(value ="shop_mushroom")
public class ShopMushroom implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 生长时间（小时）
     */
    private Integer growthTime;

    /**
     * 最小收获产量
     */
    private Integer minHarvestYield;

    /**
     * 最大收获产量
     */
    private Integer maxHarvestYield;

    /**
     * 解锁该蘑菇所需的等级
     */
    private Integer unlockLevel;

    /**
     * 卖价
     */
    private Integer sellPrice;

    /**
     * 经验值
     */
    private Integer experienceValue;

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
    public String getName() {
        return name;
    }

    /**
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 生长时间（小时）
     */
    public Integer getGrowthTime() {
        return growthTime;
    }

    /**
     * 生长时间（小时）
     */
    public void setGrowthTime(Integer growthTime) {
        this.growthTime = growthTime;
    }

    /**
     * 最小收获产量
     */
    public Integer getMinHarvestYield() {
        return minHarvestYield;
    }

    /**
     * 最小收获产量
     */
    public void setMinHarvestYield(Integer minHarvestYield) {
        this.minHarvestYield = minHarvestYield;
    }

    /**
     * 最大收获产量
     */
    public Integer getMaxHarvestYield() {
        return maxHarvestYield;
    }

    /**
     * 最大收获产量
     */
    public void setMaxHarvestYield(Integer maxHarvestYield) {
        this.maxHarvestYield = maxHarvestYield;
    }

    /**
     * 解锁该蘑菇所需的等级
     */
    public Integer getUnlockLevel() {
        return unlockLevel;
    }

    /**
     * 解锁该蘑菇所需的等级
     */
    public void setUnlockLevel(Integer unlockLevel) {
        this.unlockLevel = unlockLevel;
    }

    /**
     * 卖价
     */
    public Integer getSellPrice() {
        return sellPrice;
    }

    /**
     * 卖价
     */
    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * 经验值
     */
    public Integer getExperienceValue() {
        return experienceValue;
    }

    /**
     * 经验值
     */
    public void setExperienceValue(Integer experienceValue) {
        this.experienceValue = experienceValue;
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
        ShopMushroom other = (ShopMushroom) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getGrowthTime() == null ? other.getGrowthTime() == null : this.getGrowthTime().equals(other.getGrowthTime()))
            && (this.getMinHarvestYield() == null ? other.getMinHarvestYield() == null : this.getMinHarvestYield().equals(other.getMinHarvestYield()))
            && (this.getMaxHarvestYield() == null ? other.getMaxHarvestYield() == null : this.getMaxHarvestYield().equals(other.getMaxHarvestYield()))
            && (this.getUnlockLevel() == null ? other.getUnlockLevel() == null : this.getUnlockLevel().equals(other.getUnlockLevel()))
            && (this.getSellPrice() == null ? other.getSellPrice() == null : this.getSellPrice().equals(other.getSellPrice()))
            && (this.getExperienceValue() == null ? other.getExperienceValue() == null : this.getExperienceValue().equals(other.getExperienceValue()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getGrowthTime() == null) ? 0 : getGrowthTime().hashCode());
        result = prime * result + ((getMinHarvestYield() == null) ? 0 : getMinHarvestYield().hashCode());
        result = prime * result + ((getMaxHarvestYield() == null) ? 0 : getMaxHarvestYield().hashCode());
        result = prime * result + ((getUnlockLevel() == null) ? 0 : getUnlockLevel().hashCode());
        result = prime * result + ((getSellPrice() == null) ? 0 : getSellPrice().hashCode());
        result = prime * result + ((getExperienceValue() == null) ? 0 : getExperienceValue().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", growthTime=").append(growthTime);
        sb.append(", minHarvestYield=").append(minHarvestYield);
        sb.append(", maxHarvestYield=").append(maxHarvestYield);
        sb.append(", unlockLevel=").append(unlockLevel);
        sb.append(", sellPrice=").append(sellPrice);
        sb.append(", experienceValue=").append(experienceValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}