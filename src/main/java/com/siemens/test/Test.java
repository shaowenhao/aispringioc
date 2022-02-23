package com.siemens.test;

import com.siemens.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>Create Time: 2022年02月21日 17:16          </p>
 **/
public class Test {
    public static void main(String[] args) {
//        Student student = new Student();
//        student.setId(10);
//        student.setName("shaowenhao");
//        student.setAge(20);
//        System.out.println(student);

        //根据下面2行代码 设计自己的ioc 接口和实现类 模拟ioc的工作
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("myspring.xml");
        Student student =(Student) applicationContext.getBean("student");
        System.out.println(student);
    }
}
