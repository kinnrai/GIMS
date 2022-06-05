package com.jin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 *  <p>
 * 学校开设专业
 *  </p>
 *
 * @author 金磊
 */
@Data
public class Found {
    /**
     * 开设id
     */
    @TableId(type = IdType.AUTO)
    private Long foundId;
    /**
     * 学校id
     */
    private Long schoolId;

    /**
     * 专业id
     */
    private Long majorId;
}
