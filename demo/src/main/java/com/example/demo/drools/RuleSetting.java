package com.example.demo.drools;

public class RuleSetting {

    /**
     * 全局变量属性
     */
    private String globalKey;

    /**
     * 全局变量值对象
     */
    private Object globalVal;

    /**
     * 规则处理对象
     */
    private Object handleObject;

    /**
     * 规则前缀
     */
    private String rulePrefix;

    public String getGlobalKey() {
        return globalKey;
    }

    public void setGlobalKey(String globalKey) {
        this.globalKey = globalKey;
    }

    public Object getGlobalVal() {
        return globalVal;
    }

    public void setGlobalVal(Object globalVal) {
        this.globalVal = globalVal;
    }

    public Object getHandleObject() {
        return handleObject;
    }

    public void setHandleObject(Object handleObject) {
        this.handleObject = handleObject;
    }

    public String getRulePrefix() {
        return rulePrefix;
    }

    public void setRulePrefix(String rulePrefix) {
        this.rulePrefix = rulePrefix;
    }
}
