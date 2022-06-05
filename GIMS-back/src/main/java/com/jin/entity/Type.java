package com.jin.entity;

import lombok.Data;

/**
 * <p>
 * 账号类型
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Type {
    /**
     * 类型id
     */
    private Integer typeId;

    /**
     * 类型名称
     */
    private String typeName;

}