package com.siemens.myioc;

import com.siemens.entity.Student;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
               Object obj = clazz.getConstructor().newInstance();
               //给目标对象赋值
               Iterator propertyIterator = element.elementIterator();
               while (propertyIterator.hasNext()){
                   Element propertyElement = (Element) propertyIterator.next();
                   //实现赋值 通过Method 和 Field
                   String name = propertyElement.attributeValue("name");
                   String valueStr = propertyElement.attributeValue("value");
                   // 因为set方法 固定 setXxxxx名规则
                   String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1);
                   Field field = clazz.getDeclaredField(name);
                   Method method = clazz.getDeclaredMethod(methodName, field.getType());
                   //先打印出 便于做判断的条件
                   System.out.println(field.getType().getName());
                   //根据成员变量的数据类型 将value进行转换
                   Object value = null;
                 if (field.getType().getName()=="long"){
                     value = Long.parseLong(valueStr);
                     method.invoke(obj,value);
                 }
                 if (field.getType().getName()=="java.lang.String"){
                     value = valueStr;
                     method.invoke(obj,value);
                 }
                 if(field.getType().getName()=="int"){
                     value = Integer.parseInt(valueStr);
                     method.invoke(obj,value);
                 }
               }

              // System.out.println(obj);
               ioc.put(id,obj);
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
       } catch (NoSuchFieldException e) {
           e.printStackTrace();
       }
   }


   //定义了这个方法 想到通过一个值获得对象  Map的结构
    public Object getBean(String name) {
        return ioc.get(name);
    }
}
