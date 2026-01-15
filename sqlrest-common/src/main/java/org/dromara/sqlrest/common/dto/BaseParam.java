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

import org.dromara.sqlrest.common.enums.ParamLocationEnum;
import org.dromara.sqlrest.common.enums.ParamTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseParam implements Serializable {

  @ApiModelProperty("ID (generated and used by frontend)")
  private String id;

  @ApiModelProperty("Parameter name")
  private String name;

  @ApiModelProperty("Parameter type")
  private ParamTypeEnum type;

  @ApiModelProperty("Parameter location")
  private ParamLocationEnum location;

  @ApiModelProperty("Whether it is an array")
  private Boolean isArray;

  @ApiModelProperty("Whether it is required")
  private Boolean required;

  @ApiModelProperty("Default value")
  private String defaultValue;

  @ApiModelProperty("Parameter description")
  private String remark;
}
