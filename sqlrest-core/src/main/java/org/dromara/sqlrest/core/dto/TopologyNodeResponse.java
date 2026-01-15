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
@ApiModel("Topology node information")
public class TopologyNodeResponse {

  @ApiModelProperty("Service ID")
  private String serviceId;

  @ApiModelProperty("Instance ID")
  private String instanceId;

  @ApiModelProperty("Host address")
  private String host;

  @ApiModelProperty("Port number")
  private Integer port;
}
