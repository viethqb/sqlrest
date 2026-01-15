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

import org.dromara.sqlrest.common.dto.ParamValue;
import org.dromara.sqlrest.common.enums.ExecuteEngineEnum;
import org.dromara.sqlrest.common.enums.NamingStrategyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("SQL debug execution")
public class ApiDebugExecuteRequest {

  @NotNull(message = "datasourceId cannot be null")
  @ApiModelProperty("Datasource ID")
  private Long dataSourceId;

  @NotNull(message = "engine cannot be null")
  @ApiModelProperty("Execution engine: SQL, SCRIPT")
  private ExecuteEngineEnum engine;

  @ApiModelProperty("Data type conversion format")
  private List<DataTypeFormatMapValue> formatMap;

  @NotNull(message = "namingStrategy cannot be null")
  @ApiModelProperty("Attribute naming strategy")
  private NamingStrategyEnum namingStrategy;

  @NotEmpty(message = "contextList cannot be empty")
  @ApiModelProperty("SQL list")
  private List<String> contextList;

  @ApiModelProperty("API input parameters list")
  private List<ParamValue> paramValues;
}
