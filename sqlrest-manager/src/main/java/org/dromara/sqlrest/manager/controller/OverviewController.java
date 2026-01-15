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
import org.dromara.sqlrest.common.dto.DateCount;
import org.dromara.sqlrest.common.dto.NameCount;
import org.dromara.sqlrest.common.dto.PageResult;
import org.dromara.sqlrest.common.dto.ResultEntity;
import org.dromara.sqlrest.core.dto.ApiAccessLogBasicResponse;
import org.dromara.sqlrest.core.service.OverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Statistics Related API"})
@RestController
@RequestMapping(value = Constants.MANGER_API_V1 + "/overview")
public class OverviewController {

  @Resource
  private OverviewService overviewService;

  @ApiOperation(value = "Count statistics")
  @GetMapping(value = "/counter", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity count() {
    return ResultEntity.success(overviewService.count());
  }

  @ApiOperation(value = "Trend statistics")
  @GetMapping(value = "/trend/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity<List<DateCount>> trend(@PathVariable("days") Integer days) {
    return ResultEntity.success(overviewService.trend(days));
  }

  @ApiOperation(value = "HTTP status statistics")
  @GetMapping(value = "/ratio/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity httpStatus(@PathVariable("days") Integer days) {
    return ResultEntity.success(overviewService.httpStatus(days));
  }

  @ApiOperation(value = "Top paths")
  @GetMapping(value = "/top/path/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity<List<NameCount>> topPath(@PathVariable("days") Integer days, @RequestParam("n") Integer n) {
    return ResultEntity.success(overviewService.topPath(days, n));
  }

  @ApiOperation(value = "Top addresses")
  @GetMapping(value = "/top/addr/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity<List<NameCount>> topAddr(@PathVariable("days") Integer days, @RequestParam("n") Integer n) {
    return ResultEntity.success(overviewService.topAddr(days, n));
  }

  @ApiOperation(value = "Top clients")
  @GetMapping(value = "/top/client/{days}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity<List<NameCount>> topClient(@PathVariable("days") Integer days, @RequestParam("n") Integer n) {
    return ResultEntity.success(overviewService.topClient(days, n));
  }

  @ApiOperation(value = "API call logs")
  @GetMapping(value = "/log/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<ApiAccessLogBasicResponse> callLogs(@PathVariable("id") Long id, @RequestParam("page") Integer page,
      @RequestParam("size") Integer size) {
    return overviewService.pageByApiId(id, page, size);
  }
}
