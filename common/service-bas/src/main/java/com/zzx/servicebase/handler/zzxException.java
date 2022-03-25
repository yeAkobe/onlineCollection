package com.zzx.servicebase.handler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class zzxException extends Exception{
    @ApiModelProperty(value = "状态码")
    private Integer code;
    private String msg;
}
