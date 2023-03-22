//开发时间 : 2023/3/17 23:33

package com.da.clockin.entity.Vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class DetailVo  {

    private Long id;
    @NotNull(message = "应到人数不能为空")
    @Range(max = 1000,min = 1,message = "应到人数有误")
    private Integer maxPerson;
    @NotNull(message = "班级不能为空")
    @Length(max = 5,min = 4,message = "班级长度有误")
    private String uClass;
    @NotNull(message = "实到人数不能为空")
    @Range(max = 1000,min = 1,message = "实到人数有误")
    private Integer actualPerson;
    @NotNull(message = "楼栋不能为空")
    @Range(max = 20,min = 1,message = "楼栋人数有误")
    private Integer floor;

    @Length(max = 100,message = "备注长度超过限制")
    private String mark;

    private String image;
}
