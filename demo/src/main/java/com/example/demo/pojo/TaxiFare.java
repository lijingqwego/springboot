package com.example.demo.pojo;

import java.math.BigDecimal;

/**
 * TaxiFare 打车费
 */
public class TaxiFare {
    private BigDecimal nightSurcharge;
    private BigDecimal  rideFare;

    public BigDecimal getNightSurcharge() {
        return nightSurcharge;
    }

    public void setNightSurcharge(BigDecimal nightSurcharge) {
        this.nightSurcharge = nightSurcharge;
    }

    public BigDecimal getRideFare() {
        return rideFare;
    }

    public void setRideFare(BigDecimal rideFare) {
        this.rideFare = rideFare;
    }

    public BigDecimal  total() {
        return this.nightSurcharge.add(this.rideFare);
    }
}
