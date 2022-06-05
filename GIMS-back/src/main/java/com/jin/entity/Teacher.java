package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 老师
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Teacher {
    /**
     * 教师id
     */
    @TableId(type = IdType.AUTO)
    private Long teacherId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 所属学校
     */
    private Long schoolId;

    /**
     * 账号id
     */
    private Long accountId;

}