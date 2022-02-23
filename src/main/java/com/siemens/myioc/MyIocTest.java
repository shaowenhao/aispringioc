package com.siemens.myioc;

import com.siemens.myioc.ApplicationContext;
import com.siemens.myioc.ClassPathXmlApplicationContext;

/**
 * <p>Create Time: 2022年02月22日 17:43          </p>
 **/
public class MyIocTest {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("myspring.xml");
    }
}
