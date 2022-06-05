package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 学校
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class School {
    /**
     * 学校id
     */
    @TableId(type = IdType.AUTO)
    private Long schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

}