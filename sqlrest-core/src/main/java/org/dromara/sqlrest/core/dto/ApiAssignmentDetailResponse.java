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
import org.dromara.sqlrest.common.enums.NamingStrategyEnum;
import org.dromara.sqlrest.persistence.entity.ApiContextEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("API configuration detailed information")
public class ApiAssignmentDetailResponse extends ApiAssignmentBaseResponse {

  @ApiModelProperty("Group ID")
  private Long groupId;

  @ApiModelProperty("Module ID")
  private Long moduleId;

  @ApiModelProperty("Datasource ID")
  private Long datasourceId;

  @ApiModelProperty("Description")
  private String description;

  @ApiModelProperty("API input parameters")
  private List<ItemParam> params;

  @ApiModelProperty("API output parameters")
  private List<OutParam> outputs;

  @ApiModelProperty("HTTP request contentType")
  private String contentType;

  @ApiModelProperty("SQL list")
  private List<ApiContextEntity> sqlList;

  @ApiModelProperty("API output data type conversion format")
  private List<DataTypeFormatMapValue> formatMap;

  @ApiModelProperty("API output attribute naming strategy")
  private NamingStrategyEnum namingStrategy;

  @ApiModelProperty("Whether flow control is enabled")
  private Boolean flowStatus;

  @ApiModelProperty("Threshold type")
  private Integer flowGrade;

  @TableField("Threshold size")
  private Integer flowCount;

  @ApiModelProperty("Cache key type")
  private CacheKeyTypeEnum cacheKeyType;

  @ApiModelProperty("Cache expression")
  private String cacheKeyExpr;

  @ApiModelProperty("Cache expiration duration")
  private Long cacheExpireSeconds;
}
