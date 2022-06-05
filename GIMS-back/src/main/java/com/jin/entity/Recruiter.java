package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 招聘者
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Recruiter {
    /**
     * 招聘者id
     */
    @TableId(type = IdType.AUTO)
    private Long recruiterId;

    /**
     * 招聘者姓名
     */
    private String recruiterName;

    /**
     * 公司编号
     */
    private Long companyId;

    /**
     * 账号
     */
    private Long accountId;

}