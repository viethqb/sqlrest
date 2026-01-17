<template>
  <el-card>
    <el-row>
      <el-col :span="24">
        <div style="float:right">
          <el-button type="danger"
                     v-if="showVersionDetail"
                     size="mini"
                     @click="handleExitShowVersionDetail">
            <i class="el-icon-question">
              Exit Version View</i>
          </el-button>

          <el-button type="warning"
                     size="mini"
                     @click="handleHelp">
            <i class="el-icon-question">
              Help</i>
          </el-button>
          <el-button type="primary"
                     size="mini"
                     @click="handleGoBack">
            <i class="el-icon-d-arrow-left">
              Back</i>
          </el-button>

          <el-button type="primary"
                     size="mini"
                     v-if="!isOnlyShowDetail"
                     icon="el-icon-arrow-left"
                     @click="handleDebug">
            Debug
          </el-button>
          <el-button type="primary"
                     size="mini"
                     v-if="!isOnlyShowDetail"
                     icon="el-icon-arrow-left"
                     @click="handleSave">
            Save
          </el-button>
          <el-button type="primary"
                     size="mini"
                     v-if="isOnlyShowDetail"
                     icon="el-icon-top"
                     @click="handleShowVersionList">
            Version
          </el-button>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter=2>
      <el-col :span="24">
        <div class="grid-content bg-purple">
          <el-form size="mini"
                   :model="createParam"
                   :rules="rules"
                   label-position='left'
                   ref="form">
            <el-tabs type="border-card"
                     v-model="tabActiveName"
                     tab-position="left">
              <el-tab-pane label="API Configuration"
                           name="detail">
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="Path"
                                  label-width="150px"
                                  :required=true
                                  prop="path">
                      <el-input v-model="createParam.path"
                                size="small"
                                :disabled="isOnlyShowDetail || $route.query.id>0">
                        <template slot="prepend">{{gatewayApiPrefix}}</template>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="Method"
                                  label-width="150px"
                                  :required=true
                                  prop="method">
                      <el-select v-model="createParam.method"
                                 size="small"
                                 style="width: 400px;"
                                 :disabled="isOnlyShowDetail || $route.query.id>0">
                        <el-option label="GET"
                                   value="GET"></el-option>
                        <el-option label="PUT"
                                   value="PUT"></el-option>
                        <el-option label="POST"
                                   value="POST"></el-option>
                        <el-option label="DELETE"
                                   value="DELETE"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="Name"
                                  label-width="150px"
                                  :required=true
                                  prop="name">
                      <el-input v-model="createParam.name"
                                auto-complete="off"
                                size="small"
                                style="width: 400px;"
                                :disabled="isOnlyShowDetail"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="Content Type"
                                  label-width="150px"
                                  :required=true
                                  prop="contentType">
                      <el-select v-model="createParam.contentType"
                                 size="small"
                                 style="width: 400px;"
                                 :disabled="isOnlyShowDetail">
                        <el-option v-for="(item,index) in contentTypes"
                                   :key="index"
                                   :label="item"
                                   :value="item"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="Module"
                                  label-width="150px"
                                  :required=true
                                  prop="module">
                      <el-select v-model="createParam.module"
                                 placeholder="Please select"
                                 size="small"
                                 style="width: 400px;"
                                 :disabled="isOnlyShowDetail">
                        <el-option v-for="(item,index) in moduleList"
                                   :key="index"
                                   :label="item.name"
                                   :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="Authorization Group"
                                  label-width="150px"
                                  :required=true
                                  prop="group">
                      <el-select v-model="createParam.group"
                                 placeholder="Please select"
                                 size="small"
                                 style="width: 400px;"
                                 :disabled="isOnlyShowDetail">
                        <el-option v-for="(item,index) in groupList"
                                   :key="index"
                                   :label="item.name"
                                   :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="24">
                    <el-form-item label="Description"
                                  label-width="150px"
                                  prop="description">
                      <el-input type="textarea"
                                v-model="createParam.description"
                                auto-complete="off"
                                size="small"
                                style="width: 900px;"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="SQL Configuration"
                           name="basic">
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-form-item label="DataSource"
                                  label-width="90px"
                                  prop="dataSourceId">
                      <el-select placeholder="Please select datasource"
                                 v-model="createParam.dataSourceId"
                                 style="width: 100%;"
                                 size="small">
                        <el-option v-for="(item,index) in connectionList"
                                   :key="index"
                                   :label="item.name"
                                   :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="Engine"
                                  label-width="65px">
                      <el-radio-group size="small"
                                      @change="agreeEngineChange"
                                      v-model="createParam.engine">
                        <el-radio-button label="SQL"
                                         :disabled="$route.query.id>0 && createParam.engine==='SCRIPT'">SQL Statement</el-radio-button>
                        <el-radio-button label="SCRIPT"
                                         :disabled="$route.query.id>0 && createParam.engine==='SQL'">Groovy Script</el-radio-button>
                      </el-radio-group>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="Editor Height"
                                  label-width="100px">
                    <el-input-number v-model="editorHeightNum"
                                     size="small"
                                     :step="20"
                                     step-strictly></el-input-number>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row v-if="createParam.engine==='SQL'">
                  <el-col :span="24">
                    <el-form-item label-width="65px">
                      <span slot="label"
                            style="display:inline-block;">
                        Statement
                        <el-tooltip effect="dark"
                                    content="Write at most one SQL statement per SQL window, supports MyBatis dynamic SQL syntax"
                                    placement="bottom">
                          <i class='el-icon-question' />
                        </el-tooltip>
                      </span>
                      <multi-sql-editer ref="sqlEditors"
                                        :editorHeightNum="editorHeightNum"
                                        :tableHints="tableHints"
                                        :tabSqls="createParam.sqls"
                                        :canAddSql="!isOnlyShowDetail"></multi-sql-editer>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row v-if="createParam.engine==='SCRIPT'">
                  <el-col :span="24">
                    <el-form-item label-width="65px">
                      <span slot="label"
                            style="display:inline-block;">
                        Script
                        <el-tooltip effect="dark"
                                    content="You can write script content that conforms to Groovy syntax format"
                                    placement="bottom">
                          <i class='el-icon-question' />
                        </el-tooltip>
                      </span>
                      <script-editer ref="scriptEditer"
                                     :editorHeightNum="editorHeightNum"
                                     :content="createParam.script"></script-editer>
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-tabs type="border-card"
                         tab-position="left">
                  <el-tab-pane label="Input Parameters">
                    <el-row style="margin-bottom: 15px; display: flex; gap: 10px;">
                      <el-button type="primary"
                                 size="small"
                                 v-if="createParam.engine==='SQL' && !isOnlyShowDetail"
                                 @click="handleParseInputParams">
                        Parse Input Params
                      </el-button>
                      <el-button type="primary"
                                 size="small"
                                 v-if="!isOnlyShowDetail"
                                 @click="handleAddInputParams">
                        Add Input Param
                      </el-button>
                      <el-button type="primary"
                                 size="small"
                                 v-if="!isOnlyShowDetail"
                                 @click="handleAddPagableParams">
                        Pagination Params
                      </el-button>
                    </el-row>
                    <el-table :data="inputParams"
                              size="small"
                              border
                              default-expand-all
                              row-key="id"
                              :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
                      <!--If not lazy loading, backend should not set hasChildren property, otherwise tree display will not work; -->
                      <template slot="empty">
                        <span>Please enter SQL and click "Parse Input Params" button to parse input parameters here</span>
                      </template>
                      <el-table-column min-width="14%">
                        <template slot="header">
                          <span style="white-space: nowrap;">Param Name</span>
                        </template>
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.name"
                                    type="string"
                                    size="small"
                                    :disabled="isOnlyShowDetail"> </el-input>
                        </template>
                      </el-table-column>
                      <el-table-column min-width="14%">
                        <template slot="header">
                          <span style="white-space: nowrap;">Param Location</span>
                        </template>
                        <template slot-scope="scope">
                          <el-select v-model="scope.row.location"
                                     size="small"
                                     :disabled="isOnlyShowDetail">
                            <el-option label='header'
                                       value='REQUEST_HEADER'></el-option>
                            <el-option label='body'
                                       value='REQUEST_BODY'></el-option>
                            <el-option label='query'
                                       value='REQUEST_FORM'></el-option>
                          </el-select>
                        </template>
                      </el-table-column>
                      <el-table-column min-width="11%">
                        <template slot="header">
                          <span style="white-space: nowrap;">Param Type</span>
                        </template>
                        <template slot-scope="scope">
                          <el-select v-model="scope.row.type"
                                     size="small"
                                     :disabled="isOnlyShowDetail">
                            <el-option v-for="(item,index) in paramTypeList"
                                       :key="index"
                                       :label="item.name"
                                       :value="item.value"
                                       v-if="shouldInputShowOption(item,scope.row)"></el-option>
                          </el-select>
                        </template>
                      </el-table-column>
                      <el-table-column min-width="7%"
                                       align="center">
                        <template slot="header">
                          <span style="white-space: nowrap;">Array</span>
                        </template>
                        <template slot-scope="scope">
                          <el-checkbox v-model="scope.row.isArray"
                                       :disabled="isOnlyShowDetail"></el-checkbox>
                        </template>
                      </el-table-column>
                      <el-table-column min-width="9%"
                                       align="center">
                        <template slot="header">
                          <span style="white-space: nowrap;">Required</span>
                        </template>
                        <template slot-scope="scope">
                          <el-checkbox v-model="scope.row.required"
                                       :disabled="isOnlyShowDetail"></el-checkbox>
                        </template>
                      </el-table-column>
                      <el-table-column min-width="13%">
                        <template slot="header">
                          <span style="white-space: nowrap;">Default Value</span>
                        </template>
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.defaultValue"
                                    type="string"
                                    size="small"
                                    :disabled="isOnlyShowDetail "></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column min-width="13%">
                        <template slot="header">
                          <span style="white-space: nowrap;">Description</span>
                        </template>
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.remark"
                                    type="string"
                                    size="small"
                                    :disabled="isOnlyShowDetail"></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column v-if="!isOnlyShowDetail"
                                       min-width="10%"
                                       align="center">
                        <template slot="header">
                          <span style="white-space: nowrap;">Actions</span>
                        </template>
                        <template slot-scope="scope">
                          <el-link icon="el-icon-plus"
                                   v-if="scope.row.type=='OBJECT' && scope.row.location=='REQUEST_BODY'"
                                   @click="addInputSubParamsItem(scope.row)"></el-link>
                          &nbsp;&nbsp;&nbsp;&nbsp;
                          <el-link icon="el-icon-delete"
                                   @click="deleteInputParamsItem(scope.$index,scope.row)"></el-link>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-tab-pane>
                  <el-tab-pane label="Output Parameters">
                    <el-button type="primary"
                               size="mini"
                               icon="el-icon-arrow-down"
                               v-if="!isOnlyShowDetail"
                               @click="handleAddOutputParams">
                      Add Output Param
                    </el-button>
                    <el-table :data="outputParams"
                              size="small"
                              border
                              default-expand-all
                              row-key="id"
                              :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                              border>
                      <template slot="empty">
                        <span>Please enter SQL and successfully execute "Debug" button to parse output parameters here</span>
                      </template>
                      <el-table-column label="Parameter Name"
                                       min-width="25%">
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.name"
                                    type="string"
                                    :disabled="isOnlyShowDetail"> </el-input>
                        </template>
                      </el-table-column>
                      <el-table-column label="Parameter Type"
                                       min-width="25%">
                        <template slot-scope="scope">
                          <el-select v-model="scope.row.type"
                                     :disabled="isOnlyShowDetail">
                            <el-option v-for="(item,index) in paramTypeList"
                                       :key="index"
                                       :label="item.name"
                                       :value="item.value"
                                       v-if="shouldOutputShowOption(item,scope.row)"></el-option>
                          </el-select>
                        </template>
                      </el-table-column>
                      <el-table-column label="Description"
                                       min-width="25%">
                        <template slot-scope="scope">
                          <el-input v-model="scope.row.remark"
                                    type="string"
                                    :disabled="isOnlyShowDetail"></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column label="Actions"
                                       v-if="!isOnlyShowDetail"
                                       min-width="25%">
                        <template slot-scope="scope">
                          <el-link icon="el-icon-plus"
                                   v-if="scope.row.type=='OBJECT'"
                                   @click="addOutputSubParamsItem(scope.row)"></el-link>
                          &nbsp;&nbsp;&nbsp;&nbsp;
                          <el-link icon="el-icon-delete"
                                   @click="deleteOutputParamsItem(scope.$index,scope.row)"></el-link>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-tab-pane>
                </el-tabs>
              </el-tab-pane>
              <el-tab-pane label="Output Format"
                           name="outputParams">
                <el-row>
                  <el-col :span="12">
                    <div>
                      <el-form-item label-width="120px"
                                    prop="namingStrategy"
                                    style="width:60%">
                        <span slot="label"
                              style="display:inline-block;">
                          Naming Strategy
                          <el-tooltip effect="dark"
                                      content="After modifying the naming strategy, you need to execute the 'Debug' operation again to correct the output parameter list"
                                      placement="top">
                            <i class='el-icon-question' />
                          </el-tooltip>
                        </span>
                        <el-select v-model="createParam.namingStrategy"
                                   placeholder="Please select"
                                   :disabled="isOnlyShowDetail">
                          <el-option v-for="(item,index) in responseNamingStrategy"
                                     :key="index"
                                     :label="`[${item.key}]${item.value}`"
                                     :value="`${item.key}`"></el-option>
                        </el-select>
                      </el-form-item>
                    </div>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="Data Format"
                                  label-width="120px"
                                  prop="formatMap">
                      <div v-for="item in createParam.formatMap"
                           :key="item.key"
                           v-bind="item"
                           style="margin-bottom: 15px; display: flex; align-items: center;">
                        <span style="min-width: 200px; margin-right: 15px;">{{item.remark}}:</span>
                        <el-input type="text"
                                  :key="item.key"
                                  v-model="item.value"
                                  size="small"
                                  style="width: 300px;"
                                  :value="item.value"> </el-input>
                      </div>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="Cache Configuration"
                           name="cacheConfig">
                <el-form-item label="Cache Method"
                              label-width="120px">
                  <el-select v-model="createParam.cacheKeyType"
                             size="small"
                             style="width: 300px;"
                             :disabled="isOnlyShowDetail">
                    <el-option v-for="item in cacheKeyTypeList"
                               :key="item.value"
                               :label="item.name"
                               :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label-width="120px"
                              v-show="createParam.cacheKeyType==='SPEL'">
                  <span slot="label"
                        style="display:inline-block;">
                    SpEL Expression
                    <el-tooltip effect="dark"
                                content="Required, you can use Spring Expression Language (SpEL) to calculate the cache KEY from input parameter values, for example: #deptNo or #dept.deptNo"
                                placement="top">
                      <i class='el-icon-question' />
                    </el-tooltip>
                  </span>
                  <el-input v-model="createParam.cacheKeyExpr"
                            auto-complete="off"
                            size="small"
                            style="width: 600px;"
                            :disabled="isOnlyShowDetail"></el-input>
                </el-form-item>
                <el-form-item label="Expiration Time (seconds)"
                              label-width="120px"
                              v-show="createParam.cacheKeyType==='SPEL'|| createParam.cacheKeyType==='AUTO'">
                  <el-input-number v-model="createParam.cacheExpireSeconds"
                                   size="small"
                                   :min="10"
                                   :step="1"
                                   :disabled="isOnlyShowDetail"
                                   step-strictly></el-input-number>
                </el-form-item>
              </el-tab-pane>
              <el-tab-pane label="Authentication Configuration"
                           name="authen">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="Public">
                      <el-switch v-model="createParam.open"
                                 active-color="#13ce66"
                                 :active-value="true"
                                 :inactive-value="false"
                                 active-text="On"
                                 inactive-text="Off"
                                 :disabled="isOnlyShowDetail">
                      </el-switch>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="Alarm Configuration"
                           name="alarm">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="Alarm">
                      <el-switch v-model="createParam.alarm"
                                 active-color="#13ce66"
                                 :active-value="true"
                                 :inactive-value="false"
                                 active-text="On"
                                 inactive-text="Off"
                                 :disabled="isOnlyShowDetail">
                      </el-switch>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="Flow Control"
                           name="flowControl">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="Flow Control">
                      <el-switch v-model="createParam.flowStatus"
                                 active-color="#13ce66"
                                 :active-value="true"
                                 :inactive-value="false"
                                 active-text="On"
                                 inactive-text="Off"
                                 :disabled="isOnlyShowDetail">
                      </el-switch>
                    </el-form-item>
                    <div v-show="createParam.flowStatus">
                      <el-form-item label="Threshold Type">
                        <el-radio-group size="small"
                                        v-model="createParam.flowGrade"
                                        :disabled="isOnlyShowDetail"
                                        border>
                          <el-radio :label="1">QPS</el-radio>
                          <el-radio :label="0">Concurrent Threads</el-radio>
                        </el-radio-group>
                      </el-form-item>
                      <el-form-item label="Single Machine Threshold">
                        <el-input-number v-model="createParam.flowCount"
                                         size="small"
                                         :step="1"
                                         :disabled="isOnlyShowDetail"
                                         step-strictly></el-input-number>
                      </el-form-item>
                    </div>
                  </el-col>
                </el-row>
              </el-tab-pane>
            </el-tabs>
          </el-form>
        </div>
      </el-col>
    </el-row>

    <el-drawer title="API Debug"
               :visible.sync="showDebugDrawer"
               direction="ltr"
               size="65%"
               :with-header="true">
      <el-card>
        <el-row>
          <el-col :span="24">
            <el-table :data="debugParams"
                      size="small"
                      border
                      row-key="id"
                      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                      default-expand-all>
              <el-table-column min-width="20%">
                <template slot="header">
                  <span style="white-space: nowrap;">Param Name</span>
                </template>
                <template slot-scope="scope">
                  <el-input v-model="scope.row.name"
                            :disabled="true"
                            type="string"> </el-input>
                </template>
              </el-table-column>
              <el-table-column min-width="15%">
                <template slot="header">
                  <span style="white-space: nowrap;">Param Type</span>
                </template>
                <template slot-scope="scope">
                  <el-select v-model="scope.row.type"
                             :disabled="true">
                    <el-option v-for="(item,index) in paramTypeList"
                               :key="index"
                               :label="item.name"
                               :value="item.value"></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column min-width="8%"
                               align="center">
                <template slot="header">
                  <span style="white-space: nowrap;">Array</span>
                </template>
                <template slot-scope="scope">
                  <el-checkbox v-model="scope.row.isArray"
                               :disabled="true"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column min-width="10%"
                               align="center">
                <template slot="header">
                  <span style="white-space: nowrap;">Required</span>
                </template>
                <template slot-scope="scope">
                  <el-checkbox v-model="scope.row.required"
                               :disabled="true"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column min-width="15%">
                <template slot="header">
                  <span style="white-space: nowrap;">Description</span>
                </template>
                <template slot-scope="scope">
                  <el-input v-model="scope.row.remark"
                            :disabled="true"
                            type="string"></el-input>
                </template>
              </el-table-column>
              <el-table-column min-width="32%">
                <template slot="header">
                  <span style="white-space: nowrap;">Value</span>
                </template>
                <template slot-scope="scope">
                  <div v-if="scope.row.isArray">
                    <el-row v-if="scope.row.type=='OBJECT'">
                      <el-input v-model="scope.row.value"
                                :disabled="true"
                                type="string"></el-input>
                    </el-row>
                    <el-row v-else
                            :gutter="24">
                      <div style="display: inline-flex;flex-direction: row ;justify-content: left;align-items: center"
                           v-for="(arrayItemValue,arrayItemIndex) in scope.row.arrayValues"
                           :key="arrayItemIndex">
                        <el-col :span="4"><button @click="delArrayValuesItem(scope.row.arrayValues,arrayItemIndex)">-</button></el-col>
                        <el-col :span="16"><el-input v-model="scope.row.arrayValues[arrayItemIndex]"
                                    :disabled="scope.row.type=='OBJECT'"
                                    type="string"></el-input></el-col>
                      </div>
                      <div style="display: inline-flex;flex-direction: row ;justify-content: left;align-items: center">
                        <el-col :span="4"><button @click="addArrayValuesItem(scope.row)">+</button></el-col>
                      </div>
                    </el-row>
                  </div>
                  <div v-else>
                    <el-input v-model="scope.row.value"
                              :disabled="scope.row.type=='OBJECT'"
                              type="string"></el-input>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-tooltip effect="dark"
                        content="Object array input parameter debugging is not currently supported, but the API supports object array input parameters"
                        placement="bottom">
              <i class='el-icon-question' />
            </el-tooltip>
            <div style="float: right; padding: 25px">
              <el-button type="primary"
                         size="mini"
                         icon="el-icon-arrow-left"
                         @click="handleExecuteDebug">
                Execute Debug
              </el-button>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-tabs type="border-card">
              <el-tab-pane label="Execution Result">
                <json-viewer :value="debugResponse"
                             :expand-depth=5
                             copyable
                             boxed
                             sort></json-viewer>
              </el-tab-pane>
              <el-tab-pane label="Execution Info">
                <div class="debug-console-log-text">
                  {{debugConsoleLog}}<br />
                </div>
              </el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </el-card>
    </el-drawer>

    <el-drawer title="Version List"
               :visible.sync="showVersionDrawer"
               direction="ltr"
               size="40%"
               :with-header="true">
      <el-card>
        <el-table :data="versionList"
                  size="small"
                  border>
          <el-table-column label="Version"
                           min-width="10%">
            <template slot-scope="scope">
              V{{ scope.row.version }}
            </template>
          </el-table-column>
          <el-table-column prop="online"
                           label="Online"
                           :formatter="boolFormatOnline"
                           min-width="10%"></el-table-column>
          <el-table-column prop="createTime"
                           label="Time"
                           min-width="30%"> </el-table-column>
          <el-table-column prop="description"
                           label="Description"
                           show-overflow-tooltip
                           min-width="30%"></el-table-column>
          <el-table-column label="View"
                           min-width="10%">
            <template slot-scope="scope">
              <el-link icon="el-icon-view"
                       @click="handleShowVersionDetail(scope.$index, scope.row)"></el-link>
            </template>
          </el-table-column>
          <el-table-column label="Rollback"
                           min-width="10%">
            <template slot-scope="scope">
              <el-link icon="el-icon-male"
                       @click="handleRevertVersionDetail(scope.$index, scope.row)"></el-link>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-drawer>

  </el-card>
