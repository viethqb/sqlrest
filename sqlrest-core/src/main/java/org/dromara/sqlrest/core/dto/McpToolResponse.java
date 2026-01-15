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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MCP tool details")
public class McpToolResponse {

  @ApiModelProperty("ID number")
  private Long id;

  @ApiModelProperty("Tool name")
  private String name;

  @ApiModelProperty("Tool description")
  private String description;

  @ApiModelProperty("API module ID")
  private Long moduleId;

  @ApiModelProperty("API module name")
  private String moduleName;

  @ApiModelProperty("API ID")
  private Long apiId;

  @ApiModelProperty("API name")
  private String apiName;

  @ApiModelProperty("API Method")
  private String apiMethod;

  @ApiModelProperty("API Path")
  private String apiPath;

  @ApiModelProperty("Create time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp createTime;

  @ApiModelProperty("Update time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp updateTime;
}
