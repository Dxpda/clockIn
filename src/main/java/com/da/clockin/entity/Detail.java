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
public class Detail extends Domain implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 详情表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long uId;

    /**
     * 班级
     */
    private String uClass;


    /**
     * 应到人数
     */
    private Integer maxPerson;


    /**
     * 实到人数
     */
    private Integer actualPerson;

    /**
     * 图片
     */
    private String image;

    /**
     * 几栋楼
     */
    private Integer floor;

    /**
     * 备注
     */
    private String mark;




}