</template>

<script>
import multiSqlEditer from '@/components/codeEditer/multiSqlEditer'
import scriptEditer from '@/components/codeEditer/scriptEditer'
import urlencode from "urlencode";
import qs from "qs";
import JsonViewer from 'vue-json-viewer';
import Vue from "vue";

export default {
  name: "common",
  data () {
    return {
      tabActiveName: 'detail',
      groupList: [],
      moduleList: [],
      connectionList: [],
      paramTypeList: [
        { name: "Integer", value: "LONG" },
        { name: "Double", value: "DOUBLE" },
        { name: "String", value: "STRING" },
        { name: "Date", value: "DATE" },
        { name: "Time", value: "TIME" },
        { name: "Boolean", value: "BOOLEAN" },
        { name: "Object", value: "OBJECT" }
      ],
      contentTypes: ['application/x-www-form-urlencoded', 'application/json'],
      cacheKeyTypeList: [
        { name: "Disable Cache", value: "NONE" },
        { name: "Auto Generate Cache Key", value: "AUTO" },
        { name: "SpEL Generate Cache Key", value: "SPEL" },
      ],
      showTree: true,
      editorHeightNum: 300,
      createParam: {
        id: null,
        name: null,
        description: null,
        dataSourceId: null,
        group: null,
        module: null,
        method: null,
        path: null,
        contentType: null,
        engine: 'SQL',
        sqls: [],
        script: '',
        open: false,
        namingStrategy: 'CAMEL_CASE',
        formatMap: null,
        open: false,
        alarm: false,
        flowStatus: false,
        flowGrade: 1,
        flowCount: 5,
        cacheKeyType: 'NONE',
        cacheKeyExpr: '',
        cacheExpireSeconds: '300',
      },
      showDebugDrawer: false,
      gatewayApiPrefix: 'http://127.0.0.1:8081/api/',
      treeData: [],
      props: {
        label: 'label',
        children: 'children',
        disabled: false,
        isLeaf: false
      },
      tableHints: {
        'mysql': ['user']
      },
      keywordHints: [],
      inputParams: [],
      debugParams: [],
      debugResponse: {},
      debugConsoleLog: "",
      outputParams: [],
      responseNamingStrategy: [],
      responseTypeFormat: [],
      showVersionDrawer: false,
      versionList: [],
      showVersionDetail: false,
      rules: {
        name: [
          {
            required: true,
            message: "Name cannot be empty",
            trigger: "blur"
          }
        ],
        dataSourceId: [
          {
            required: true,
            message: "Datasource must be selected",
            trigger: "change"
          }
        ],
        group: [
          {
            required: true,
            message: "Group must be selected",
            trigger: "change"
          }
        ],
        module: [
          {
            required: true,
            message: "Module must be selected",
            trigger: "change"
          }
        ],
        method: [
          {
            required: true,
            message: "Method must be selected",
            trigger: "change"
          }
        ],
        path: [
          {
            required: true,
            message: "Path must be provided",
            trigger: "blur"
          }
        ],
        contentType: [
          {
            required: true,
            message: "Content type must be selected",
            trigger: "change"
          }
        ],
      },
    }
  },
  props: {
    isOnlyShowDetail: {
      type: Boolean,
      default: false
    }
  },
  components: { multiSqlEditer, scriptEditer, JsonViewer },
  methods: {
    uuid: function () {
      var s = [];
      var hexDigits = "0123456789abcdef";
      for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
      }
      s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
      s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
      s[8] = s[13] = s[18] = s[23] = "-";

      var uuid = s.join("");
      return uuid;
    },
    isUpdatePage: function () {
      if (this.$route.query.id) {
        return true;
      }
      return false;
    },
    applyAssignmentDetail: function (detail) {
      let mergedFormatMap = this.mergeFormatMap(detail.formatMap);
      this.createParam = {
        id: detail.id,
        name: detail.name,
        description: detail.description,
        method: detail.method,
        path: detail.path,
        contentType: detail.contentType,
        open: detail.open,
        group: detail.groupId,
        module: detail.moduleId,
        dataSourceId: detail.datasourceId,
        engine: detail.engine,
        sqls: [],
        script: "",
        namingStrategy: detail.namingStrategy,
        formatMap: mergedFormatMap,
        open: detail.open,
        alarm: detail.alarm,
        flowStatus: detail.flowStatus,
        flowGrade: detail.flowGrade,
        flowCount: detail.flowCount,
        cacheKeyType: detail.cacheKeyType,
        cacheKeyExpr: detail.cacheKeyExpr,
        cacheExpireSeconds: detail.cacheExpireSeconds,
      }
      this.inputParams = []
      if (detail.params) {
        this.inputParams = detail.params
        for (let item of this.inputParams) {
          if (!item.id) {
            Vue.set(item, 'id', this.uuid());
          }
        }
      }
      this.outputParams = detail.outputs || [];

      if (detail.sqlList && detail.sqlList.length > 0) {
        if (this.createParam.engine === 'SQL') {
          if (this.$refs.sqlEditors) {
            this.$refs.sqlEditors.resetEditor();
          }
          this.createParam.sqls = detail.sqlList.map(obj => obj['sqlText'])
        } else {
          this.createParam.script = detail.sqlList[0].sqlText
          if (this.$refs.scriptEditer) {
            this.$refs.scriptEditer.resetEditor(this.createParam.script)
          }
        }
      }
    },
    loadAssignmentDetail: function () {
      if (!this.isUpdatePage()) {
        return;
      }
      this.$http.get(
        "/sqlrest/manager/api/v1/assignment/detail/" + this.$route.query.id
      ).then(res => {
        if (0 === res.data.code) {
          this.showTree = false;
          let detail = res.data.data;
          this.applyAssignmentDetail(detail);
        } else {
          if (res.data.message) {
            alert("Query failed: " + res.data.message);
          }
        }
      });
    },
    loadConnections: function () {
      this.connectionList = [];
      this.$http({
        method: "GET",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/datasource/list/name",
      }).then(
        res => {
          if (0 === res.data.code) {
            this.connectionList = res.data.data || [];
          } else {
            if (res.data.message) {
              alert("Failed to load data: " + res.data.message);
              this.connectionList = [];
            }
          }
        }
      );
    },
    loadGroups: function () {
      this.groupList = [];
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/group/listAll",
        data: JSON.stringify({
          page: 1,
          size: 2147483647,
          searchText: null
        })
      }).then(
        res => {
          if (0 === res.data.code) {
            this.groupList = res.data.data || [];
          } else {
            if (res.data.message) {
              alert("Failed to load data: " + res.data.message);
              this.groupList = [];
            }
          }
        }
      );
    },
    loadModules: function () {
      this.moduleList = [];
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/module/listAll",
        data: JSON.stringify({
          page: 1,
          size: 2147483647,
          searchText: null
        })
      }).then(
        res => {
          if (0 === res.data.code) {
            this.moduleList = res.data.data || [];
          } else {
            if (res.data.message) {
              alert("Failed to load data: " + res.data.message);
              this.moduleList = [];
            }
          }
        }
      );
    },
    loadGateway: function () {
      this.$http({
        method: "GET",
        url: "/sqlrest/manager/api/v1/node/prefix"
      }).then(
        res => {
          if (0 === res.data.code) {
            if (res.data.data && typeof res.data.data === 'string') {
              this.gatewayApiPrefix = res.data.data;
            }
          } else {
            if (res.data.message) {
              alert("Failed to load data: " + res.data.message);
            }
          }
        }
      );
    },
    loadKeywordHints: function () {
      this.$http.get(
        "/sqlrest/manager/api/v1/assignment/completions"
      ).then(res => {
        if (0 === res.data.code) {
          this.keywordHints = res.data.data;
        }
      });
    },
    loadResponseNamingStrategy: function () {
      this.$http.get(
        "/sqlrest/manager/api/v1/assignment/response-naming-strategy"
      ).then(res => {
        if (0 === res.data.code) {
          this.responseNamingStrategy = res.data.data;
        }
      });
    },
    loadResponseTypeFormat: function () {
      return this.$http.get(
        "/sqlrest/manager/api/v1/assignment/response-type-format"
      ).then(res => {
        if (0 === res.data.code) {
          this.responseTypeFormat = res.data.data;
          if (!this.createParam.formatMap) {
            this.createParam.formatMap = res.data.data;
          }
        }
      });
    },
    mergeFormatMap: function (existingFormatMap) {

      // If no existing formatMap, return system default directly
      if (!existingFormatMap || existingFormatMap.length === 0) {
        return this.responseTypeFormat || [];
      }

      // If system format not loaded yet, return existing first
      if (!this.responseTypeFormat || this.responseTypeFormat.length === 0) {
        return existingFormatMap;
      }

      // Merge logic: use system format as base, override same key values with existing config
      const merged = [...this.responseTypeFormat];
      const existingMap = {};

      // Create mapping of existing config
      existingFormatMap.forEach(item => {
        existingMap[item.key] = item.value;
      });

      // Override system default values with existing config
      merged.forEach(item => {
        if (existingMap.hasOwnProperty(item.key)) {
          item.value = existingMap[item.key];
        }
      });
      return merged;
    },
    loadTreeData: function () {
      if (this.createParam.dataSourceId && this.createParam.dataSourceId > 0 && this.showTree) {
        this.treeData = []
        setTimeout(() => {
          this.$http({
            method: "GET",
            url: "/sqlrest/manager/api/v1/datasource/schemas/get/" + this.createParam.dataSourceId
          }).then(
            res => {
              if (0 === res.data.code) {
                for (let element of res.data.data) {
                  let obj = new Object();
                  obj['label'] = element;
                  obj['parent'] = null;
                  obj['value'] = element;
                  obj['hasChild'] = true;
                  obj['type'] = 'DATABASE';
                  this.treeData.push(obj);
                }
              } else {
                this.$alert("Failed to load, reason: " + res.data.message, 'Data Load Failed');
              }
            }
          );
        }, 500);
      }
    },
    loadNode: function (node, resolve) {
      setTimeout(() => {
        if (node.level === 1) {
          let tableView = [
            {
              'label': 'Tables',
              'parent': this.createParam.dataSourceId,
              'value': node.label,
              'hasChild': true,
              'type': 'TABLE',
            },
            {
              'label': 'Views',
              'parent': this.createParam.dataSourceId,
              'value': node.label,
              'hasChild': true,
              'type': 'VIEW',
            }
          ]
          resolve(tableView);
        } else if (node.level === 2) {
          this.loadTablesList(resolve, this.createParam.dataSourceId, node.data.value, node.data.type)
        } else if (node.level === 3) {
          this.loadColumnList(resolve, this.createParam.dataSourceId, node.data.value, node.data.label)
        } else {
          resolve([]);
        }
      }, 500);
    },
    loadTablesList: function (resolve, id, schema, type) {
      var tableType = 'VIEW' === type ? 'views' : 'tables'
      this.$http({
        method: "GET",
        url: "/sqlrest/manager/api/v1/datasource/" + tableType + "/get/" + id + "?schema=" + urlencode(schema)
      }).then(
        res => {
          if (0 === res.data.code) {
            let tableList = []
            let nameList = []
            for (let element of res.data.data) {
              let obj = new Object();
              obj['label'] = element;
              obj['parent'] = id;
              obj['value'] = schema;
              obj['hasChild'] = true;
              obj['type'] = type;
              tableList.push(obj);
              nameList.push(element)
            }

            // SQL keyword hints
            if (this.tableHints[schema] && Array.isArray(this.tableHints[schema])) {
              this.tableHints[schema].push(nameList)
            } else {
              this.tableHints[schema] = nameList;
            }
            if (this.$refs.sqlEditors) {
              this.$refs.sqlEditors.setTableHints(this.tableHints)
            }
            return resolve(tableList);
          } else {
            this.$alert("Failed to load, reason: " + res.data.message, 'Data Load Failed');
          }
        }
      );
    },
    loadColumnList: function (resolve, id, schema, table) {
      this.$http({
        method: "GET",
        url: "/sqlrest/manager/api/v1/datasource/columns/get/" + id + "?schema=" + urlencode(schema) + "&table=" + urlencode(table)
      }).then(
        res => {
          if (0 === res.data.code) {
            let columnList = []
            for (let element of res.data.data) {
              let obj = new Object();
              obj['label'] = element.name;
              obj['parent'] = table;
              obj['value'] = element.name;
              obj['hasChild'] = false;
              obj['type'] = element.type;
              columnList.push(obj);
            }
            return resolve(columnList);
          } else {
            this.$alert("Failed to load, reason: " + res.data.message, 'Data Load Failed');
          }
        }
      );
    },
    renderContent (h, { node, data, store }) {
      // https://www.cnblogs.com/zhoushuang0426/p/11059989.html
      if (node.level === 1) {
        return (
          <div class="custom-tree-node">
            <i class="iconfont icon-shujuku1"></i>
            <el-tooltip class="item" effect="light" placement="left">
              <div slot="content">{node.label}</div>
              <span>{data.label}</span>
            </el-tooltip>
          </div>
        );
      } else if (node.level === 2) {
        var icon_pic = "iconfont icon-shitu_biaoge";
        if (data.type === 'VIEW') {
          icon_pic = "iconfont icon-viewList"
        }
        return (
          <div class="custom-tree-node">
            <i class={icon_pic}></i>
            <span>{data.label}</span>
          </div>
        );
      } else if (node.level === 3) {
        var icon_pic = "iconfont icon-shitu_biaoge";
        if (data.type === 'VIEW') {
          icon_pic = "iconfont icon-viewList"
        }
        return (
          <div class="custom-tree-node">
            <i class={icon_pic}></i>
            <el-tooltip class="item" effect="light" placement="left">
              <div slot="content">{node.label}</div>
              <span>{data.label}</span>
            </el-tooltip>
          </div>
        );
      } else {
        return (
          <div class="custom-tree-node">
            <i class="el-icon-set-up"></i>
            <el-tooltip class="item" effect="light" placement="left">
              <div slot="content">{data.type}</div>
              <span>{data.label}({data.type})</span>
            </el-tooltip>
          </div>
        );
      }

    },
    handleNodeClick: function () {

    },
    handleHelp: function () {
      const url = 'https://www.yuque.com/sanpang-jq7te/nys82g/hur636mthgyhaodb#Wkpmx';
      window.open(url, '_blank');
    },
    handleGoBack: function () {
      this.$router.go(-1);
    },
    handleParseInputParams: function () {
      var currTabSql = this.$refs.sqlEditors.queryCurrentTabSql()
      if (/^\s*$/.test(currTabSql)) {
        alert("SQL content cannot be empty")
        return
      }
      this.$http({
        method: "POST",
        url: "/sqlrest/manager/api/v1/assignment/parse",
        data: qs.stringify({
          sql: currTabSql
        }),
      }).then(
        res => {
          if (0 === res.data.code) {
            if (res.data.data && res.data.data.length === 0) {
              this.$alert("Parsed input parameters are empty", "Error",
                {
                  confirmButtonText: "OK",
                  type: "error"
                }
              );
              return
            }
            for (let item of res.data.data) {
              if (!this.inputParams.find(i => i.name === item.name)) {
                let type = "STRING";
                let children = [];
                if (item.children) {
                  for (let child of item.children) {
                    children.push(
                      {
                        id: this.uuid(),
                        name: child.name,
                        location: 'REQUEST_BODY',
                        type: "STRING",
                        isArray: child.isArray,
                        required: true,
                        defaultValue: "",
                        remark: "",
                      }
                    );
                    type = "OBJECT";
                  }
                }
                this.inputParams.push(
                  {
                    id: this.uuid(),
                    name: item.name,
                    location: 'REQUEST_BODY',
                    type: type,
                    isArray: item.isArray,
                    required: true,
                    defaultValue: "",
                    remark: "",
                    children: children,
                  }
                )
              }
            };
          } else {
            if (res.data.message) {
              this.$alert(res.data.message, "Error",
                {
                  confirmButtonText: "OK",
                  type: "error"
                }
              );
            }
          }
        }
      );
    },
    agreeEngineChange: function () {
      if (this.createParam.engine === 'SCRIPT') {
        this.$refs.scriptEditer.setTableHints(this.keywordHints);
      }
    },
    handleAddInputParams: function () {
      this.inputParams.push(
        {
          id: this.uuid(),
          name: "",
          location: 'REQUEST_BODY',
          type: "STRING",
          isArray: false,
          required: true,
          defaultValue: "",
          remark: ""
        }
      )
    },
    handleAddPagableParams: function () {
      var add = false
      if (!this.inputParams.find(item => item.name === 'apiPageNum')) {
        add = true
        this.inputParams.push(
          {
            id: this.uuid(),
            name: "apiPageNum",
            type: "LONG",
            location: 'REQUEST_FORM',
            isArray: false,
            required: true,
            defaultValue: "1",
            remark: "Page Number"
          }
        );
      }
      if (!this.inputParams.find(item => item.name === 'apiPageSize')) {
        add = true
        this.inputParams.push(
          {
            id: this.uuid(),
            name: "apiPageSize",
            type: "LONG",
            location: 'REQUEST_FORM',
            isArray: false,
            required: true,
            defaultValue: "10",
            remark: "Page Size"
          },
        )
      }
      if (!add) {
        this.$alert("Pagination parameters already exist!", "Information",
          {
            confirmButtonText: "OK",
            type: "info"
          }
        );
      }
    },
    shouldInputShowOption: function (item, row) {
      if (this.getInputParamsParentRow(row)) {
        return item.value != 'OBJECT';
      }
      return true;
    },
    getInputParamsParentRow: function (childRow) {
      for (const row of this.inputParams) {
        if (row.children && row.children.includes(childRow)) {
          return row;
        }
      }
      return null;
    },
    deleteInputParamsItem: function (idx, row) {
      const index = this.inputParams.indexOf(row);
      if (index !== -1) {
        this.inputParams.splice(index, 1);
      } else {
        this.deleteInputSubParamsItem(idx, row);
      }
    },
    deleteInputSubParamsItem: function (index, childRow) {
      // Access parent row data through childRow
      const parentRow = this.getInputParamsParentRow(childRow);
      if (parentRow) {
        const childIndex = parentRow.children.indexOf(childRow);
        if (childIndex !== -1) {
          parentRow.children.splice(childIndex, 1);
        } else {
          console.warn('Child not found');
        }
      }
    },
    addInputSubParamsItem: function (row) {
      const index = this.inputParams.findIndex(item => row == item)
      if (index !== -1) {
        if (!this.inputParams[index].children) {
          // If children array doesn't exist yet, create it
          // Use Vue.set to ensure reactivity
          Vue.set(this.inputParams[index], 'children', []);
        }
        this.inputParams[index].location = 'REQUEST_BODY';
        this.inputParams[index].type = 'OBJECT';
        this.inputParams[index].children.push(
          {
            id: this.uuid(),
            name: "",
            type: "STRING",
            location: 'REQUEST_BODY',
            isArray: false,
            required: true,
            defaultValue: "",
            remark: ""
          },
        );
      } else {
        row.type = 'STRING';
        this.$alert('Only one level of nesting is allowed, type has been reset to string', "Operation Notice",
          {
            confirmButtonText: "OK",
            type: "info"
          }
        );
      }
    },
    checkSqlsOrScriptEmpty: function (sqls) {
      if (sqls === null || sqls === undefined || !Array.isArray(sqls) || sqls.length === 0 || sqls.includes('')) {
        return true
      }
      for (let str in sqls) {
        if (str === null || str === undefined || str.trim() === '' || str.trim().length === 0) {
          return true
        }
      }
      return false
    },
    handleSave: function () {
      this.$refs.form.validate(valid => {
        if (valid) {
          var sqls = []
          var isSql = true;
          if (this.createParam.engine === 'SQL') {
            isSql = true
            sqls = this.$refs.sqlEditors.queryContent()
          } else {
            isSql = false
            sqls = this.$refs.scriptEditer.queryContent()
          }

          if (!this.createParam.dataSourceId) {
            this.$alert('Please select a datasource', "Error",
              {
                confirmButtonText: "OK",
                type: "error"
              }
            );
            return
          }

          if (this.checkSqlsOrScriptEmpty(sqls)) {
            this.$alert(isSql ? 'Please check SQL window content' : 'Please check script content', "Error",
              {
                confirmButtonText: "OK",
                type: "error"
              }
            );
          } else {
            if (this.isUpdatePage()) {
              this.handleUpdateSave(sqls);
            } else {
              this.handleCreateSave(sqls);
            }
          }
        } else {
          alert("Please check input");
        }
      });
    },
    handleCreateSave: function (sqls) {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/create",
        data: JSON.stringify({
          groupId: this.createParam.group,
          moduleId: this.createParam.module,
          datasourceId: this.createParam.dataSourceId,
          name: this.createParam.name,
          description: this.createParam.description,
          method: this.createParam.method,
          contentType: this.createParam.contentType,
          path: this.createParam.path,
          open: this.createParam.open,
          namingStrategy: this.createParam.namingStrategy,
          formatMap: this.createParam.formatMap,
          open: this.createParam.open,
          alarm: this.createParam.alarm,
          flowStatus: this.createParam.flowStatus,
          flowGrade: this.createParam.flowGrade,
          flowCount: this.createParam.flowCount,
          cacheKeyType: this.createParam.cacheKeyType,
          cacheKeyExpr: this.createParam.cacheKeyExpr,
          cacheExpireSeconds: this.createParam.cacheExpireSeconds,
          engine: this.createParam.engine,
          contextList: sqls,
          params: this.inputParams,
          outputs: this.outputParams
        })
      }).then(
        res => {
          if (0 === res.data.code) {
            this.$router.push({ path: '/interface/list' });
            this.$message("Information added successfully");
          } else {
            if (res.data.message) {
              this.$alert(res.data.message, "Error",
                {
                  confirmButtonText: "OK",
                  type: "error"
                }
              );
            }
          }
        }
      );
    },
    handleUpdateSave: function (sqls) {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/update",
        data: JSON.stringify({
          id: this.createParam.id,
          groupId: this.createParam.group,
          moduleId: this.createParam.module,
          datasourceId: this.createParam.dataSourceId,
          name: this.createParam.name,
          description: this.createParam.description,
          method: this.createParam.method,
          contentType: this.createParam.contentType,
          path: this.createParam.path,
          open: this.createParam.open,
          namingStrategy: this.createParam.namingStrategy,
          formatMap: this.createParam.formatMap,
          open: this.createParam.open,
          alarm: this.createParam.alarm,
          flowStatus: this.createParam.flowStatus,
          flowGrade: this.createParam.flowGrade,
          flowCount: this.createParam.flowCount,
          cacheKeyType: this.createParam.cacheKeyType,
          cacheKeyExpr: this.createParam.cacheKeyExpr,
          cacheExpireSeconds: this.createParam.cacheExpireSeconds,
          engine: this.createParam.engine,
          contextList: sqls,
          params: this.inputParams,
          outputs: this.outputParams
        })
      }).then(
        res => {
          if (0 === res.data.code) {
            this.$router.push({ path: '/interface/list' });
            this.$message("Information updated successfully");
          } else {
            if (res.data.message) {
              this.$alert(res.data.message, "Error",
                {
                  confirmButtonText: "OK",
                  type: "error"
                }
              );
            }
          }
        }
      );
    },
    handleDebug: function () {
      this.debugResponse = {}
      this.debugConsoleLog = ""
      var sqls = []
      var isSql = true;
      if (this.createParam.engine === 'SQL') {
        isSql = true
        sqls = this.$refs.sqlEditors.queryContent()
      } else {
        isSql = false
        sqls = this.$refs.scriptEditer.queryContent()
      }

      if (!this.createParam.dataSourceId) {
        this.$alert('Please select a datasource', "Error",
          {
            confirmButtonText: "OK",
            type: "error"
          }
        );
        return
      }

      if (this.checkSqlsOrScriptEmpty(sqls)) {
        this.$alert(isSql ? 'Please check SQL window content' : 'Please check script content', "Error",
          {
            confirmButtonText: "OK",
            type: "error"
          }
        );
      } else {
        this.debugParams = []
        this.inputParams.forEach(item => {
          if (item.children && item.children.length > 0) {
            for (let it of item.children) {
              if (!it.arrayValues) {
                Vue.set(it, 'arrayValues', []);
              }
            }
          }
          this.debugParams.push(
            {
              id: item.id,
              name: item.name,
              type: item.type,
              isArray: item.isArray,
              required: item.required,
              defaultValue: item.defaultValue,
              remark: item.remark,
              value: null,
              arrayValues: [],
              children: item.children
            },
          )
        })
        this.showDebugDrawer = true
      }
    },
    addArrayValuesItem: function (row) {
      row.arrayValues.push('');
    },
    delArrayValuesItem: function (array, index) {
      array.splice(index, 1);
    },
    handleExecuteDebug: function () {
      var sqls = []
      if (this.createParam.engine === 'SQL') {
        sqls = this.$refs.sqlEditors.queryContent()
      } else {
        sqls = this.$refs.scriptEditer.queryContent()
      }

      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/debug",
        data: JSON.stringify({
          dataSourceId: this.createParam.dataSourceId,
          engine: this.createParam.engine,
          namingStrategy: this.createParam.namingStrategy,
          formatMap: this.createParam.formatMap,
          contextList: sqls,
          paramValues: this.debugParams
        })
      }).then(
        res => {
          if (0 === res.data.code) {
            this.debugResponse = res.data.data.answer;
            this.debugConsoleLog = res.data.data.logs;
            let arr = res.data.data.types;
            if (Array.isArray(arr) && arr.length === 0) {
              this.$alert("Result set is empty", "Information",
                {
                  confirmButtonText: "OK",
                  type: "info"
                }
              );
            } else {
              var paramNameRemarkMap = new Map();
              for (let one of this.outputParams) {
                paramNameRemarkMap.set(one.name, one.remark);
                if (one.children) {
                  for (let subOne of one.children) {
                    paramNameRemarkMap.set(one.name + "." + subOne.name, subOne.remark);
                  }
                }
              }

              this.outputParams = [];
              for (let item of arr) {
                var remark = item.remark || paramNameRemarkMap.get(item.name);
                if (item.children) {
                  for (let one of item.children) {
                    one.remark = one.remark || paramNameRemarkMap.get(item.name + "." + one.name);
                  }
                }
                this.outputParams.push(
                  {
                    id: item.id,
                    name: item.name,
                    type: item.type,
                    isArray: item.isArray,
                    remark: remark,
                    children: item.children,
                  }
                )
              }
            }
          } else {
            if (res.data.message) {
              this.$alert(res.data.message, "Error",
                {
                  confirmButtonText: "OK",
                  type: "error"
                }
              );
            }
          }
        }
      );
    },
    handleAddOutputParams: function () {
      this.outputParams.push(
        {
          name: '',
          type: 'STRING',
          remark: null
        },
      )
    },
    shouldOutputShowOption: function (item, row) {
      if (this.getOutputParamsParentRow(row)) {
        return item.value != 'OBJECT';
      }
      return true;
    },
    getOutputParamsParentRow: function (childRow) {
      for (const row of this.outputParams) {
        if (row.children && row.children.includes(childRow)) {
          return row;
        }
      }
      return null;
    },
    addOutputSubParamsItem: function (row) {
      const index = this.outputParams.findIndex(item => row == item)
      if (index !== -1) {
        if (!this.outputParams[index].children) {
          // If children array doesn't exist, create it
          // Use Vue.set to ensure reactivity
          Vue.set(this.outputParams[index], 'children', []);
        }
        this.outputParams[index].location = 'REQUEST_BODY';
        this.outputParams[index].type = 'OBJECT';
        this.outputParams[index].children.push(
          {
            id: this.uuid(),
            name: "",
            type: "STRING",
            location: 'REQUEST_BODY',
            isArray: false,
            required: true,
            defaultValue: "",
            remark: ""
          },
        );
      } else {
        row.type = 'STRING';
        this.$alert('Only one level of nesting is allowed, type has been reset to string', "Operation Notice",
          {
            confirmButtonText: "OK",
            type: "info"
          }
        );
      }
    },
    deleteOutputParamsItem: function (idx, row) {
      const index = this.outputParams.indexOf(row);
      if (index !== -1) {
        this.outputParams.splice(index, 1);
      } else {
        this.deleteOutputSubParamsItem(idx, row);
      }
    },
    deleteOutputSubParamsItem: function (index, childRow) {
      // Access parent row data through childRow
      const parentRow = this.getOutputParamsParentRow(childRow);
      if (parentRow) {
        const childIndex = parentRow.children.indexOf(childRow);
        if (childIndex !== -1) {
          parentRow.children.splice(childIndex, 1);
        } else {
          console.warn('Child not found');
        }
      }
    },
    handleShowVersionList: function () {
      this.$http({
        method: "GET",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/version/list/" + + this.$route.query.id,
      }).then(res => {
        if (0 === res.data.code) {
          this.versionList = res.data.data;
          this.showVersionDrawer = true;
        } else {
          if (res.data.message) {
            alert("Failed to get version list: " + res.data.message);
          }
        }
      });
    },
    boolFormatOnline: function (row) {
      if (row.online === true) {
        return "Yes";
      } else {
        return "-";
      }
    },
    handleShowVersionDetail: function (index, row) {
      this.$http({
        method: "GET",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/version/show/" + row.commitId,
      }).then(res => {
        if (0 === res.data.code) {
          this.showVersionDrawer = false;
          this.showTree = false;
          let detail = res.data.data.detail;
          this.applyAssignmentDetail(detail);
          this.showVersionDetail = true
          this.$message("Content has been switched to version V" + row.version + ".");
        } else {
          if (res.data.message) {
            alert("Failed to view version details: " + res.data.message);
          }
        }
      });
    },
    handleExitShowVersionDetail: function () {
      this.loadAssignmentDetail();
      this.showVersionDetail = false;
      this.$message("Exited version detail view.");
    },
    handleRevertVersionDetail: function (index, row) {
      this.$confirm(
        "Are you sure you want to rollback the latest editable content to version V" + row.version + ", continue?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning"
        }
      ).then(() => {
        this.$http({
          method: "GET",
          headers: {
            'Content-Type': 'application/json'
          },
          url: "/sqlrest/manager/api/v1/version/revert/" + this.$route.query.id + "?commitId=" + row.commitId,
        }).then(res => {
          if (0 === res.data.code) {
            this.showVersionDrawer = false;
            this.loadAssignmentDetail();
            this.$message("Version rollback successful");
          } else {
            if (res.data.message) {
              alert("Failed to rollback version: " + res.data.message);
            }
          }
        });
      });
    }
  },
  async created () {
    await this.loadResponseTypeFormat();
    this.loadAssignmentDetail();
    this.loadConnections();
    this.loadGroups();
    this.loadModules();
    this.loadGateway();
    this.loadKeywordHints();
    this.loadTreeData();
    this.loadResponseNamingStrategy();
  },
}
</script>

