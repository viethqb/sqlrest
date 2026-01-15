// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.common.enums;

import cn.hutool.core.util.StrUtil;
import java.util.function.Function;

public enum NamingStrategyEnum {
  NONE(Function.identity(), "No conversion"),
  CAMEL_CASE(StrUtil::toCamelCase, "Convert property names to camelCase"),
  SNAKE_CASE(StrUtil::toUnderlineCase, "Convert property names to snake_case"),
  LOWER_CASE(String::toLowerCase, "Convert property names to lowercase"),
  UPPER_CASE(String::toUpperCase, "Convert property names to uppercase"),
  ;
  private Function<String, String> function;
  private String description;

  NamingStrategyEnum(Function<String, String> function, String description) {
    this.function = function;
    this.description = description;
  }

  public Function<String, String> getFunction() {
    return function;
  }

  public String getDescription() {
    return description;
  }
}
