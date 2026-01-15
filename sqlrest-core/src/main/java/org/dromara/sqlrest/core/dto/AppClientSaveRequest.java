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

import org.dromara.sqlrest.common.enums.AliveTimeEnum;
import org.dromara.sqlrest.common.enums.ExpireTimeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Client application information")
public class AppClientSaveRequest {

  @NotBlank(message = "name cannot be empty")
  @ApiModelProperty("Client application name")
  private String name;

  @ApiModelProperty("Client application description")
  private String description;

  @NotBlank(message = "appKey cannot be empty")
  @ApiModelProperty("Application AppKey account")
  private String appKey;

  @NotNull(message = "expireTime cannot be null")
  @ApiModelProperty("Expiration time")
  private ExpireTimeEnum expireTime;

  @NotNull(message = "tokenAlive cannot be null")
  @ApiModelProperty("Token lifetime")
  private AliveTimeEnum tokenAlive;
}
