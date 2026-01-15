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
@ApiModel("Database type")
public class DatabaseTypeDetailResponse {

  @ApiModelProperty("ID number")
  private Integer id;

  @ApiModelProperty("Database type")
  private String type;

  @ApiModelProperty("Driver class")
  private String driver;

  @ApiModelProperty("Connection string sample")
  private String sample;
}
