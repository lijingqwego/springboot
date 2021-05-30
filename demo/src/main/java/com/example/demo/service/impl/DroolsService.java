package com.example.demo.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

    @Autowired
    private KieSession kieSession;

    public String execRules(String globalKey, Object globalValue, Object[] objects, String ruleNamePrefix) {
        if (StringUtils.isNotEmpty(globalKey)) {
            kieSession.setGlobal(globalKey, globalValue);
        }
        for (Object object : objects) {
            kieSession.insert(object);
        }
//        RuleNameEndsWithAgendaFilter 执行名称以xxx结尾的规则
//        RuleNameEqualsAgendaFilter 执行名称全匹配的规则
//        RuleNameMatchesAgendaFilter 可以写自己的正则
//        RuleNameStartsWithAgendaFilter 执行名称以xxx开头的规则
//        RuleNameSerializationAgendaFilter 规则名称序列化代理筛选器（其实好像就是可以执行以上4钟中的一钟）
        int ruleFiredCount = kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter(ruleNamePrefix));
        System.out.println("触发了" + ruleFiredCount + "条规则");
        return "OK";
    }

}
