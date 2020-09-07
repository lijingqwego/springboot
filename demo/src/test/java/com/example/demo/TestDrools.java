package com.example.demo;

import com.example.demo.drools.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Title TestDrools
 * Package
 * Description 测试Drools类
 * Author 唐磊
 * Date 2019-07-06 14:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
public class TestDrools {

    private static KieContainer container = null;
    private KieSession statefulKieSession = null;

    /**
     * Title test
     * Param []
     * Return void
     * Exception
     * Description 基于配置文件
     * Author 唐磊
     * Date 2019-07-06 14:27
     */
    @Test
    public void test() {

        KieServices kieServices = KieServices.Factory.get();
        container = kieServices.getKieClasspathContainer();
        statefulKieSession = container.newKieSession("all-rules");
        Person person = new Person();

        person.setAge(12);
        person.setName("Test");

        statefulKieSession.insert(person);
        statefulKieSession.fireAllRules();
        statefulKieSession.dispose();
    }

    /**
     * Title ruleStringTest
     * Param []
     * Return void
     * Exception
     * Description 基于字符串
     * Author 唐磊
     * Date 2019-07-06 14:27
     */
    @Test
    public void ruleStringTest() throws Exception {

        String myRule = "import com.example.demo.drools.Person\n" +
                "\n" +
                "dialect  \"mvel\"\n" +
                "\n" +
                "rule \"age\"\n" +
                "    when\n" +
                "        $person : Person(age<16 || age>50)\n" +
                "    then\n" +
                "        System.out.println(\"这个人的年龄不符合要求！（基于动态加载）\");\n" +
                "end\n";


        KieHelper helper = new KieHelper();

        helper.addContent(myRule, ResourceType.DRL);

        KieSession ksession = helper.build().newKieSession();

        Person person = new Person();

        person.setAge(12);
        person.setName("Test");

        ksession.insert(person);

        ksession.fireAllRules();

        ksession.dispose();
    }
}
