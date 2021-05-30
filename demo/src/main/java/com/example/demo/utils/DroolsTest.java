package com.example.demo.utils;

import com.example.demo.pojo.Refuse;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.List;

public class DroolsTest {

    public static void main2(String[] args) {
        try {
            List<String> ageAlis = new ArrayList<>();
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("base.rules");
            Refuse refuse=new Refuse();
            refuse.setAge(700);
            refuse.setSex('女');
//            Person person = new Person();
//            person.setAge(51);
//            kSession.insert(person);
            kSession.setGlobal("ageAlis",ageAlis);
            kSession.insert(refuse);
            int count=kSession.fireAllRules();
            System.out.println("规则执行条数：--------"+count);
            System.out.println("规则执行完成--------"+refuse.toString());
            System.out.println(kSession.getGlobals().get("ageAlis"));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
