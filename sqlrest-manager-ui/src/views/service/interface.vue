<template>
  <el-card>
    <div ref="box"
         class="box">
      <div class="resizable"
           :style="{ width: leftWidth + 'px' }">
        <el-scrollbar style="min-height: 500px; max-height: 800px; overflow-x: hidden; overflow-y: auto;">
          <el-tree ref="tree"
                   empty-text="No content"
                   :indent=4
                   :data="treeData"
                   :props="props"
                   :load="loadNode"
                   :expand-on-click-node="true"
                   :highlight-current="true"
                   :render-content="renderContent"
                   @node-click="handleTreeNodeClick"
                   lazy>
          </el-tree>
        </el-scrollbar>
      </div>
      <div class="resizer"
           :style="{ height: resizerHeight + 'px' }"
           @mousedown="startResize">⋮</div>
      <div class="resizable"
           :style="{ width: rightWidth + 'px' }">
        <div v-if="!showDetail">
          <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                    :data="tableData"
                    size="small"
                    border>
            <el-table-column prop="name"
                             label="Name"
                             show-overflow-tooltip
                             min-width="30%">
              <template slot-scope="scope">
                [{{ scope.row.id }}]{{ scope.row.name }}
              </template>
            </el-table-column>
            <el-table-column prop="path"
                             label="API Path"
                             show-overflow-tooltip
                             min-width="20%">
              <template slot-scope="scope">
                <el-tag size="medium"
                        class="name-wrapper-tag">{{ scope.row.method }}</el-tag>
                {{ scope.row.path }}
              </template>
            </el-table-column>
            <el-table-column label="Engine"
                             min-width="10%">
              <template slot-scope="scope">
                <el-tag size="medium"
                        class="name-wrapper-tag">{{ scope.row.engine }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime"
                             label="Create Time"
                             min-width="18%"></el-table-column>
          </el-table>
          <div class="page"
               align="right">
            <el-pagination @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page="currentPageNum"
                           :page-sizes="[5, 10, 20, 40]"
                           :page-size="currentPageSize"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="totalItemCount"></el-pagination>
          </div>
        </div>
        <el-tabs v-if="showDetail"
                 type="border-card">
          <el-tab-pane label="API Definition">
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-key">Current Version:</i>
              </el-col>
              <el-col :span="20">
                <el-tag size="small">V{{interfaceDetail.version}}</el-tag>
                <el-button size="small"
                           type="danger"
                           icon="el-icon-timer"
                           @click="handleSwitchVersion(interfaceDetail)"
                           round>Switch</el-button>
              </el-col>
            </el-row>
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-mic">CommitId:</i>
              </el-col>
              <el-col :span="20">
                <el-tag size="small">{{interfaceDetail.commitId}}</el-tag>
              </el-col>
            </el-row>
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-user">API Name:</i>
              </el-col>
              <el-col :span="20">
                <el-tag size="small">{{interfaceDetail.name}}</el-tag>
              </el-col>
            </el-row>
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-attract">API Path:</i>
              </el-col>
              <el-col :span="20">
                <el-tag size="small"
                        type="danger">{{interfaceDetail.method}}</el-tag>
                <el-tag size="small"
                        type="warning">{{interfaceDetail.path}}</el-tag>
              </el-col>
            </el-row>
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-tickets">Request Type:</i>
              </el-col>
              <el-col :span="20">
                <el-tag size="small"
                        type="success">{{interfaceDetail.contentType}}</el-tag>
              </el-col>
            </el-row>
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-s-check">Authentication:</i>
              </el-col>
              <el-col :span="20">
                <el-tag size="small"
                        type="danger">{{boolTypeFormat(!interfaceDetail.open)}}</el-tag>
              </el-col>
            </el-row>
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-help">Description:</i>
              </el-col>
              <el-col :span="20">
                <el-tag size="small"
                        type="info">{{interfaceDetail.description}}</el-tag>
              </el-col>
            </el-row>
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-postcard">Request Parameters:</i>
              </el-col>
              <el-col :span="20">
                <el-table :data="interfaceDetail.inputParams"
                          :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                          size="mini"
                          default-expand-all
                          row-key="id"
                          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                          border>
                  <template slot="empty">
                    <span>Request parameters are empty</span>
                  </template>
                  <el-table-column label="Parameter Name"
                                   prop="name"
                                   min-width="40%">
                  </el-table-column>
                  <el-table-column label="Parameter Location"
                                   prop="location"
                                   min-width="15%">
                    <template slot-scope="scope">
                      {{enumTypeLocationFormat(scope.row.location)}}
                    </template>
                  </el-table-column>
                  <el-table-column label="Parameter Type"
                                   prop="type"
                                   min-width="15%">
                    <template slot-scope="scope">
                      {{enumTypeValueFormat(scope.row.type)}}
                    </template>
                  </el-table-column>
                  <el-table-column label="Array"
                                   min-width="15%">
                    <template slot-scope="scope">
                      {{boolTypeFormat(scope.row.isArray)}}
                    </template>
                  </el-table-column>
                  <el-table-column label="Required"
                                   min-width="15%">
                    <template slot-scope="scope">
                      {{boolTypeFormat(scope.row.required)}}
                    </template>
                  </el-table-column>
                  <el-table-column label="Default Value"
                                   prop="defaultValue"
                                   min-width="20%">
                    <template slot-scope="scope">
                      {{scope.row.defaultValue}}
                    </template>
                  </el-table-column>
                  <el-table-column label="Description"
                                   prop="remark"
                                   min-width="25%">
                    <template slot-scope="scope">
                      {{scope.row.remark}}
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
            </el-row>
            <el-row class="detail-row">
              <el-col :span="4">
                <i class="el-icon-chat-line-round">Response Parameters:</i>
              </el-col>
              <el-col :span="20">
                <el-table :data="interfaceDetail.outputParams"
                          :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                          size="mini"
                          default-expand-all
                          row-key="id"
                          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                          border>
                  <template slot="empty">
                    <span>Response parameters are empty</span>
                  </template>
                  <el-table-column label="Parameter Name"
                                   prop="name"
                                   min-width="25%">
                  </el-table-column>
                  <el-table-column label="Parameter Type"
                                   min-width="25%">
                    <template slot-scope="scope">
                      {{enumTypeValueFormat(scope.row.type)}}
                    </template>
                  </el-table-column>
                  <el-table-column label="Description"
                                   min-width="25%">
                    <template slot-scope="scope">
                      {{scope.row.remark}}
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="Access Log">
            <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                      :data="accessLogData"
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
      <el-table :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                :data="versionList"
                highlight-current-row
                size="mini"
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
      leftWidth: 0, // Initial width of left div
      rightWidth: 0, // Initial width of right div
      resizerHeight: 0,
      startX: 0, // Initial position when mouse is pressed
      treeData: [],
      props: {
        label: 'label',
        children: 'children',
        isLeaf: 'leaf'
      },
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
  mounted () {
    window.addEventListener('resize', this.initResize);
    this.initResize();
  },
  methods: {
    handleClose () { },
    initResize () {
      if (this.$refs.box && this.$refs.box.clientWidth) {
        const width = this.$refs.box.clientWidth;
        const height = this.$refs.box.clientHeight;
        this.resizerHeight = height;
        this.leftWidth = Math.floor(width * 0.25) - 5;
        this.rightWidth = Math.floor(width * 0.75) - 5;
      }
    },
    startResize (event) {
      this.startX = event.clientX;
      document.addEventListener("mousemove", this.onResize);
      document.addEventListener("mouseup", this.stopResize);
    },
    onResize (event) {
      const diff = event.clientX - this.startX;
      this.leftWidth += diff;
      this.rightWidth -= diff;
      this.startX = event.clientX;
    },
    stopResize () {
      document.removeEventListener("mousemove", this.onResize);
      document.removeEventListener("mouseup", this.stopResize);
    },
    loadNode: function (node, resolve) {
      if (node.level > 1) {
        return resolve([]);
      }
      if (node.level === 0) {
        this.loadModuleListAll(resolve)
      } else {
        setTimeout(() => {
          this.loadInterfaceList(resolve, node.data.value)
        }, 500);
      }
    },
    loadModuleListAll: function (resolve) {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/module/listAll",
        data: JSON.stringify({
        })
      }).then(res => {
        if (0 === res.data.code) {
          let moduleList = []
          for (let element of res.data.data) {
            moduleList.push(
              {
                'label': element.name,
                'parent': 0,
                'value': element.id,
                'leaf': false,
              }
            )
          }
          resolve(moduleList)
        } else {
          resolve([])
          if (res.data.message) {
            alert("Failed to load module list: " + res.data.message);
          }
        }
      });
    },
    loadInterfaceList: function (resolve, id) {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/list",
        data: window.JSON.stringify(
          {
            moduleId: id,
            online: true,
            page: 1,
            size: 2147483647
          }
        )
      }).then(res => {
        if (0 === res.data.code) {
          let interfaceList = []
          for (let element of res.data.data) {
            interfaceList.push(
              {
                'label': element.name,
                'parent': id,
                'value': element.id,
                'commitId': element.commitId,
                'leaf': true,
              }
            )
          }
          resolve(interfaceList)
        } else {
          resolve([])
          alert("Failed to load interface list: " + res.data.message);
        }
      }
      );
    },
    renderContent (h, { node, data, store }) {
      if (node.level === 1) {
        return (
          <div class="iconfont icon-wenjianxitong-86">
            <span class="tree-node-text">{data.label}</span>
          </div>
        );
      } else {
        return (
          <div class="iconfont icon-wenjianxitong3">
            <span class="tree-node-text">{data.label}</span>
          </div>
        );
      }
    },
    handleTreeNodeClick (data) {
      if (data.parent > 0) {
        this.currentInterfaceId = data.value
        this.currentCommitId = data.commitId
        this.showDetail = true
        this.reloadIntefaceDetail()
        this.reloadAccessLogList()
      } else {
        this.showDetail = false
        this.currentModuleId = data.value;
        this.reloadInterfaceList()
      }
    },
    reloadInterfaceList () {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/list",
        data: window.JSON.stringify(
          {
            moduleId: this.currentModuleId,
            online: true,
            page: this.currentPageNum,
            size: this.currentPageSize
          }
        )
      }).then(res => {
        if (0 === res.data.code) {
          this.tableData = []
          this.totalItemCount = res.data.pagination.total
          for (let element of res.data.data) {
            this.tableData.push(
              {
                'id': element.id,
                'name': element.name,
                'path': element.path,
                'method': element.method,
                'engine': element.engine,
                'createTime': element.createTime
              }
            )
          }
        }
      }
      );
    },
    loadGetwayApiPrefix: function () {
      this.$http({
        method: "GET",
        url: "/sqlrest/manager/api/v1/node/prefix"
      }).then(
        res => {
          if (0 === res.data.code) {
            if (res.data.data && typeof res.data.data === 'string') {
              this.gatewayApiPrefix = res.data.data;
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
      if (this.selectCommitId === this.currentCommitId) {
        this.$alert("The selected version is the same as the current online version, no need to switch", "Error",
          {
            confirmButtonText: "OK",
            type: "error"
          }
        );
        return;
      }
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
          this.$message("Version switched successfully");
          this.reloadIntefaceDetail();
        } else {
          if (res.data.message) {
            alert("Failed to deploy: " + res.data.message);
          }
        }
      });
    }
  },
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
  font-size: 13px;
  padding: 2px;
}

.btn-style {
  color: #e9e9f3;
}

.btn-text {
  font-size: 12px;
  color: #6873ce;
}
</style>
