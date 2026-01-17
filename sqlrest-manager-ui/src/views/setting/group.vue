<template>
  <div>
    <h1 class="page-title">AUTHORIZATION GROUP</h1>
    <el-card>
      <div class="group-list-top">
        <div class="left-search-input-group">
          <div class="left-search-input">
            <el-input placeholder="Please enter name keyword to search"
                      size="small"
                      v-model="searchText"
                      @change="searchByKeyword"
                      :clearable=true
                      style="width:400px">
            </el-input>
          </div>
        </div>
        <div class="right-add-button-group">
          <el-button type="primary"
                     size="small"
                     @click="addGroup">Add</el-button>
        </div>
      </div>

      <el-table :data="tableData"
                size="small"
                border>
        <el-table-column prop="id"
                         label="ID"
                         min-width="5%"></el-table-column>
        <el-table-column prop="name"
                         label="Group Name"
                         show-overflow-tooltip
                         min-width="20%"></el-table-column>
        <el-table-column prop="createTime"
                         label="Create Time"
                         min-width="20%"></el-table-column>
        <el-table-column prop="updateTime"
                         label="Update Time"
                         show-overflow-tooltip
                         min-width="20%"></el-table-column>
        <el-table-column label="Actions"
                         min-width="20%">
          <template slot-scope="scope">
            <el-tooltip content="Associate" placement="top" effect="dark">
              <el-button plain size="mini" type="info" @click="handleRelation(scope.$index, scope.row)" circle>
                <i class="el-icon-link"></i>
              </el-button>
            </el-tooltip>
            <el-tooltip content="Edit" placement="top" effect="dark">
              <el-button plain size="mini" type="warning" @click="handleUpdate(scope.$index, scope.row)" circle>
                <i class="el-icon-edit"></i>
              </el-button>
            </el-tooltip>
            <el-tooltip content="Delete" placement="top" effect="dark" v-if="scope.row.id!==1">
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
                       :current-page="currentPageNum"
                       :page-sizes="[5, 10, 20, 40]"
                       :page-size="currentPageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="totalItemCount"></el-pagination>
      </div>

      <el-dialog title="Add Information"
                 :visible.sync="createFormVisible"
                 :showClose="false"
                 :before-close="handleClose">
        <el-form :model="createform"
                 size="small"
                 status-icon
                 :rules="rules"
                 ref="createform">
          <el-form-item label="Group Name"
                        label-width="120px"
                        :required=true
                        prop="name"
                        style="width:85%">
            <el-input v-model="createform.name"
                      size="small"
                      auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="createFormVisible = false">Cancel</el-button>
          <el-button type="primary"
                     @click="handleCreate">OK</el-button>
        </div>
      </el-dialog>

      <el-dialog title="Update Information"
                 :visible.sync="updateFormVisible"
                 :showClose="false"
                 :before-close="handleClose">
        <el-form :model="updateform"
                 size="small"
                 status-icon
                 :rules="rules"
                 ref="updateform">
          <el-form-item label="Group Name"
                        label-width="120px"
                        :required=true
                        prop="name"
                        style="width:85%">
            <el-input v-model="updateform.name"
                      size="small"
                      auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="updateFormVisible = false">Cancel</el-button>
          <el-button type="primary"
                     @click="handleSave">OK</el-button>
        </div>
      </el-dialog>

      <el-dialog title="Update Association Information"
                 :visible.sync="relationFormVisible"
                 :showClose="false"
                 :before-close="handleClose">
        <el-alert title="When unchecking items and performing update operation, unchecked items will be reassociated to the 'Default Group' with id=1."
                  type="warning"
                  show-icon>
        </el-alert>
        <el-tree :data="moduleAssignments"
                 :show-checkbox="true"
                 ref="relationTree"
                 node-key="id"
                 :default-checked-keys="initCheckedKeys"
                 highlight-current
                 :props="defaultProps">
        </el-tree>
        <div slot="footer"
             class="dialog-footer">
          <el-button type="primary"
                     @click="relationFormVisible = false">Close</el-button>
          <el-button type="danger"
                     v-if="currentGroupId!==1"
                     @click="handleRelationSave">Update</el-button>
        </div>
      </el-dialog>

    </el-card>
  </div>
</template>

<script>
import qs from "qs";
import Vue from "vue";

