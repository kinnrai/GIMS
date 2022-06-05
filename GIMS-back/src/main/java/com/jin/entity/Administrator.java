package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Administrator {
    /**
     * 管理员id
     */
    @TableId(type = IdType.AUTO)
    private Long adminId;

    /**
     * 管理员姓名
     */
    private String adminName;

    /**
     * 账号id
     */
    private Long accountId;

}