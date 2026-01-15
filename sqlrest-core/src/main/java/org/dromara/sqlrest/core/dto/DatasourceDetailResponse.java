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
import org.dromara.sqlrest.common.enums.ProductTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@ApiModel("Datasource details")
public class DatasourceDetailResponse {

  @ApiModelProperty("ID number")
  private Long id;

  @ApiModelProperty("Title")
  private String name;

  @ApiModelProperty("Database type")
  private ProductTypeEnum type;

  @ApiModelProperty("Driver version")
  private String version;

  @ApiModelProperty("Driver class")
  private String driver;

  @ApiModelProperty("URL connection string")
  private String url;

  @ApiModelProperty("Username")
  private String username;

  @ApiModelProperty("Password")
  private String password;

  @ApiModelProperty("Create time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp createTime;

  @ApiModelProperty("Update time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Timestamp updateTime;
}
