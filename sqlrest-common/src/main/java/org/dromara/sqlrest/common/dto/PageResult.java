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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Pagination result")
@NoArgsConstructor
@Data
public class PageResult<E> implements Serializable {

  @ApiModelProperty("Status code")
  private Integer code = 0;

  @ApiModelProperty("Status description")
  private String message = "success";

  @ApiModelProperty("Pagination information")
  private Pagination pagination;

  @ApiModelProperty("Data")
  private List<E> data;

  @ApiModel(description = "Pagination information")
  @NoArgsConstructor
  @Data
  public static class Pagination {

    @ApiModelProperty("Page number")
    private int page;

    @ApiModelProperty("Total records")
    private int total;

    @ApiModelProperty("Page size")
    private int size;
  }

}
