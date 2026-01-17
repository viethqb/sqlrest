<template>
  <div>
    <h1 class="page-title">CLIENT APPLICATION</h1>
    <el-card>
      <div class="client-list-top">
        <div class="left-search-input-group">
          <div class="left-search-input">
            <el-select v-model="groupId"
                       size="small"
                       @change="searchByKeyword"
                       :clearable="true"
                       style="width:200px"
                       placeholder="Select Authorization Group">
              <el-option v-for="(item,index) in groups"
                         :key="index"
                         :label="item.name"
                         :value="item.id"></el-option>
            </el-select>
            <el-input placeholder="Please enter name keyword to search"
                      size="small"
                      v-model="keyword"
                      @change="searchByKeyword"
                      :clearable=true
                      style="width:400px">
            </el-input>
          </div>
        </div>
        <div class="right-add-button-group">
          <el-button type="primary"
                     size="small"
                     @click="addClient">Add</el-button>
        </div>
      </div>

      <el-table :data="tableData"
                size="small"
                border>
        <el-table-column prop="id"
                         label="ID"
                         min-width="5%"></el-table-column>
        <el-table-column prop="name"
                         label="Application Name"
                         show-overflow-tooltip
                         min-width="18%"></el-table-column>
        <el-table-column prop="description"
                         label="Description"
                         show-overflow-tooltip
                         min-width="12%"></el-table-column>
        <el-table-column prop="appKey"
                         min-width="15%">
          <template slot="header">
            <span style="white-space: nowrap;">Application Account</span>
          </template>
          <template slot-scope="scope">
            <span :title="scope.row.appKey">{{ scope.row.appKey }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="expireDuration"
                         label="Expiration Time"
                         :formatter="stringFormatExpireDuration"
                         show-overflow-tooltip
                         min-width="15%"></el-table-column>
        <el-table-column prop="isExpired"
                         label="Expired"
                         show-overflow-tooltip
                         min-width="8%"
                         align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isExpired"
                    type="danger"
                    effect="dark"
                    size="mini">Expired
            </el-tag>
            <el-tag v-if="!scope.row.isExpired"
                    type="primary"
                    effect="dark"
                    size="mini">Not Expired
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="tokenAlive"
                         label="Token Lifetime"
                         :formatter="stringFormatTokenAlive"
                         show-overflow-tooltip
                         min-width="12%"></el-table-column>
        <el-table-column prop="createTime"
                         label="Create Time"
                         min-width="15%">
        </el-table-column>
        <el-table-column label="Actions"
                         min-width="15%"
                         align="center">
          <template slot-scope="scope">
            <el-tooltip content="Authorize" placement="top" effect="dark">
              <el-button plain size="mini" type="primary" @click="handleAuthorize(scope.$index, scope.row)" circle>
                <i class="el-icon-lock"></i>
              </el-button>
            </el-tooltip>
            <el-tooltip content="Secret" placement="top" effect="dark">
              <el-button plain size="mini" type="info" @click="handleShowSecret(scope.$index, scope.row)" circle>
                <i class="el-icon-view"></i>
              </el-button>
            </el-tooltip>
            <el-tooltip content="Delete" placement="top" effect="dark">
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

      <el-dialog title="Add Application Information"
                 :visible.sync="createFormVisible"
                 :showClose="false"
                 :before-close="handleClose">
        <el-form :model="createform"
                 size="small"
                 status-icon
                 :rules="rules"
                 ref="createform">
          <el-form-item label="Application Name"
                        label-width="120px"
                        :required=true
                        prop="name"
                        style="width:85%">
            <el-input v-model="createform.name"
                      size="small"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="Description"
                        label-width="120px"
                        prop="description"
                        style="width:85%">
            <el-input type="textarea"
                      size="small"
                      :rows="6"
                      :spellcheck="false"
                      placeholder="Please enter"
                      v-model="createform.description"
                      auto-complete="off">
            </el-input>
          </el-form-item>
          <el-form-item label="Application Account"
                        label-width="120px"
                        prop="appKey"
                        style="width:85%">
            <el-input v-model="createform.appKey"
                      size="small"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="Application Expiration Time"
                        label-width="120px"
                        prop="expireTime"
                        style="width:85%">
            <el-select v-model="createform.expireTime"
                       size="small">
              <el-option label="Never"
                         value="EXPIRE_FOR_EVER"></el-option>
              <el-option label="Once"
                         value="EXPIRE_ONLY_ONCE"></el-option>
              <el-option label="5 Minutes"
                         value="EXPIRE_05_MIN"></el-option>
              <el-option label="30 Minutes"
                         value="EXPIRE_30_MIN"></el-option>
              <el-option label="1 Hour"
                         value="EXPIRE_01_HOUR"></el-option>
              <el-option label="12 Hours"
                         value="EXPIRE_12_HOUR"></el-option>
              <el-option label="1 Day"
                         value="EXPIRE_01_DAY"></el-option>
              <el-option label="15 Days"
                         value="EXPIRE_15_DAY"></el-option>
              <el-option label="1 Month"
                         value="EXPIRE_01_MOUTH"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label-width="120px"
                        prop="tokenAlive"
                        style="width:35%">
            <span slot="label"
                  style="display:inline-block;">
              Token Lifetime
              <el-tooltip effect="dark"
                          content="‘'Short-term' token duration is 7200 seconds, renew by calling authentication API after expiration; 'Long-term' token never expires, but expires when application expires, and is deleted when application is deleted."
                          placement="bottom">
                <i class='el-icon-question' />
              </el-tooltip>
            </span>
            <el-select v-model="createform.tokenAlive"
                       size="small">
              <el-option label="Short-term"
                         value="PERIOD"></el-option>
              <el-option label="Long-term"
                         value="LONGEVITY"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="createFormVisible = false">Cancel</el-button>
          <el-button type="primary"
                     @click="handleCreate">OK</el-button>
        </div>
      </el-dialog>

      <el-dialog title="View Secret"
                 :visible.sync="ShowSecretDialog">
        <el-input type="input"
                  style="width:55%"
                  id="secretTextInput"
                  v-model="clientSecret"></el-input>
        <el-button @click="handleCopyText">Click to Copy</el-button>
        <span slot="footer">
          <el-button @click="ShowSecretDialog = false">Cancel</el-button>
        </span>
      </el-dialog>

      <el-dialog title="Authorization Group"
                 :visible.sync="showAuthDialog"
                 @open="loadAllGroups">
        <el-checkbox-group v-model="selectList">
          <el-checkbox v-for="item in groups"
                       :label="item.id"
                       :key="item.id">{{ item.name }}</el-checkbox>
        </el-checkbox-group>
        <span slot="footer">
          <el-button @click="showAuthDialog = false">Cancel</el-button>
          <el-button type="primary"
                     @click="handleSaveAuth()">Save</el-button>
        </span>
      </el-dialog>

    </el-card>
  </div>
</template>

<script>

export default {
  name: "client",
  components: {
  },
  data () {
    return {
      loading: true,
      groupId: null,
      keyword: null,
      lists: [],
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      tableData: [
      ],
      groups: [],
      clientId: 0,
      selectList: [],
      showAuthDialog: false,
      ShowSecretDialog: false,
      clientSecret: '',
      createform: {
        name: "",
        description: "",
        appKey: "",
        expireTime: "",
        tokenAlive: "",
      },
      rules: {
        name: [
          {
            required: true,
            message: "Name cannot be empty",
            trigger: "blur"
          }
        ],
        appKey: [
          {
            required: true,
            message: "Application account cannot be empty",
            trigger: "blur"
          }
        ],
        expireTime: [
          {
            required: true,
            message: "Expiration time must be selected",
            trigger: "change"
          }
        ],
        tokenAlive: [
          {
            required: true,
            message: "Token lifetime must be selected",
            trigger: "change"
          }
        ]
      },
      createFormVisible: false
    }
  },
  methods: {
    loadData: function () {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/client/list",
        data: JSON.stringify({
          groupId: this.groupId,
          searchText: this.keyword,
          page: this.currentPage,
          size: this.pageSize
        })
      }).then(res => {
        if (0 === res.data.code) {
          this.currentPage = res.data.pagination.page;
          this.pageSize = res.data.pagination.size;
          this.totalCount = res.data.pagination.total;
          this.tableData = res.data.data;
        } else {
          alert("Failed to load data: " + res.data.message);
        }
      });
    },
    loadAllGroups: function () {
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
      }).then((res) => {
        if (0 === res.data.code) {
          this.groups = res.data.data
        } else {
          alert("Failed to load data: " + res.data.message);
        }
      }).catch((error) => {
      })
    },
    stringFormatExpireDuration (row, column) {
      if (row.expireDuration === "FOR_EVER") {
        return "Never Expires";
      } else if (row.expireDuration === "ONLY_ONCE") {
        return "Expires Once";
      } else if (row.expireDuration === "TIME_VALUE") {
        return row.expireAtStr;
      }
      return "-";
    },
    stringFormatTokenAlive (row, column) {
      if (row.tokenAlive === "LONGEVITY") {
        return "Long-term";
      } else if (row.tokenAlive === "PERIOD") {
        return "Short-term";
      }
      return "-";
    },
    searchByKeyword: function () {
      this.currentPage = 1;
      this.loadData();
    },
    handleClose (done) {
    },
    handleDelete: function (index, row) {
      this.$confirm(
        "This operation will delete application ID=" + row.id + ", continue?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning"
        }
      ).then(() => {
        this.$http.delete(
          "/sqlrest/manager/api/v1/client/delete/" + row.id
        ).then(res => {
          if (0 === res.data.code) {
            this.loadData();
          } else {
            alert("Failed to delete: " + res.data.message);
          }
        });
      });
    },
    addClient: function () {
      this.createFormVisible = true;
      this.createform = {};
    },
    handleCreate: function () {
      this.$refs['createform'].validate(valid => {
        if (valid) {
          this.$http({
            method: "POST",
            headers: {
              'Content-Type': 'application/json'
            },
            url: "/sqlrest/manager/api/v1/client/create",
            data: JSON.stringify({
              name: this.createform.name,
              description: this.createform.description,
              appKey: this.createform.appKey,
              expireTime: this.createform.expireTime,
              tokenAlive: this.createform.tokenAlive,
            })
          }).then(res => {
            if (0 === res.data.code) {
              this.createFormVisible = false;
              this.$message("Information added successfully");
              this.createform = {};
              this.loadData();
            } else {
              alert("Failed to add information: " + res.data.message);
            }
          });
        } else {
          alert("Please check input");
        }
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
    },
    handleShowSecret: function (index, row) {
      this.ShowSecretDialog = true
      this.$http.get("/sqlrest/manager/api/v1/client/secret/" + row.id)
        .then((res) => {
          if (0 === res.data.code) {
            this.clientSecret = res.data.data
          } else {
            alert("Operation failed: " + res.data.message)
          }
        })
    },
    handleCopyText: function () {
      var d = document.getElementById("secretTextInput")
      d.select() // Select
      document.execCommand("copy")
      this.$message.success("Copied successfully")
    },
    handleAuthorize: function (index, row) {
      this.showAuthDialog = true
      this.$http.get("/sqlrest/manager/api/v1/client/auth/" + row.id)
        .then((res) => {
          this.selectList = []
          this.clientId = row.id
          for (let item of res.data) {
            this.selectList.push(item.id)
          }
        })
    },
    handleSaveAuth: function () {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/client/auth/create",
        data: JSON.stringify({
          id: this.clientId,
          groupIds: this.selectList
        })
      }).then(res => {
        if (0 === res.data.code) {
          this.showAuthDialog = false
        } else {
          alert("Operation failed: " + res.data.message);
        }
      });
    }
  },
  created () {
    this.loadData();
    this.loadAllGroups();
  }
};
</script>

<style scoped>
.el-table {
  width: 100%;
  height: 100%;
}
.el-card,
.el-message {
  width: 100%;
  height: 100%;
  overflow: auto;
}
.client-list-top {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.left-search-input-group {
  width: calc(100% - 100px);
  margin-right: auto;
  display: flex;
  justify-content: space-between;
}
.left-search-input {
  width: 80%;
  margin-right: auto;
  margin: 10px 5px;
}
.right-add-button-group {
  width: 100px;
  margin-left: auto;
  margin: 10px 5px;
}
</style>
