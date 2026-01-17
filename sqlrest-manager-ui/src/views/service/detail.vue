<template>
  <div>
    <h1 class="page-title">API DETAILS</h1>
    <el-card>
    <div class="container">
      <div>
        <el-button type="primary"
                   size="mini"
                   @click="handleGoBack">
          <i class="el-icon-d-arrow-left">
            Back</i>
        </el-button>
      </div>
      <el-tabs type="border-card">
        <el-tab-pane label="API Definition">
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">Current Version:</span>
            </el-col>
            <el-col :span="20">
              <el-tag size="medium">V{{interfaceDetail.version}}</el-tag>
              <el-tooltip content="Switch Version" placement="top" effect="dark">
                <el-button plain size="mini" type="danger" @click="handleSwitchVersion(interfaceDetail)" circle>
                  <i class="el-icon-switch-button"></i>
                </el-button>
              </el-tooltip>
            </el-col>
          </el-row>
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">CommitId:</span>
            </el-col>
            <el-col :span="20">
              <span class="detail-value">{{interfaceDetail.commitId}}</span>
            </el-col>
          </el-row>
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">API Name:</span>
            </el-col>
            <el-col :span="20">
              <span class="detail-value">{{interfaceDetail.name}}</span>
            </el-col>
          </el-row>
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">API Path:</span>
            </el-col>
            <el-col :span="20">
              <el-tag size="medium"
                      type="danger"
                      class="method-tag">{{interfaceDetail.method}}</el-tag>
              <span class="detail-value" style="margin-left: 10px;">{{interfaceDetail.path}}</span>
            </el-col>
          </el-row>
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">Request Type:</span>
            </el-col>
            <el-col :span="20">
              <span class="detail-value">{{interfaceDetail.contentType}}</span>
            </el-col>
          </el-row>
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">Authentication:</span>
            </el-col>
            <el-col :span="20">
              <el-tooltip :content="boolTypeFormat(!interfaceDetail.open)" placement="top" effect="dark">
                <el-button plain size="mini" :type="interfaceDetail.open ? 'success' : 'danger'" circle>
                  <i :class="interfaceDetail.open ? 'el-icon-unlock' : 'el-icon-lock'"></i>
                </el-button>
              </el-tooltip>
            </el-col>
          </el-row>
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">Description:</span>
            </el-col>
            <el-col :span="20">
              <span class="detail-value">{{interfaceDetail.description}}</span>
            </el-col>
          </el-row>
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">Request Parameters:</span>
            </el-col>
            <el-col :span="20">
              <el-table :data="interfaceDetail.inputParams"
                        size="small"
                        default-expand-all
                        row-key="id"
                        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                        border>
                <template slot="empty">
                  <span>Request parameters are empty</span>
                </template>
                <el-table-column min-width="18%">
                  <template slot="header">
                    <span style="white-space: nowrap;">Param Name</span>
                  </template>
                  <template slot-scope="scope">
                    {{scope.row.name}}
                  </template>
                </el-table-column>
                <el-table-column min-width="14%">
                  <template slot="header">
                    <span style="white-space: nowrap;">Param Location</span>
                  </template>
                  <template slot-scope="scope">
                    {{enumTypeLocationFormat(scope.row.location)}}
                  </template>
                </el-table-column>
                <el-table-column min-width="14%">
                  <template slot="header">
                    <span style="white-space: nowrap;">Param Type</span>
                  </template>
                  <template slot-scope="scope">
                    {{enumTypeValueFormat(scope.row.type)}}
                  </template>
                </el-table-column>
                <el-table-column min-width="10%"
                                 align="center">
                  <template slot="header">
                    <span style="white-space: nowrap;">Array</span>
                  </template>
                  <template slot-scope="scope">
                    {{boolTypeFormat(scope.row.isArray)}}
                  </template>
                </el-table-column>
                <el-table-column min-width="10%"
                                 align="center">
                  <template slot="header">
                    <span style="white-space: nowrap;">Required</span>
                  </template>
                  <template slot-scope="scope">
                    {{boolTypeFormat(scope.row.required)}}
                  </template>
                </el-table-column>
                <el-table-column min-width="15%">
                  <template slot="header">
                    <span style="white-space: nowrap;">Default Value</span>
                  </template>
                  <template slot-scope="scope">
                    {{scope.row.defaultValue}}
                  </template>
                </el-table-column>
                <el-table-column min-width="19%">
                  <template slot="header">
                    <span style="white-space: nowrap;">Description</span>
                  </template>
                  <template slot-scope="scope">
                    {{scope.row.remark}}
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
          <el-row class="detail-row">
            <el-col :span="4">
              <span class="detail-label">Response Parameters:</span>
            </el-col>
            <el-col :span="20">
              <el-table :data="interfaceDetail.outputParams"
                        size="small"
                        default-expand-all
                        row-key="id"
                        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                        border>
                <template slot="empty">
                  <span>Response parameters are empty</span>
                </template>
                <el-table-column min-width="30%">
                  <template slot="header">
                    <span style="white-space: nowrap;">Param Name</span>
                  </template>
                  <template slot-scope="scope">
                    {{scope.row.name}}
                  </template>
                </el-table-column>
                <el-table-column min-width="30%">
                  <template slot="header">
                    <span style="white-space: nowrap;">Param Type</span>
                  </template>
                  <template slot-scope="scope">
                    {{enumTypeValueFormat(scope.row.type)}}
                  </template>
                </el-table-column>
                <el-table-column min-width="40%">
                  <template slot="header">
                    <span style="white-space: nowrap;">Description</span>
                  </template>
                  <template slot-scope="scope">
                    {{scope.row.remark}}
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="Access Log">
          <el-table :data="accessLogData"
                    size="small"
                    border>
            <el-table-column prop="createTime"
                             label="Time"
                             min-width="20%"></el-table-column>
            <el-table-column label="Client Address"
                             prop="ipAddr"
                             :show-overflow-tooltip="true"
                             min-width="15%">
            </el-table-column>
            <el-table-column label="Executor Address"
                             prop="executorAddr"
                             :show-overflow-tooltip="true"
                             min-width="15%">
            </el-table-column>
            <el-table-column label="Gateway Address"
                             prop="gatewayAddr"
                             :show-overflow-tooltip="true"
                             min-width="15%">
            </el-table-column>
            <el-table-column label="Status Code"
                             prop="status"
                             :show-overflow-tooltip="true"
                             min-width="12%">
            </el-table-column>
            <el-table-column label="Duration (ms)"
                             prop="duration"
                             :show-overflow-tooltip="true"
                             min-width="12%">
            </el-table-column>
            <el-table-column label="Caller"
                             prop="clientApp"
                             :show-overflow-tooltip="true"
                             min-width="15%">
            </el-table-column>
            <el-table-column label="UserAgent"
                             prop="userAgent"
                             :show-overflow-tooltip="true"
                             min-width="15%">
            </el-table-column>
            <el-table-column label="View"
                             min-width="20%">
              <template slot-scope="scope">
                <el-link class="btn-text"
                         type="primary"
                         @click="handleShowParam(scope.$index, scope.row)">Input</el-link>
                <label v-if="scope.row.exception"
                       class="btn-style">&nbsp;|&nbsp;</label>
                <el-link class="btn-text"
                         v-if="scope.row.exception"
                         type="primary"
                         @click="handleShowException(scope.$index, scope.row)">Exception</el-link>
              </template>
            </el-table-column>
          </el-table>
          <div class="page"
               align="right">
            <el-pagination @size-change="handleAccessSizeChange"
                           @current-change="handleAccessCurrentChange"
                           :current-page="currentAccessPageNum"
                           :page-sizes="[5, 10, 20, 40]"
                           :page-size="currentAccessPageSize"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="totalAccessItemCount"></el-pagination>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>

    <el-dialog title="API Request Parameters"
               :visible.sync="showParamDialogVisible"
               :showClose="false">
      <json-viewer :value="requestParameters"
                   :expand-depth=10
                   copyable
                   boxed
                   sort></json-viewer>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="info"
                   @click="showParamDialogVisible = false">Close</el-button>
      </div>
    </el-dialog>

    <el-dialog title="API Exception Information"
               :visible.sync="showExceptDialogVisible"
               :showClose="false">
      <el-input type="textarea"
                :rows="20"
                :spellcheck="false"
                v-model="exeptionText"></el-input>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="info"
                   @click="showExceptDialogVisible = false">Close</el-button>
      </div>
    </el-dialog>

    <el-dialog title="Switch Online Version"
               :visible.sync="versionDialogVisible"
               :showClose="false"
               width="40%"
               :before-close="handleClose">
      <el-table :data="versionList"
                highlight-current-row
                size="small"
                border>
        <template slot="empty">
          <span>Version list is empty, please click "Publish" button to publish a version</span>
        </template>
        <el-table-column label="Select Version"
                         min-width="10%">
          <template slot-scope="scope">
            <el-radio v-model="selectCommitId"
                      :label="scope.row.commitId">V{{ scope.row.version }}</el-radio>
          </template>
        </el-table-column>
        <el-table-column prop="createTime"
                         label="Create Time"
                         min-width="15%"> </el-table-column>
        <el-table-column prop="description"
                         label="Version Description"
                         show-overflow-tooltip
                         min-width="20%"></el-table-column>
      </el-table>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="versionDialogVisible = false">Cancel</el-button>
        <el-button type="primary"
                   @click="handleDeployVersion">Switch</el-button>
      </div>
    </el-dialog>

  </el-card>
  </div>
