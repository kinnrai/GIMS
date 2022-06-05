package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 公司
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Company {
    /**
     * 公司id
     */
    @TableId(type = IdType.AUTO)
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

}