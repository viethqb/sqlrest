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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Assignment list search")
public class AssignmentSearchRequest extends EntitySearchRequest {

  @ApiModelProperty("Whether online")
  private Boolean online;

  @ApiModelProperty("Group ID")
  private Long groupId;

  @ApiModelProperty("Module ID")
  private Long moduleId;

  @ApiModelProperty("Whether public")
  private Boolean open;
}