export default {
  name: "group",
  components: {
  },
  data () {
    return {
      loading: true,
      lists: [],
      tableData: [
      ],
      currentPageNum: 1,
      currentPageSize: 10,
      totalItemCount: 0,
      searchText: '',
      createform: {
        title: "",
      },
      updateform: {
        id: 0,
        title: "",
      },
      rules: {
        name: [
          {
            required: true,
            message: "Name cannot be empty",
            trigger: "blur"
          }
        ]
      },
      createFormVisible: false,
      updateFormVisible: false,
      relationFormVisible: false,
      moduleAssignments: [],
      initCheckedKeys: [],
      currentGroupId: 0,
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
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
    loadData: function () {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/group/listAll",
        data: JSON.stringify({
          page: this.currentPageNum,
          size: this.currentPageSize,
          searchText: this.searchText
        })
      }).then(res => {
        if (0 === res.data.code) {
          this.totalItemCount = res.data.pagination.total
          this.tableData = res.data.data;
        } else {
          alert("Failed to load data: " + res.data.message);
        }
      }
      );
    },
    handleClose (done) {
    },
    handleDelete: function (index, row) {
      this.$confirm(
        "This operation will delete group ID=" + row.id + ", continue?",
        "Warning",
        {
          confirmButtonText: "OK",
          cancelButtonText: "Cancel",
          type: "warning"
        }
      ).then(() => {
        this.$http.delete(
          "/sqlrest/manager/api/v1/group/delete/" + row.id
        ).then(res => {
          if (0 === res.data.code) {
            this.loadData();
          } else {
            alert("Failed to delete: " + res.data.message);
          }
        });
      });
    },
    addGroup: function () {
      this.createFormVisible = true;
      this.createform = {};
    },
    handleCreate: function () {
      this.$refs['createform'].validate(valid => {
        if (valid) {
          this.$http({
            method: "POST",
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            url: "/sqlrest/manager/api/v1/group/create",
            data: qs.stringify({
              name: this.createform.name
            })
          }).then(res => {
            if (0 === res.data.code) {
              this.createFormVisible = false;
              this.$message("Added successfully");
              this.createform = {};
              this.loadData();
            } else {
              alert("Failed to add: " + res.data.message);
            }
          });
        } else {
          alert("Please check input");
        }
      });
    },
    handleRelation: function (index, row) {
      this.moduleAssignments = [];
      this.$http.get(
        "/sqlrest/manager/api/v1/module/moduleTree/" + row.id
      ).then(res => {
        if (0 === res.data.code) {
          this.initCheckedKeys = [];
          this.currentGroupId = row.id;
          this.moduleAssignments = res.data.data;
          for (let item of this.moduleAssignments) {
            Vue.set(item, 'id', this.uuid());
            if (item.children) {
              for (let one of item.children) {
                //Vue.set(one, 'disabled', true);
                if (one.selected) {
                  this.initCheckedKeys.push(one.id);
                }
              }
            }
          }
        }
      });
      this.relationFormVisible = true;
    },
    handleRelationSave: function () {
      let checkedKeys = this.$refs.relationTree.getCheckedKeys(true);
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/group/" + this.currentGroupId,
        data: checkedKeys
      }).then(res => {
        if (0 === res.data.code) {
          this.relationFormVisible = false;
          this.$message("Association information updated successfully.");
        } else {
          alert("Failed to update: " + res.data.message);
        }
      });
    },
    handleUpdate: function (index, row) {
      this.updateform = JSON.parse(JSON.stringify(row));
      this.updateFormVisible = true;
    },
    handleSave: function () {
      this.$refs['updateform'].validate(valid => {
        if (valid) {
          this.$http({
            method: "POST",
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            url: "/sqlrest/manager/api/v1/group/update/" + this.updateform.id,
            data: qs.stringify({
              name: this.updateform.name,
            })
          }).then(res => {
            if (0 === res.data.code) {
              this.updateFormVisible = false;
              this.$message("Updated successfully");
              this.loadData();
              this.updateform = {};
            } else {
              alert("Failed to update: " + res.data.message);
            }
          });
        } else {
          alert("Please check input");
        }
      });
    },
    handleSizeChange: function (pageSize) {
      this.currentPageSize = pageSize;
      this.loadData();
    },
    handleCurrentChange: function (currentPage) {
      this.currentPageNum = currentPage;
      this.loadData();
    },
    searchByKeyword: function () {
      this.currentPage = 1;
      this.loadData();
    },
  },
  mounted () {
    this.loadData();
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
.group-list-top {
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
  width: 300px;
  margin-right: auto;
  margin: 10px 5px;
}
.right-add-button-group {
  width: 100px;
  margin-right: 5px;
  margin: 10px 5px;
}
</style>
