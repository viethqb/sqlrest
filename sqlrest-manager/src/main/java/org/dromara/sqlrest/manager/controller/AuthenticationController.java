// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.manager.controller;

import org.dromara.sqlrest.common.dto.ResultEntity;
import org.dromara.sqlrest.core.dto.UserLoginRequest;
import org.dromara.sqlrest.core.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Login Authentication API"})
@RestController
@RequestMapping(value = "/user")
public class AuthenticationController {

  @Resource
  private SystemUserService systemUserService;

  @ApiOperation(value = "User login", notes = "Login with username and password")
  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity login(@Valid @RequestBody UserLoginRequest request) {
    return ResultEntity.success(systemUserService.login(request.getUsername(), request.getPassword()));
  }

  @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "User logout", notes = "Logout from system")
  @ApiImplicitParams({
      @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "Token identifier", required = true)
  })
  public ResultEntity logout(HttpServletRequest request) {
    systemUserService.logout(request);
    return ResultEntity.success();
  }
}