<style scoped>
.el-card {
  width: 100%;
  height: 100%;
  overflow: auto;
}

.tip-content {
  font-size: 12px;
}

.name-mapper-table,
.name-mapper-table table tr th,
.name-mapper-table table tr td {
  border-collapse: collapse;
  border: 1px solid #e0dddd;
  width: 100%;
}

.el-descriptions__body
  .el-descriptions__table
  .el-descriptions-row
  .el-descriptions-item__label {
  min-width: 20px;
  max-width: 60px;
}

.custom-tree-node span {
  font-size: 12px;
}

.el-col .el-select {
  width: 98%;
}
.el-tree {
  overflow: auto;
}
.el-tree-node__content {
  height: 20px;
}
.el-drawer__wrapper {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: hidden;
  margin: 0;
}
/deep/ .el-input.is-disabled .el-input__inner {
  color: #5f5e5e !important;
}
/deep/.el-table .cell {
  box-sizing: border-box;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-break: break-all;
  line-height: 23px;
  padding-right: 10px;
  display: flex;
  flex-direction: row;
}
/deep/.el-table
  .cell
  .el-checkbox__input.is-disabled.is-checked
  .el-checkbox__inner {
  background-color: #1464dd;
  border-color: #f4f5f8;
}
.debug-console-log-text {
  white-space: pre-line;
}

/* Vertical tabs styling - DBAPI-UI style */
.el-tabs--left {
  min-height: 600px;
}

.el-tabs--left .el-tabs__header {
  margin-right: 0;
  width: 200px;
  background-color: #fafafa;
  border-right: 1px solid #e4e7ed;
}

.el-tabs--left .el-tabs__nav-wrap {
  margin-right: 0;
}

.el-tabs--left .el-tabs__item {
  text-align: left;
  padding: 0 20px;
  height: 50px;
  line-height: 50px;
  color: #606266;
  font-size: 14px;
  transition: all 0.3s ease;
}

.el-tabs--left .el-tabs__item:hover {
  color: #b142af;
  background-color: #f5f7fa;
}

.el-tabs--left .el-tabs__item.is-active {
  color: #b142af;
  background-color: #f0f2f5;
  font-weight: 500;
}

.el-tabs--left .el-tabs__active-bar {
  background-color: #b142af;
  width: 3px;
}

.el-tabs--left .el-tabs__content {
  padding: 20px;
  background-color: #ffffff;
}
</style>
