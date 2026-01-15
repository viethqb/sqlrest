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
import org.dromara.sqlrest.common.enums.AliveTimeEnum;
import org.dromara.sqlrest.common.enums.DurationTimeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Client application details")
public class AppClientDetailResponse {

  @ApiModelProperty("ID number")
  private Long id;

  @ApiModelProperty("Client application name")
  private String name;

  @ApiModelProperty("Client application description")
  private String description;

  @ApiModelProperty("Application AppKey account")
  private String appKey;

  @ApiModelProperty("Expiration type")
  private DurationTimeEnum expireDuration;

  @ApiModelProperty("Expiration time")
  private Long expireAt;

  @ApiModelProperty("Expiration time (string)")
  private String expireAtStr;

  @ApiModelProperty("Expiration type")
  private String expireType;

  @ApiModelProperty("Whether expired")
  private Boolean isExpired;

  @ApiModelProperty("Token lifetime")
  private AliveTimeEnum tokenAlive;

  @ApiModelProperty("Create time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp createTime;

  @ApiModelProperty("Update time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp updateTime;
}
