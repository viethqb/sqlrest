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

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@ApiModel("Flow control rule details")
public class FlowRuleDetailResponse {

  @ApiModelProperty("ID number")
  private Long id;

  @ApiModelProperty("Title")
  private String name;

  @ApiModelProperty("Threshold type")
  private Integer flowGrade;

  @ApiModelProperty("Single machine threshold")
  private Integer flowCount;

  @ApiModelProperty("Create time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp createTime;

  @ApiModelProperty("Update time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp updateTime;
}
