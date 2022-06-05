package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 职业
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Profession {
    /**
     * 职位id
     */
    @TableId(type = IdType.AUTO)
    private Long professionId;

    /**
     * 职位名称
     */
    private String professionName;

}