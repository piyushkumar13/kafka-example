package com.piyush.kafkaspringboot.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Piyush Kumar.
 * @since 25/05/21.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Student {

    private int id;
    private String name;
    private String address;
    private String[] subject;

    public Student(){

    }
}
