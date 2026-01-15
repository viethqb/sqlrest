// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.core.exec.module;

import java.util.Map;
import org.dromara.sqlrest.common.service.VarModuleInterface;
import org.dromara.sqlrest.core.exec.annotation.Comment;
import org.dromara.sqlrest.core.exec.annotation.Module;

@Module(ReqVarModule.VAR_NAME)
public class ReqVarModule implements VarModuleInterface {

  protected static final String VAR_NAME = "req";

  private Map<String, Object> params;

  public ReqVarModule(Map<String, Object> params) {
    this.params = params;
  }

  @Override
  public String getVarModuleName() {
    return VAR_NAME;
  }

  @Comment("Set a request parameter. If a parameter with the same name exists, it will be overwritten")
  public void setParam(@Comment("name") String name, @Comment("value") Object value) {
    this.params.put(name, value);
  }
}
