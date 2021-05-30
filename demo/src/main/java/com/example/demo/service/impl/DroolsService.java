package com.example.demo.service.impl;

import com.example.demo.drools.RuleSetting;
import com.example.demo.schedule.ScheduleManage;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {
    private static Logger logger = Logger.getLogger(ScheduleManage.class);

    @Autowired
    private KieSession kieSession;

    public void execRules(RuleSetting setting) {
        if (StringUtils.isNotEmpty(setting.getGlobalKey())) {
            kieSession.setGlobal(setting.getGlobalKey(), setting.getGlobalVal());
        }
        kieSession.insert(setting.getHandleObject());
//        RuleNameEndsWithAgendaFilter 执行名称以xxx结尾的规则
//        RuleNameEqualsAgendaFilter 执行名称全匹配的规则
//        RuleNameMatchesAgendaFilter 可以写自己的正则
//        RuleNameStartsWithAgendaFilter 执行名称以xxx开头的规则
//        RuleNameSerializationAgendaFilter 规则名称序列化代理筛选器（其实好像就是可以执行以上4钟中的一钟）
        int ruleFiredCount = kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter(setting.getRulePrefix()));
        logger.info("触发了" + ruleFiredCount + "条规则");
    }

}
