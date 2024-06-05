package org.example.cfftgame.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName shop_fertilizer
 */
@TableName(value ="shop_fertilizer")
public class ShopFertilizer implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 肥料名称
     */
    private String name;

    /**
     * 效果（缩短的时间，单位：小时）
     */
    private Integer effect;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 解锁等级
     */
    private Integer unlockLevel;

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
     * 肥料名称
     */
    public String getName() {
        return name;
    }

    /**
     * 肥料名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 效果（缩短的时间，单位：小时）
     */
    public Integer getEffect() {
        return effect;
    }

    /**
     * 效果（缩短的时间，单位：小时）
     */
    public void setEffect(Integer effect) {
        this.effect = effect;
    }

    /**
     * 价格
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 价格
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 解锁等级
     */
    public Integer getUnlockLevel() {
        return unlockLevel;
    }

    /**
     * 解锁等级
     */
    public void setUnlockLevel(Integer unlockLevel) {
        this.unlockLevel = unlockLevel;
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
        ShopFertilizer other = (ShopFertilizer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getEffect() == null ? other.getEffect() == null : this.getEffect().equals(other.getEffect()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getUnlockLevel() == null ? other.getUnlockLevel() == null : this.getUnlockLevel().equals(other.getUnlockLevel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getEffect() == null) ? 0 : getEffect().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getUnlockLevel() == null) ? 0 : getUnlockLevel().hashCode());
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
        sb.append(", effect=").append(effect);
        sb.append(", price=").append(price);
        sb.append(", unlockLevel=").append(unlockLevel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}