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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.dromara.sqlrest.common.consts.Constants;
import org.dromara.sqlrest.common.dto.ResultEntity;
import org.dromara.sqlrest.core.dto.VersionCommitResponse;
import org.dromara.sqlrest.core.service.ApiAssignmentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Version Control API"})
@RestController
@RequestMapping(value = Constants.MANGER_API_V1 + "/version")
public class VersionCommitController {

  @Resource
  private ApiAssignmentService apiAssignmentService;

  @ApiOperation(value = "Query version list")
  @GetMapping(value = "/list/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity<List<VersionCommitResponse>> listVersions(@PathVariable("id") Long bizId) {
    return ResultEntity.success(apiAssignmentService.listVersions(bizId));
  }

  @ApiOperation(value = "Query version details")
  @GetMapping(value = "/show/{commitId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity showVersion(@PathVariable("commitId") Long commitId) {
    return ResultEntity.success(apiAssignmentService.showVersion(commitId));
  }

  @ApiOperation(value = "Revert to specified version")
  @GetMapping(value = "/revert/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity revertVersion(@PathVariable("id") Long bizId, @RequestParam("commitId") Long commitId) {
    apiAssignmentService.revertVersion(bizId, commitId);
    return ResultEntity.success();
  }
}
