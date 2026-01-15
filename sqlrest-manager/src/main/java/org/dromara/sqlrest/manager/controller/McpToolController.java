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
import org.dromara.sqlrest.core.dto.McpToolResponse;
import org.dromara.sqlrest.core.dto.McpToolSaveRequest;
import org.dromara.sqlrest.manager.service.McpManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"MCP Tool Management API"})
@RestController
@RequestMapping(value = Constants.MANGER_API_V1 + "/mcp/tool")
public class McpToolController {

  @Resource
  private McpManageService mcpManageService;

  @ApiOperation(value = "Add MCP tool")
  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity create(@Valid @RequestBody McpToolSaveRequest request) {
    mcpManageService.createTool(request);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Update MCP tool")
  @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity update(@Valid @RequestBody McpToolSaveRequest request) {
    mcpManageService.updateTool(request);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Delete MCP tool")
  @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity delete(@PathVariable("id") Long id) {
    mcpManageService.deleteTool(id);
    return ResultEntity.success();
  }

  @ApiOperation(value = "MCP tool list")
  @PostMapping(value = "/listAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<McpToolResponse> listAll(@RequestBody EntitySearchRequest request) {
    return mcpManageService.listToolAll(request);
  }
}
