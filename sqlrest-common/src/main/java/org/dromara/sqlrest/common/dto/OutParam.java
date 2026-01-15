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

import org.dromara.sqlrest.common.enums.ParamTypeEnum;
import org.dromara.sqlrest.common.exception.CommonException;
import org.dromara.sqlrest.common.exception.ResponseErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Output parameter information")
public class OutParam implements Serializable {

  @ApiModelProperty("ID (generated and used by frontend)")
  private String id;

  @ApiModelProperty("Parameter name")
  private String name;

  @ApiModelProperty("Parameter type")
  private ParamTypeEnum type;

  @ApiModelProperty("Whether it is an array")
  private Boolean isArray;

  @ApiModelProperty("Parameter description")
  private String remark;

  @ApiModelProperty("Child elements of Object type")
  private List<OutParam> children;

  public void checkValid() {
    if (StringUtils.isBlank(getName())) {
      throw new CommonException(ResponseErrorCode.ERROR_INTERNAL_ERROR, "output parameter name must is not blank");
    }
    if (null == getType()) {
      throw new CommonException(ResponseErrorCode.ERROR_INTERNAL_ERROR, "output parameter type must is not empty");
    }

    if (getType() == ParamTypeEnum.OBJECT) {
      if (null != children && children.size() > 0) {
        for (OutParam param : children) {
          if (StringUtils.isBlank(param.getName())) {
            throw new CommonException(ResponseErrorCode.ERROR_INTERNAL_ERROR,
                "output parameter name must is not blank");
          }
        }
      } else {
        throw new CommonException(ResponseErrorCode.ERROR_INVALID_ARGUMENT,
            "Object output param '" + getName() + "' must have child parameter.");
      }
    } else {
      children = Collections.emptyList();
    }
  }
}
