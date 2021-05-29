package com.example.demo.pojo;
/**
 * 拒件实体
 * @author Administrator
 *
 */
public class Refuse {
    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private char sex;

    /**
     * 古代年龄别称
     */
    private String ageAlis;

    /**
     * 工作城市
     */
    private String workCity;
    /**
     * 申请城市
     */
    private String applyCity;
    /**
     * 居住城市
     */
    private String addressCity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getAgeAlis() {
        return ageAlis;
    }

    public void setAgeAlis(String ageAlis) {
        this.ageAlis = ageAlis;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getApplyCity() {
        return applyCity;
    }

    public void setApplyCity(String applyCity) {
        this.applyCity = applyCity;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
}
