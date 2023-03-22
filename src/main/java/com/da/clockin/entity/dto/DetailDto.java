//开发时间 : 2023/3/15 8:36

package com.da.clockin.entity.dto;

import com.da.clockin.entity.Detail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class DetailDto extends Detail implements Serializable {
    private String uName;
    private String type;
}
