// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@ApiModel("Flow control rule details")
public class FlowRuleSaveRequest {

  @ApiModelProperty("ID number")
  private Long id;

  @ApiModelProperty("Title")
  private String name;

  @ApiModelProperty("Threshold type")
  private Integer flowGrade;

  @ApiModelProperty("Single machine threshold")
  private Integer flowCount;
}
