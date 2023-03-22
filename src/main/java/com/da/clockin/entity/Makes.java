package com.da.clockin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Makes extends Domain implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 评价表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Long uId;

    /**
     * 管理员id
     */
    private Long rootId;

    /**
     * 详情表id
     */
    private Long dId;

    /**
     * 管理员评价
     */
    private String make;

    /**
     * 评分1-100分
     */
    private Integer score;

    /**
     * 是否已读 1:已读 0:未读
     */
    private Boolean read;




}
