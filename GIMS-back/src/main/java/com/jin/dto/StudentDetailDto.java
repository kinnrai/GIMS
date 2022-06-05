package com.jin.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDetailDto {

    private Long studentId;

    private String studentName;

    private Integer sex;

    private LocalDate birthday;

    private String teacherName;

    private String className;

    private String schoolName;

    private String majorName;

    private Double credit;

    private String resume;
}
