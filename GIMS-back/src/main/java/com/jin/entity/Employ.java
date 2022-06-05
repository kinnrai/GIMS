package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 雇佣关系
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Employ {
    /**
     * 招聘id
     */
    @TableId(type = IdType.AUTO)
    private Long employId;

    /**
     * 招聘名
     */
    private String employName;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 职位id
     */
    private Long professionId;

    /**
     * 工资
     */
    private Long wages;

}