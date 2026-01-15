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

import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.sqlrest.common.dto.ItemParam;
import org.dromara.sqlrest.common.dto.OutParam;
import org.dromara.sqlrest.common.enums.CacheKeyTypeEnum;
import org.dromara.sqlrest.common.enums.ExecuteEngineEnum;
import org.dromara.sqlrest.common.enums.HttpMethodEnum;
import org.dromara.sqlrest.common.enums.NamingStrategyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("API configuration")
public class ApiAssignmentSaveRequest {

  @ApiModelProperty("ID number (used when saving interface)")
  private Long id;

  @NotNull(message = "groupId cannot be null")
  @ApiModelProperty("Group ID")
  private Long groupId;

  @NotNull(message = "moduleId cannot be null")
  @ApiModelProperty("Module ID")
  private Long moduleId;

  @NotNull(message = "datasourceId cannot be null")
  @ApiModelProperty("Datasource ID")
  private Long datasourceId;

  @NotBlank(message = "name cannot be empty")
  @ApiModelProperty("API configuration name")
  private String name;

  @ApiModelProperty("Description")
  private String description;

  @NotNull(message = "method cannot be null")
  @ApiModelProperty("API request method: GET, HEAD, PUT, POST, DELETE")
  private HttpMethodEnum method;

  @NotBlank(message = "contentType cannot be empty")
  @ApiModelProperty("HTTP request contentType")
  private String contentType;

  @NotBlank(message = "path cannot be empty")
  @ApiModelProperty("Request path (without api prefix)")
  private String path;

  @NotNull(message = "open cannot be null")
  @ApiModelProperty("Whether public")
  private Boolean open;

  @NotNull(message = "alarm cannot be null")
  @ApiModelProperty("Whether alarm is enabled")
  private Boolean alarm;

  @NotNull(message = "engine cannot be null")
  @ApiModelProperty("Execution engine: SQL, SCRIPT")
  private ExecuteEngineEnum engine;

  @NotEmpty(message = "contextList cannot be empty")
  @ApiModelProperty("SQL list")
  private List<String> contextList;

  @ApiModelProperty("API input parameters list")
  private List<ItemParam> params;

  @ApiModelProperty("API output parameters list")
  private List<OutParam> outputs;

  @ApiModelProperty("API output data type conversion format")
  private List<DataTypeFormatMapValue> formatMap;

  @NotNull(message = "namingStrategy cannot be null")
  @ApiModelProperty("API output attribute naming strategy")
  private NamingStrategyEnum namingStrategy;

  @NotNull(message = "flowStatus cannot be null")
  @ApiModelProperty("Whether flow control is enabled")
  private Boolean flowStatus;

  @ApiModelProperty("Threshold type")
  private Integer flowGrade;

  @ApiModelProperty("Single machine threshold")
  private Integer flowCount;

  @ApiModelProperty("Cache key type")
  private CacheKeyTypeEnum cacheKeyType;

  @TableField("Cache expression")
  private String cacheKeyExpr;

  @TableField("Cache expiration duration")
  private Long cacheExpireSeconds;
}
