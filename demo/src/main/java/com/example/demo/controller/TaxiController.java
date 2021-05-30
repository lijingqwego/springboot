package com.example.demo.controller;

import java.math.BigDecimal;

import com.example.demo.drools.DroolsConstants;
import com.example.demo.pojo.TaxiFare;
import com.example.demo.pojo.TaxiRide;
import com.example.demo.service.impl.DroolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Drools的士计费控制器
 * (白天起步价：首3公里12元; 续租价：超过3公里部分，每公里2.6元;返空费实行阶梯附加，15至25公里按照续租价加收20%，25公里以上按续租价加收50%; )
 * (夜间起步价：首3公里12元; 续租价：超过3公里部分，每公里3.2元;返空费实行阶梯附加，15至25公里按照续租价加收20%，25公里以上按续租价加收50%; )
 *
 * @author zhengkai.blog.csdn.net
 */
@RequestMapping("taxi")
@RestController
public class TaxiController {

    @Autowired
    private DroolsService droolsService;

    @PostMapping("cal")
    public ResponseEntity<Object> calTaxiFare(TaxiRide taxiRide) {
        if (taxiRide.getIsNightSurcharge() == null) {
            taxiRide.setIsNightSurcharge(false);
        }
        if (taxiRide.getDistanceInMile() == null) {
            taxiRide.setDistanceInMile(new BigDecimal(9));
        }
        TaxiFare rideFare = new TaxiFare();
        //Drools计算
        droolsService.execRules("rideFare", rideFare, new Object[]{taxiRide}, DroolsConstants.RULES_PREFIX_TAXI_FARE);
        BigDecimal totalCharge = rideFare.total();
        //BigDecimal处理：设置一位小数位，向上取整，去掉末尾0，,转换为String
        String totalString = totalCharge.setScale(1, BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString();
        return ResponseEntity.ok(totalString);
    }
}
