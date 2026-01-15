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

import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.json.JSONUtil;
import lombok.Getter;

@Getter
public enum ParamTypeEnum {
  LONG("Integer", "number", 0L, Long.class, (String str) -> StringUtils.isNotBlank(str) ? Long.valueOf(str) : null),
  DOUBLE("Double", "number", 0D, Double.class, (String str) -> StringUtils.isNotBlank(str) ? Double.valueOf(str) : null),
  STRING("String", "string", "", String.class, (String str) -> str),
  DATE("Date", "string", "", String.class, (String str) -> str),
  TIME("Time", "string", "", String.class, (String str) -> str),
  //fix: Display boolean type directly in swagger
  BOOLEAN("Boolean", "boolean", "true", Boolean.class, (String str) -> StringUtils.isNotBlank(str) ? Boolean.parseBoolean(str): null),
  //fix: Fix issue where default value is empty string when object is not passed
  OBJECT("Object", "object", "{}", Map.class, (String str) -> StringUtils.isNotBlank(str) ? JSONUtil.toBean(str, Map.class) : null);

  private String name;
  private String jsType;
  private Object example;
  private Class clazz;
  private Function<String, Object> converter;

  ParamTypeEnum(String name, String jsType, Object example, Class clazz, Function<String, Object> converter) {
    this.name = name;
    this.jsType = jsType;
    this.example = example;
    this.clazz = clazz;
    this.converter = converter;
  }

  public boolean isObject() {
    return OBJECT == this;
  }
}
