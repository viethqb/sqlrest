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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Script editor completion list")
public class ScriptEditorCompletion {

  @ApiModelProperty("Return type")
  private String meta;

  @ApiModelProperty("Dropdown hint")
  private String caption;

  @ApiModelProperty("Selected fill value")
  private String value;

  @Builder.Default
  @ApiModelProperty("Score value")
  private Integer score = 1;
}
