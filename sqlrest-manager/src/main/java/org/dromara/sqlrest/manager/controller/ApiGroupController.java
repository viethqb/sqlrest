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
import org.dromara.sqlrest.common.dto.PageResult;
import org.dromara.sqlrest.common.dto.ResultEntity;
import org.dromara.sqlrest.core.dto.EntitySearchRequest;
import org.dromara.sqlrest.core.service.ApiGroupService;
import org.dromara.sqlrest.persistence.entity.ApiGroupEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Group Management API"})
@RestController
@RequestMapping(value = Constants.MANGER_API_V1 + "/group")
public class ApiGroupController {

  @Resource
  private ApiGroupService apiGroupService;

  @ApiOperation(value = "Add group")
  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity create(@Valid @NotBlank(message = "name cannot be empty") @RequestParam("name") String name) {
    apiGroupService.createGroup(name);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Update group")
  @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity update(@PathVariable("id") Long id,
      @Valid @NotBlank(message = "name cannot be empty") @RequestParam("name") String name) {
    apiGroupService.updateGroup(id, name);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Delete group")
  @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity delete(@PathVariable("id") Long id) {
    apiGroupService.deleteGroup(id);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Group list")
  @PostMapping(value = "/listAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<ApiGroupEntity> listAll(@RequestBody EntitySearchRequest request) {
    return apiGroupService.listAll(request);
  }
}
