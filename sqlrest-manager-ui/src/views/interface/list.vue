<template>
  <div>
    <h1 class="page-title">API CONFIG</h1>
    <el-card>
      <div class="assignment-list-top">
        <div class="left-search-input-group">
          <div class="left-search-input">
            <el-radio-group v-model="online"
                            @change="handleSearch"
                            size="small">
              <el-radio-button :label="false">In Development</el-radio-button>
              <el-radio-button :label="true">Online</el-radio-button>
            </el-radio-group>
            <el-select v-model="groupId"
                       size="small"
                       :clearable="true"
                       style="width:15%"
                       placeholder="Select Authorization Group">
              <el-option v-for="(item,index) in groupLists"
                         :key="index"
                         :label="item.name"
                         :value="item.id"></el-option>
            </el-select>
            <el-select v-model="moduleId"
                       size="small"
                       :clearable="true"
                       style="width:15%"
                       placeholder="Please select module">
              <el-option v-for="(item,index) in moduleLists"
                         :key="index"
                         :label="item.name"
                         :value="item.id"></el-option>
            </el-select>
            <el-select v-model="open"
                       size="small"
                       :clearable="true"
                       style="width:10%"
                       placeholder="Public">
              <el-option :key=true
                         label="Yes"
                         :value=true></el-option>
              <el-option :key=false
                         label="No"
                         :value=false></el-option>
            </el-select>
            <el-input placeholder="Name search"
                      size="small"
                      v-model="keyword"
                      :clearable=true
                      style="width:15%"
                      @change="searchByKeyword">
            </el-input>
            <el-button type="primary"
                       size="small"
                       @click="handleSearch">Search</el-button>
            <el-switch v-model="apiDocStatus"
                       name="Swagger Documentation Switch"
                       active-color="#13ce66"
                       inactive-color="#ff4949"
                       :active-value=true
                       :inactive-value=false
                       v-if="online"
                       active-text="Doc On"
                       inactive-text="Doc Off"
                       @change="hanldeSwitchApiDoc()">
            </el-switch>
          </div>
        </div>
        <div class="right-add-button-group">
          <el-button type="warning"
                     size="small"
                     :disabled="isSelected"
                     plain
                     icon="el-icon-download"
                     @click="handleBatchExport">Export APIs</el-button>
          <el-upload :action="uploadAssignmentPath"
                     accept="application/json"
                     :http-request="handleFileUpload"
                     v-if="!online"
                     :multiple="false"
                     :show-file-list="false"
                     :auto-upload="true">
            <el-button type="warning"
                       size="small"
                       plain
                       v-if="!online"
                       icon="el-icon-upload2">Import APIs</el-button>
          </el-upload>
          <el-button type="warning"
                     size="small"
                     :disabled="apiDocStatus==false"
                     v-if="online"
                     icon="el-icon-document-add"
                     @click="openOnlineApiDoc">Online Documentation</el-button>
          <el-button type="primary"
                     size="small"
                     v-if="!online"
                     @click="handleCreate">New API</el-button>
        </div>
      </div>

      <el-table :data="tableData"
                size="small"
                @selection-change="handleSelectionChange"
                border>
        <el-table-column prop="id"
                         type="selection"
                         min-width="8%"></el-table-column>
        <el-table-column prop="id"
                         label="ID"
                         min-width="8%"></el-table-column>
        <el-table-column prop="name"
                         label="Name"
                         show-overflow-tooltip
                         min-width="20%">
          <template slot-scope="scope">
            <el-link class="btn-text"
                     type="primary"
                     @click="handleDetail(scope.$index, scope.row)">{{ scope.row.name }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="Path"
                         show-overflow-tooltip
                         min-width="20%">
          <template slot-scope="scope">
            <el-tag size="medium"
                    class="name-wrapper-tag method-tag">{{ scope.row.method }}</el-tag>
            {{ scope.row.path }}
          </template>
        </el-table-column>
        <el-table-column prop="moduleName"
                         label="Module"
                         show-overflow-tooltip
                         min-width="10%"></el-table-column>
        <el-table-column prop="groupName"
                         label="Authorization Group"
                         show-overflow-tooltip
                         min-width="15%"></el-table-column>
        <el-table-column label="Engine"
                         min-width="10%">
          <template slot-scope="scope">
            <el-tag size="medium"
                    class="name-wrapper-tag method-tag">{{ scope.row.engine }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status"
                         label="Online"
                         :formatter="boolFormatPublish"
                         show-overflow-tooltip
                         v-if="online"
                         min-width="8%"></el-table-column>
        <el-table-column prop="open"
                         label="Public"
                         :formatter="boolFormatOpen"
                         v-if="online"
                         show-overflow-tooltip
                         min-width="8%"></el-table-column>
        <el-table-column prop="alarm"
                         label="Alarm"
                         :formatter="boolFormatAlarm"
                         v-if="online"
                         show-overflow-tooltip
                         min-width="8%"></el-table-column>
        <el-table-column prop="createTime"
                         label="Create Time"
                         min-width="18%"></el-table-column>
        <el-table-column label="Actions"
                         min-width="25%">
          <template slot-scope="scope">
            <el-tooltip content="Deploy" placement="top" effect="dark" v-if="scope.row.status===false">
              <el-button plain size="mini" type="primary" @click="handleOnline(scope.$index, scope.row)" circle>
                <i class="el-icon-timer"></i>
              </el-button>
            </el-tooltip>
            <el-tooltip content="Retire" placement="top" effect="dark" v-if="scope.row.status===true">
              <el-button plain size="mini" type="info" @click="handleOffline(scope.$index, scope.row)" circle>
                <i class="el-icon-delete-location"></i>
              </el-button>
            </el-tooltip>
            <el-tooltip content="Edit" placement="top" effect="dark">
              <el-button plain size="mini" type="warning" @click="handleUpdate(scope.$index, scope.row)" circle>
                <i class="el-icon-edit"></i>
              </el-button>
            </el-tooltip>
            <el-tooltip content="Publish" placement="top" effect="dark">
              <el-button plain size="mini" type="success" @click="handlePublish(scope.$index, scope.row)" circle>
                <i class="el-icon-position"></i>
              </el-button>
            </el-tooltip>
            <el-tooltip content="Delete" placement="top" effect="dark" v-if="scope.row.status===false">
              <el-button plain size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)" circle>
                <i class="el-icon-delete"></i>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <div class="page"
           align="right">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currentPage"
                       :page-sizes="[5, 10, 20, 40]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="totalCount"></el-pagination>
      </div>
    </el-card>

    <el-dialog title="Publish New Version"
               :visible.sync="publishDialogVisible"
               :showClose="false"
               width="40%"
               :before-close="handleClose">
      <el-form size="mini"
               status-icon>
        <el-form-item label="Version Description"
                      label-width="120px"
                      :required=true
                      style="width:85%">
          <el-input type="textarea"
                    :autosize="{ minRows: 4, maxRows: 10 }"
                    v-model="publishVersionDesc"
                    auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="publishDialogVisible = false">Cancel</el-button>
        <el-button type="primary"
                   @click="handlePublishVersion">Publish</el-button>
      </div>
    </el-dialog>

    <el-dialog title="Select Version to Deploy"
               :visible.sync="versionDialogVisible"
               :showClose="false"
               width="40%"
               :before-close="handleClose">
      <el-table :data="versions"
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
                   @click="handleDeploy">Deploy</el-button>
      </div>
    </el-dialog>

    <el-dialog title="Please Select Online Documentation Type"
               :visible.sync="selectOpenApiDocsDialogVisible"
               :showClose="false"
               width="20%"
               :before-close="handleClose">
      <el-select v-model="selectedOpenApiDocType"
                 size="mini"
                 style="width:95%"
                 placeholder="Please select documentation type">
        <el-option v-for="(item,index) in openApiDocs"
                   :key="index"
                   :label="item.key"
                   :value="item.key"></el-option>
      </el-select>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="selectOpenApiDocsDialogVisible = false">Cancel</el-button>
        <el-button type="primary"
                   @click="handleOpenApiDoc">Open</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {

  data () {
    return {
      loading: true,
      currentPage: 1,
      pageSize: 10,
      totalCount: 2,
      keyword: null,
      groupId: null,
      moduleId: null,
      online: false,
      open: null,
      apiDocStatus: true,
      groupLists: [],
      moduleLists: [],
      tableData: [],
      publishDialogVisible: false,
      publishVersionDesc: '',
      versionDialogVisible: false,
      selectRowId: 0,
      selectCommitId: 0,
      versions: [],
      openApiDocs: [{ key: "swagger" }, { key: "knife4j" }],
      selectOpenApiDocsDialogVisible: false,
      selectedOpenApiDocType: "swagger",
      isSelected: true,
      idsSelected: [],
      uploadAssignmentPath: process.env.API_ROOT + '/sqlrest/manager/api/v1/assignment/import',
    };
  },
  methods: {
    handleClose (done) {
    },
    loadData: function () {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/list",
        data: window.JSON.stringify(
          {
            groupId: this.groupId,
            moduleId: this.moduleId,
            online: this.online,
            open: this.open,
            searchText: this.keyword,
            page: this.currentPage,
            size: this.pageSize
          }
        )
      }).then(res => {
        if (0 === res.data.code) {
          this.currentPage = res.data.pagination.page;
          this.pageSize = res.data.pagination.size;
          this.totalCount = res.data.pagination.total;
          this.tableData = res.data.data;
        } else {
          alert("Failed to load list: " + res.data.message);
        }
      }
      );
    },
    loadGroupList () {
      this.groupLists = [];
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
            this.groupLists = res.data.data;
          } else {
            alert("Failed to load: " + res.data.message);
          }
        }
      );
    },
    loadModuleList () {
      this.moduleLists = [];
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
            this.moduleLists = res.data.data;
          } else {
            alert("Failed to load: " + res.data.message);
          }
        }
      );
    },
    loadApiDocOpenStatus () {
      this.$http.get(
        "/sqlrest/manager/api/v1/param/value/query?key=apiDocOpen"
      ).then(res => {
        if (0 === res.data.code) {
          this.apiDocStatus = res.data.data;
        } else {
          if (res.data.message) {
            alert("Operation failed: " + res.data.message);
          }
        }
      });
    },
    searchByKeyword: function () {
      this.currentPage = 1;
      this.loadData();
    },
    hanldeSwitchApiDoc: function () {
      this.$http.post(
        "/sqlrest/manager/api/v1/param/value/update?key=apiDocOpen&value=" + this.apiDocStatus
      ).then(res => {
        if (0 === res.data.code) {
          this.loadApiDocOpenStatus();
        } else {
          if (res.data.message) {
            alert("Operation failed: " + res.data.message);
          }
        }
      });
    },
    boolFormatPublish (row) {
      if (row.status === true) {
        return "V" + row.version;
      } else {
        return "No";
      }
    },
    boolFormatOpen (row) {
      if (row.open === true) {
        return "Yes";
      } else {
        return "No";
      }
    },
    boolFormatAlarm (row) {
      if (row.alarm === true) {
        return "On";
      } else {
        return "Off";
      }
    },
    handleSearch: function () {
      this.loadData();
    },
    handleCreate: function () {
      this.$router.push('/interface/create')
    },
    openOnlineApiDoc: function () {
      this.selectedOpenApiDocType = this.openApiDocs[0].key;
      this.selectOpenApiDocsDialogVisible = true;
    },
    handleOpenApiDoc: function () {
      this.$http({
        method: "GET",
        url: "/sqlrest/manager/api/v1/node/gateway"
      }).then(
        res => {
          if (0 === res.data.code) {
            if (res.data.data && typeof res.data.data === 'string') {
              var url = res.data.data + '/apidoc/' + this.selectedOpenApiDocType + '/index.html';
              window.open(url, '_blank');
            }
          } else {
            if (res.data.message) {
              alert("Operation failed: " + res.data.message);
            }
          }
          this.selectOpenApiDocsDialogVisible = false;
        }
      );
    },
    handleDetail: function (index, row) {
      this.$router.push({ path: '/interface/detail', query: { id: row.id } })
    },
    handleUpdate: function (index, row) {
      this.$router.push({ path: '/interface/update', query: { id: row.id } })
    },
    handleDelete: function (index, row) {
      this.$confirm(
        "This operation will delete API ID=" + row.id + ", continue?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning"
        }
      ).then(() => {
        this.$http.delete(
          "/sqlrest/manager/api/v1/assignment/delete/" + row.id
        ).then(res => {
          if (0 === res.data.code) {
            this.loadData();
          } else {
            if (res.data.message) {
              alert("Failed to delete: " + res.data.message);
            }
          }
        });
      });
    },
    handlePublish: function (index, row) {
      this.selectRowId = row.id
      this.publishDialogVisible = true
    },
    handlePublishVersion: function () {
      if (!this.publishVersionDesc) {
        this.$alert("Version description cannot be empty", "Error",
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
        url: "/sqlrest/manager/api/v1/assignment/publish",
        data: JSON.stringify({
          id: this.selectRowId,
          description: this.publishVersionDesc,
        })
      }).then(res => {
        if (0 === res.data.code) {
          this.selectRowId = null;
          this.publishVersionDesc = null;
          this.publishDialogVisible = false
          this.$message("Version published successfully");
        } else {
          if (res.data.message) {
            alert("Failed to publish version: " + res.data.message);
          }
        }
      });
    },
    handleOnline: function (index, row) {
      this.$http({
        method: "GET",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/version/list/" + row.id,
      }).then(res => {
        if (0 === res.data.code) {
          this.versions = res.data.data;
          this.selectRowId = row.id;
          this.versionDialogVisible = true;
        } else {
          if (res.data.message) {
            alert("Failed to get version list: " + res.data.message);
          }
        }
      });
    },
    handleDeploy: function () {
      if (!this.selectCommitId || this.selectCommitId <= 0) {
        this.$alert("Please select a version", "Error",
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
        url: "/sqlrest/manager/api/v1/assignment/deploy/" + this.selectRowId + "?commitId=" + this.selectCommitId,
      }).then(res => {
        if (0 === res.data.code) {
          this.selectRowId = null;
          this.selectCommitId = null;
          this.versionDialogVisible = false;
          this.$message("Deployed successfully");
          this.loadData();
        } else {
          if (res.data.message) {
            alert("Failed to deploy: " + res.data.message);
          }
        }
      });
    },
    handleOffline: function (index, row) {
      this.$http({
        method: "PUT",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/retire/" + row.id,
      }).then(res => {
        if (0 === res.data.code) {
          this.$message("Retired successfully");
          this.loadData();
        } else {
          if (res.data.message) {
            alert("Failed to retire: " + res.data.message);
          }
        }
      });
    },
    handleSelectionChange (arr) {
      if (arr.length > 0) {
        this.isSelected = false;
        for (var item of arr) {
          if (!this.idsSelected.includes(item.id)) {
            this.idsSelected.push(item.id);
          }
        }
      } else {
        this.isSelected = true;
        this.idsSelected = []
      }
    },
    handleBatchExport: function () {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/export",
        data: JSON.stringify(this.idsSelected),
        responseType: 'blob',
      }).then(resp => {
        const headers = resp.headers;
        const contentType = headers['content-type'];
        if (!resp.data) {
          console.error('Response error: ', resp);
          return false;
        } else {
          const blob = new Blob([resp.data], { type: contentType });
          this.downloadFile(blob, "sqlrest_interfaces.json");
        }
      });
    },
    downloadFile: function (blob, fileName) {
      if ('download' in document.createElement('a')) {
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob); // Create download link
        link.download = fileName; // Downloaded file name
        link.style.display = 'none';
        document.body.appendChild(link);
        link.click(); // Click to download
        window.URL.revokeObjectURL(link.href); // Release blob object
        document.body.removeChild(link); // Remove element after download
      } else {
        // IE10+ download
        window.navigator.msSaveBlob(blob, fileName);
      }
    },
    handleFileUpload: function (options) {
      const formData = new FormData();
      formData.append("file", options.file);
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        url: "/sqlrest/manager/api/v1/assignment/import",
        data: formData,
      })
        .then(res => {
          if (0 === res.data.code) {
            this.$message.success("Import successful");
            this.loadData();
          } else {
            if (res.data.message) {
              this.$alert("Import failed: " + res.data.message, "Error",
                {
                  confirmButtonText: "OK",
                  type: "error"
                }
              );
            }
          }
        })
        .catch(err => {
          this.$message.error("Import failed: " + err);
        });
    },
    handleSizeChange: function (pageSize) {
      this.loading = true;
      this.pageSize = pageSize;
      this.loadData();
    },

    handleCurrentChange: function (currentPage) {
      this.loading = true;
      this.currentPage = currentPage;
      this.loadData();
    }
  },
  created () {
    this.loadGroupList();
    this.loadModuleList();
    this.loadApiDocOpenStatus();
    this.loadData();
  },
};
</script>

<style scoped>
.el-card,
.el-message {
  width: 100%;
  height: 100%;
  overflow: auto;
}

.el-table {
  width: 100%;
  height: 100%;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

.el-input.is-disabled .el-input__inner {
  background-color: #f5f7fa;
  border-color: #e4e7ed;
  color: #c0c4cc;
  cursor: pointer;
}

.assignment-list-top {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 5px;
  flex-wrap: wrap;
  gap: 10px;
}

.left-search-input-group {
  width: calc(100% - 200px);
  margin-right: auto;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}

.left-search-input {
  margin-right: auto;
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}

.right-add-button-group {
  width: auto;
  margin-left: auto;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  flex-wrap: wrap;
}

.method-tag {
  font-weight: bold !important;
}
</style>
