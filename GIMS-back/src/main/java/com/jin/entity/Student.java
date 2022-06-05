package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;

/**
 * <p>
 * 学生
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Student {
    /**
     * 学生id
     */
    @TableId(type = IdType.AUTO)
    private Long studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 所属班级
     */
    private Long classId;

    /**
     * 学分
     */
    private Double credit;

    /**
     * 简历
     */
    private String resume;

    /**
     * 账号id
     */
    private Long accountId;

}