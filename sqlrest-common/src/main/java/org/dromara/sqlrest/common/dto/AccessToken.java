// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("TOKEN information")
public class AccessToken implements Serializable {

  @ApiModelProperty("Real name")
  private String realName;

  @ApiModelProperty("Unique identifier")
  private String appKey;

  @ApiModelProperty("Token string")
  private String accessToken;

  @JsonIgnore
  @ApiModelProperty("Timestamp when created")
  private Long createTimestamp;

  @ApiModelProperty("Validity period (time period, unit: seconds)")
  private Long expireSeconds;
}
