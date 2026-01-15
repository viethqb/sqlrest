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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Client group association")
public class AppClientGroupRequest {

  @NotNull(message = "id cannot be null")
  @ApiModelProperty("Client application ID")
  private Long id;

  @NotEmpty(message = "groupIds cannot be empty")
  @ApiModelProperty("Group ID list")
  private List<Long> groupIds;
}
