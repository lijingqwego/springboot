package com.example.demo.pojo;

import java.math.BigDecimal;

/**
 * TaxiRide 打车情况
 */
public class TaxiRide {
    /**
     * 是否夜间
     */
    private Boolean isNightSurcharge;
    /**
     * 打车距离
     */
    private BigDecimal distanceInMile;

    public Boolean getIsNightSurcharge() {
        return isNightSurcharge;
    }

    public void setIsNightSurcharge(Boolean nightSurcharge) {
        isNightSurcharge = nightSurcharge;
    }

    public BigDecimal getDistanceInMile() {
        return distanceInMile;
    }

    public void setDistanceInMile(BigDecimal distanceInMile) {
        this.distanceInMile = distanceInMile;
    }
}
