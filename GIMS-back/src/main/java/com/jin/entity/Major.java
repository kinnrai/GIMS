package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 专业
 * </p>
 *
 * @author 金磊
 * @since 2022-05-04
 */
@Data
public class Major {
    /**
     * 专业id
     */
    @TableId(type = IdType.AUTO)
    private Long majorId;

    /**
     * 专业名称
     */
    private String majorName;

}