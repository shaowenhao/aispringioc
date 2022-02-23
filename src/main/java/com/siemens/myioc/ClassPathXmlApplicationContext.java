package com.siemens.myioc;

import com.siemens.entity.Student;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 *     Create Time: 2022年02月22日 17:22
 * </p>
 **/
public class ClassPathXmlApplicationContext implements ApplicationContext {

    Map<String,Object> ioc = new HashMap<String, Object>();

    //解析xml  把xml转成了document对象
   ClassPathXmlApplicationContext(String path) {

       SAXReader saxReader = new SAXReader();
       Document document = null;
       try {
           document = saxReader.read(ClassPathXmlApplicationContext.class.getClassLoader().getResourceAsStream(path));
           Element root = document.getRootElement();
           Iterator<Element> iterator = root.elementIterator();
           while (iterator.hasNext()) {
               Element element = iterator.next();
               String id = element.attributeValue("id");
               String className = element.attributeValue("class");
               //通过反射机制创建对象
               Class clazz = Class.forName(className);
               Student obj = (Student) clazz.getConstructor().newInstance();
               //给目标对象赋值
               Iterator propertyIterator = element.elementIterator();
               while (propertyIterator.hasNext()){
                   Element propertyElement = (Element) propertyIterator.next();
                   if(propertyElement.attributeValue("name").equals("id")){
                       long pid = Long.parseLong(propertyElement.attributeValue("value"));
                       obj.setId(pid);
                   }
                   if(propertyElement.attributeValue("name").equals("name")){
                       String pname = propertyElement.attributeValue("value");
                       obj.setName(pname);
                   }
                   if(propertyElement.attributeValue("name").equals("age")){
                       int page = Integer.parseInt(propertyElement.attributeValue("value"));
                       obj.setAge(page);
                   }
               }
               System.out.println(obj);
           }

       } catch (DocumentException ex) {
           ex.printStackTrace();
       } catch (InstantiationException e) {
           e.printStackTrace();
       } catch (InvocationTargetException e) {
           e.printStackTrace();
       } catch (NoSuchMethodException e) {
           e.printStackTrace();
       } catch (IllegalAccessException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
   }


   //定义了这个方法 想到通过一个值获得对象  Map的结构
    public Object getBean(String name) {
        return ioc.get(name);
    }
}
