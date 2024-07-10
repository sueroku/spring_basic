package com.beyond.basic.domain;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;


@Data
public class Student {
    private String name;
    private String email;
    private List<Student.Grade> grades = new ArrayList<>();
//    private List<Grade> grades = new ArrayList<>();
    @Data
    static class Grade{
        private String className;
        private int point;
    }
}

//@Data
//class Grade {
//    private String className;
//    private int point;
//}
