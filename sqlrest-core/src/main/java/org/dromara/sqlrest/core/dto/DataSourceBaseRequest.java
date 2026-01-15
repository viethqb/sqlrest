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

import org.dromara.sqlrest.common.enums.ProductTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSourceBaseRequest {
  
  @NotBlank(message = "name cannot be empty")
  @ApiModelProperty("Name")
  private String name;

  @NotNull(message = "type cannot be null")
  @ApiModelProperty("Type")
  private ProductTypeEnum type;

  @NotBlank(message = "version cannot be empty")
  @ApiModelProperty("Driver version")
  private String version;

  @NotBlank(message = "driver cannot be empty")
  @ApiModelProperty("Driver type")
  private String driver;

  @NotBlank(message = "url cannot be empty")
  @ApiModelProperty("JDBC connection URL")
  private String url;

  @NotBlank(message = "username cannot be empty")
  @ApiModelProperty("Username")
  private String username;

  @ApiModelProperty("Password")
  private String password;
}
