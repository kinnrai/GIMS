package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 金磊
 */
@Data
public class Deliver {

    /**
     * 投递id
     */
    @TableId(type = IdType.AUTO)
    private Long deliverId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 招聘id
     */
    private Long employId;

    /**
     * 投递状态
     */
    private Integer deliverStatus;
}
