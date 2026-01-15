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

import org.dromara.sqlrest.common.enums.OnOffEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Unified alarm configuration")
public class UpdateAlarmConfigRequest {

  @NotNull(message = "status cannot be null")
  @ApiModelProperty("Enable status")
  private OnOffEnum status;

  @NotBlank(message = "endpoint cannot be empty")
  @ApiModelProperty("API endpoint")
  private String endpoint;

  @NotBlank(message = "contentType cannot be empty")
  @ApiModelProperty("Input format type")
  private String contentType;

  @NotBlank(message = "inputTemplate cannot be empty")
  @ApiModelProperty("Input template")
  private String inputTemplate;
}
