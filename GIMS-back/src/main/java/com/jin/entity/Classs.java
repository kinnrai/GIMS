package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 班级
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Classs {
    /**
     * 班级id
     */
    @TableId(type = IdType.AUTO)
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 管理老师
     */
    private Long teacherId;

    /**
     * 专业
     */
    private Long majorId;

}