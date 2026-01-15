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
import org.dromara.sqlrest.common.enums.ProductTypeEnum;
import org.dromara.sqlrest.core.driver.DriverLoadService;
import org.dromara.sqlrest.core.dto.DataSourceBaseRequest;
import org.dromara.sqlrest.core.dto.DataSourceSaveRequest;
import org.dromara.sqlrest.core.dto.DatasourceDetailResponse;
import org.dromara.sqlrest.core.dto.EntityIdNameResponse;
import org.dromara.sqlrest.core.dto.EntitySearchRequest;
import org.dromara.sqlrest.core.service.DataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Datasource Management API"})
@RestController
@RequestMapping(value = Constants.MANGER_API_V1 + "/datasource")
public class DataSourceController {

  @Resource
  private DataSourceService datasourceService;
  @Resource
  private DriverLoadService driverLoadService;

  @ApiOperation(value = "Database types")
  @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity getTypes() {
    return ResultEntity.success(datasourceService.getTypes());
  }

  @ApiOperation(value = "Database drivers")
  @GetMapping(value = "/{type}/drivers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity getDrivers(@PathVariable("type") ProductTypeEnum type) {
    return ResultEntity.success(driverLoadService.getDrivers(type));
  }

  @ApiOperation(value = "Connection pre-test")
  @PostMapping(value = "/preTest", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity preTest(@Valid @RequestBody DataSourceBaseRequest request) {
    datasourceService.preTest(request);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Datasource list")
  @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<DatasourceDetailResponse> getConnections(@RequestBody EntitySearchRequest request) {
    return datasourceService.searchList(request);
  }

  @ApiOperation(value = "Datasource details")
  @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity getDetail(@PathVariable("id") Long id) {
    return ResultEntity.success(datasourceService.getDetailById(id));
  }

  @ApiOperation(value = "Test datasource connection")
  @GetMapping(value = "/test/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity test(@PathVariable("id") Long id) {
    datasourceService.testDataSource(id);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Add datasource")
  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity create(@Valid @RequestBody DataSourceSaveRequest request) {
    datasourceService.createDataSource(request);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Update datasource")
  @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity update(@Valid @RequestBody DataSourceSaveRequest request) {
    datasourceService.updateDataSource(request);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Delete datasource")
  @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity delete(@PathVariable("id") Long id) {
    datasourceService.deleteDataSource(id);
    return ResultEntity.success();
  }

  @ApiOperation(value = "Datasource name list")
  @GetMapping(value = "/list/name", produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResult<EntityIdNameResponse> getNameList(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size) {
    return datasourceService.getDataSourceNames(page, size);
  }

  @ApiOperation(value = "Query schema list of datasource")
  @GetMapping(value = "/schemas/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity getSchemas(@PathVariable("id") Long id) {
    return ResultEntity.success(datasourceService.getDatasourceSchemas(id));
  }

  @ApiOperation(value = "Query all physical table list of datasource under specified schema")
  @GetMapping(value = "/tables/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity getSchemaTables(@PathVariable("id") Long id,
      @RequestParam("schema") String schema) {
    return ResultEntity.success(datasourceService.getSchemaTables(id, schema));
  }

  @ApiOperation(value = "Query all view table list of datasource under specified schema")
  @GetMapping(value = "/views/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity getSchemaViews(@PathVariable("id") Long id,
      @RequestParam("schema") String schema) {
    return ResultEntity.success(datasourceService.getSchemaViews(id, schema));
  }

  @ApiOperation(value = "Query column information of specified table in datasource")
  @GetMapping(value = "/columns/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResultEntity getTableColumns(@PathVariable("id") Long id,
      @RequestParam("schema") String schema, @RequestParam("table") String table) {
    return ResultEntity.success(datasourceService.getTableColumns(id, schema, table));
  }

}
