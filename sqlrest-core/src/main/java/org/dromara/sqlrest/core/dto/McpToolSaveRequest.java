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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("MCP tool configuration")
public class McpToolSaveRequest {

  @ApiModelProperty("ID number (used when saving interface)")
  private Long id;

  @NotNull(message = "apiId cannot be null")
  @ApiModelProperty("API ID")
  private Long apiId;

  @NotBlank(message = "name cannot be empty")
  @ApiModelProperty("Tool name")
  private String name;

  @NotBlank(message = "description cannot be empty")
  @ApiModelProperty("Tool description")
  private String description;
}
