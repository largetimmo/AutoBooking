package test;


import dao.ConnectionPool;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Tester {
    @Test
    public void doTest(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        ConnectionPool connectionPool = (ConnectionPool) applicationContext.getBean("connectionpool");

    }

}
