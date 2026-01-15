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
import org.dromara.sqlrest.common.enums.WhiteBlackEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Firewall rules")
public class UpdateFirewallRulesRequest {

  @NotNull(message = "status cannot be null")
  @ApiModelProperty("Enable status")
  private OnOffEnum status;

  @ApiModelProperty("Whitelist/blacklist option")
  private WhiteBlackEnum mode;

  @ApiModelProperty("Address list")
  private String addresses;
}
