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
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Unified alarm configuration")
public class TestAlarmConfigRequest {

  @NotBlank(message = "endpoint cannot be empty")
  @ApiModelProperty("API endpoint")
  private String endpoint;

  @NotBlank(message = "contentType cannot be empty")
  @ApiModelProperty("Input format type")
  private String contentType;

  @NotBlank(message = "inputTemplate cannot be empty")
  @ApiModelProperty("Input template")
  private String inputTemplate;

  @NotEmpty(message = "dataModel cannot be empty")
  @ApiModelProperty("Sample data")
  private List<NameValueBaseResponse> dataModel;
}
