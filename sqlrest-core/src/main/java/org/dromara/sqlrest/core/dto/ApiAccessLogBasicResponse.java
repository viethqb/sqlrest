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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("API call log record")
public class ApiAccessLogBasicResponse {

  @ApiModelProperty("ID number")
  private Long id;

  @ApiModelProperty("HTTP status code")
  private Integer status;

  @ApiModelProperty("Duration")
  private Long duration;

  @ApiModelProperty("Client address")
  private String ipAddr;

  @ApiModelProperty("Client UA")
  private String userAgent;

  @ApiModelProperty("Application name")
  private String clientApp;

  @ApiModelProperty("Request input parameters")
  private Map<String, Object> parameters;

  @ApiModelProperty("Error exception")
  private String exception;

  @ApiModelProperty("Record time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp createTime;

  @ApiModelProperty("Executor address")
  private String executorAddr;

  @ApiModelProperty("Gateway address")
  private String gatewayAddr;
}
