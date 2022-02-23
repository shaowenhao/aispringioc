package com.siemens.entity;

import lombok.Data;

/**
 * <p>
 *     Create Time: 2022年02月21日 17:13
 * </p>
 **/
@Data
public class Student {

   private long id;
   private String name;
   private  int age;
   private Address address;
}
