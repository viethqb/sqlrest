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

public enum CacheKeyTypeEnum {

  NONE(false, "Cache disabled, no need to generate cache KEY"),
  AUTO(true, "Auto-generate cache KEY based on input parameters"),
  SPEL(true, "Use SpEL expression to generate cache KEY"),
  ;

  private boolean useCache;
  private String name;

  CacheKeyTypeEnum(boolean cache, String name) {
    this.useCache = cache;
    this.name = name;
  }

  public boolean isUseCache() {
    return useCache;
  }

  public String getName() {
    return name;
  }
}