</template>

<script>
import '@/assets/sysicon/iconfont.js'
import JsonViewer from 'vue-json-viewer';

export default {
  data () {
    return {
      paramLocation: [
        { name: "Query", value: "REQUEST_FORM" },
        { name: "Body", value: "REQUEST_BODY" },
        { name: "Header", value: "REQUEST_HEADER" }
      ],
      paramTypeList: [
        { name: "Integer", value: "LONG" },
        { name: "Double", value: "DOUBLE" },
        { name: "String", value: "STRING" },
        { name: "Date", value: "DATE" },
        { name: "Time", value: "TIME" },
        { name: "Object", value: "OBJECT" }
      ],
      showDetail: false,
      tableData: [],
      currentModuleId: 0,
      currentPageNum: 1,
      currentPageSize: 10,
      totalItemCount: 0,
      interfaceDetail: {},
      gatewayApiPrefix: null,
      currentInterfaceId: 0,
      currentCommitId: 0,
      accessLogData: [],
      currentAccessPageNum: 1,
      currentAccessPageSize: 10,
      totalAccessItemCount: 0,
      showParamDialogVisible: false,
      requestParameters: null,
      showExceptDialogVisible: false,
      exeptionText: null,
      versionDialogVisible: false,
      versionList: [],
      selectCommitId: null,
    };
  },
  components: { JsonViewer },
  methods: {
    handleClose () { },
    handleGoBack: function () {
      this.$router.go(-1);
    },
    loadGetwayApiPrefix: function () {
      this.$http({
        method: "GET",
        url: "/sqlrest/manager/api/v1/node/prefix"
      }).then(
        res => {
          if (0 === res.data.code) {
            if (res.data.data && typeof res.data.data === 'string') {
              this.gatewayApiPrefix = res.data.data || "";
            }
          }
        }
      );
    },
    reloadIntefaceDetail: function () {
      if (!this.gatewayApiPrefix) {
        this.loadGetwayApiPrefix();
      }
      this.$http.get(
        "/sqlrest/manager/api/v1/version/show/" + this.currentCommitId
      ).then(res => {
        if (0 === res.data.code) {
          let detail = res.data.data.detail;
          this.interfaceDetail = {
            id: detail.id,
            version: detail.version,
            commitId: detail.commitId,
            name: detail.name,
            description: detail.description,
            method: detail.method,
            path: this.gatewayApiPrefix + detail.path,
            contentType: detail.contentType,
            open: detail.open,
            group: detail.groupId,
            module: detail.moduleId,
            dataSourceId: detail.datasourceId,
            engine: detail.engine,
            inputParams: detail.params,
            outputParams: detail.outputs || [],
          }
        }
      });
    },
    reloadAccessLogList: function () {
      this.$http.get(
        "/sqlrest/manager/api/v1/overview/log/" + this.currentInterfaceId
        + "?page=" + this.currentAccessPageNum + "&size=" + this.currentAccessPageSize
      ).then(res => {
        if (0 === res.data.code) {
          this.totalAccessItemCount = res.data.pagination.total
          this.accessLogData = res.data.data;
        }
      });
    },
    handleShowParam: function (index, row) {
      this.requestParameters = row.parameters;
      this.showParamDialogVisible = true;
    },
    handleShowException: function (index, row) {
      this.exeptionText = row.exception;
      this.showExceptDialogVisible = true;
    },
    handleSizeChange: function (pageSize) {
      this.currentPageSize = pageSize;
      this.reloadInterfaceList()
    },
    handleCurrentChange: function (currentPage) {
      this.currentPageNum = currentPage;
      this.reloadInterfaceList()
    },
    handleAccessSizeChange: function (pageSize) {
      this.currentAccessPageSize = pageSize;
      this.reloadAccessLogList()
    },
    handleAccessCurrentChange: function (currentPage) {
      this.currentAccessPageNum = currentPage;
      this.reloadAccessLogList()
    },
    boolTypeFormat (value) {
      if (value === true) {
        return "Yes";
      } else {
        return "No";
      }
    },
    returnUnknownValue () {
      return "Unknown";
    },
    enumTypeLocationFormat (value) {
      for (const item of this.paramLocation) {
        if (item.value === value) {
          return item.name;
        }
      }
      return this.returnUnknownValue();
    },
    enumTypeValueFormat (value) {
      for (const item of this.paramTypeList) {
        if (item.value === value) {
          return item.name;
        }
      }
      return this.returnUnknownValue();
    },
    handleSwitchVersion (detail) {
      this.$http({
        method: "GET",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/version/list/" + detail.id,
      }).then(res => {
        if (0 === res.data.code) {
          this.versionList = res.data.data;
          this.versionDialogVisible = true;
        } else {
          if (res.data.message) {
            alert("Failed to get version list: " + res.data.message);
          }
        }
      });
    },
    handleDeployVersion () {
      if (!this.selectCommitId || this.selectCommitId <= 0) {
        this.$alert("Please select a version", "Error",
          {
            confirmButtonText: "OK",
            type: "error"
          }
        );
        return;
      }
      if (this.selectCommitId == this.currentCommitId) {
        this.$alert("The selected version is the same as the current online version, no need to switch", "Error",
          {
            confirmButtonText: "OK",
            type: "error"
          }
        );
        return;
      }
      console.log("selectCommitId=" + this.selectCommitId + ",currentCommitId=" + this.currentCommitId);
      this.$http({
        method: "PUT",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/deploy/" + this.interfaceDetail.id + "?commitId=" + this.selectCommitId,
      }).then(res => {
        if (0 === res.data.code) {
          this.currentCommitId = this.selectCommitId
          this.selectCommitId = null;
          this.versionDialogVisible = false;
          this.reloadIntefaceDetail();
          this.$message("Version switched successfully");
        } else {
          if (res.data.message) {
            alert("Failed to deploy: " + res.data.message);
          }
        }
      });
    }
  },
  created () {
    this.currentInterfaceId = this.$route.query.id;
    this.currentCommitId = this.$route.query.commitId;
    this.reloadIntefaceDetail();
    this.reloadAccessLogList();
  }
};
</script>

<style scoped>
.el-card {
  width: 100%;
  height: 100%;
  overflow: auto;
}

.tree-node-text {
  overflow: hidden; /* Hide overflow content */
  white-space: nowrap; /* Prevent text wrapping */
  text-overflow: ellipsis; /* Show ellipsis for overflow */
}

.box {
  width: 100%;
  height: 100%;
  display: flex;
  vertical-align: top; /* Ensure elements are top-aligned */
  align-items: flex-start;
}

.resizable {
  height: 100%;
  padding: 0px;
  display: inline-block;
}

.resizer {
  width: 5px;
  height: 200px;
  cursor: ew-resize;
  display: inline-block;
  border-left: 1px solid #dcdfe6;
  margin-left: 5px;
  margin-right: 2px;
}

.resizer:hover {
  background-color: #699eff;
}

.detail-row {
  font-size: 15px;
  padding: 8px 2px;
}

.detail-label {
  font-weight: 500;
  color: #606266;
}

.detail-value {
  color: #303133;
}

.method-tag {
  font-weight: bold;
}

.btn-style {
  color: #e9e9f3;
}

.btn-text {
  font-size: 12px;
  color: #6873ce;
}
</style>
