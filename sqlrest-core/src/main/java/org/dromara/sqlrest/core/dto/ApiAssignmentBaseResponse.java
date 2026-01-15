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
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.sqlrest.common.enums.ExecuteEngineEnum;
import org.dromara.sqlrest.common.enums.HttpMethodEnum;

@Data
@NoArgsConstructor
@ApiModel("API configuration basic information")
public class ApiAssignmentBaseResponse {

  @ApiModelProperty("ID number")
  private Long id;

  @ApiModelProperty("API configuration name")
  private String name;

  @ApiModelProperty("Description")
  private String description;

  @ApiModelProperty("Module ID")
  private Long moduleId;

  @ApiModelProperty("Module name")
  private String moduleName;

  @ApiModelProperty("Authorization group ID")
  private Long groupId;

  @ApiModelProperty("Authorization group name")
  private String groupName;

  @ApiModelProperty("API request method: GET, HEAD, PUT, POST, DELETE")
  private HttpMethodEnum method;

  @ApiModelProperty("Request path (without api prefix)")
  private String path;

  @ApiModelProperty("Whether online")
  private Boolean status;

  @ApiModelProperty("commitId")
  private Long commitId;

  @ApiModelProperty("Online version")
  private Integer version;

  @ApiModelProperty("Whether public")
  private Boolean open;

  @ApiModelProperty("Whether alarm is enabled")
  private Boolean alarm;

  @ApiModelProperty("Execution engine")
  private ExecuteEngineEnum engine;

  @ApiModelProperty("Create time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp createTime;

  @ApiModelProperty("Update time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp updateTime;
}
