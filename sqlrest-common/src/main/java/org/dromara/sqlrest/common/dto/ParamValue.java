// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Parameter information")
public class ParamValue extends BaseParam {

  @ApiModelProperty("Child elements and values of Object type")
  private List<BaseParamValue> children;

  @ApiModelProperty("Non-array parameter value")
  private String value;

  @ApiModelProperty("Array parameter value")
  private List<String> arrayValues;

  @Data
  public static class BaseParamValue extends BaseParam {

    private String value;

    private List<String> arrayValues;
  }
}
