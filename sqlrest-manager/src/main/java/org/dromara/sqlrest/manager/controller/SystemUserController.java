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

import org.dromara.sqlrest.common.consts.Constants;
import org.dromara.sqlrest.common.dto.ResultEntity;
import org.dromara.sqlrest.core.dto.SystemUserDetailResponse;
import org.dromara.sqlrest.core.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User Management API"})
@RestController
@RequestMapping(value = Constants.MANGER_API_V1 + "/user")
public class SystemUserController {

  @Resource
  private SystemUserService systemUserService;

  @ApiOperation(value = "User details", notes = "Get user details by user ID")
  @GetMapping(value = "/detail/id", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity<SystemUserDetailResponse> getUserById(@RequestParam("id") Long id) {
    return ResultEntity.success(systemUserService.getUserDetailById(id));
  }

  @ApiOperation(value = "User details", notes = "Get user details by username")
  @GetMapping(value = "/detail/name", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity<SystemUserDetailResponse> getUserByName(@RequestParam("username") String username) {
    return ResultEntity.success(systemUserService.getUserDetailByUsername(username));
  }

  @ApiOperation(value = "Change password", notes = "User changes their own password")
  @RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity changeOwnPassword(HttpServletRequest request,
      @RequestParam("oldPassword") String oldPassword,
      @RequestParam("newPassword") String newPassword) {
    systemUserService.changeOwnPassword(request, oldPassword, newPassword);
    return ResultEntity.success();
  }

}
