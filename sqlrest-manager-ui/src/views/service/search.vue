<template>
  <div>
    <h1 class="page-title">ONLINE APIs</h1>
    <el-card>
      <div style="margin-bottom: 15px; display: flex; gap: 15px; align-items: center;">
        <el-input v-model="searchKeyword"
                  placeholder="Please enter API name keyword to search"
                  size="small"
                  style="width: 400px;"
                  @keyup.enter="handleSearch">
        </el-input>
        <el-button type="primary"
                   size="small"
                   @click="handleSearch">
          Search
        </el-button>
        <el-checkbox-group v-model="selectedModuleList"
                           @change="handleSearch"
                           style="margin-left: 20px;">
          <el-checkbox v-for="item in moduleItemList"
                       :key="item.id"
                       :label="item.id"
                       border
                       size="small">
            {{ item.name }}
          </el-checkbox>
        </el-checkbox-group>
      </div>

      <el-table :data="allData"
                size="small"
                border>
        <el-table-column prop="id"
                         label="ID"
                         min-width="8%"></el-table-column>
        <el-table-column prop="name"
                         label="Name"
                         show-overflow-tooltip
                         min-width="18%">
          <template slot-scope="scope">
            <el-link type="primary"
                     @click="handleGoDetail(scope.row)">{{ scope.row.name }}</el-link>
            <i v-if="!scope.row.open"
               class="el-icon-user"
               style="margin-left: 5px; color: #909399;"
               title="Authentication Required"></i>
          </template>
        </el-table-column>
        <el-table-column label="Path"
                         show-overflow-tooltip
                         min-width="25%">
          <template slot-scope="scope">
            <el-tag size="small"
                    class="method-tag">{{ scope.row.method }}</el-tag>
            <span style="margin-left: 5px;">{{ scope.row.path }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="moduleName"
                         label="Module"
                         show-overflow-tooltip
                         min-width="12%"></el-table-column>
        <el-table-column prop="groupName"
                         label="Authorization Group"
                         show-overflow-tooltip
                         min-width="15%"></el-table-column>
        <el-table-column label="Version"
                         min-width="10%">
          <template slot-scope="scope">
            <el-tag size="small">V{{ scope.row.version }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime"
                         label="Create Time"
                         min-width="15%"></el-table-column>
        <el-table-column label="Actions"
                         min-width="15%"
                         align="center">
          <template slot-scope="scope">
            <el-tooltip content="Details" placement="top" effect="dark">
              <el-button plain size="mini" type="primary" @click="handleGoDetail(scope.row)" circle>
                <i class="el-icon-view"></i>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <div class="page" align="right" style="margin-top: 20px;">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currentPage"
                       :page-sizes="[10, 20, 50, 100]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="totalCount">
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'ServiceSearch',
  data () {
    return {
      searchKeyword: '',
      moduleItemList: [],
      selectedModuleList: [],
      currentPage: 1,
      pageSize: 12,
      totalCount: 0,
      allData: [],
    }
  },
  methods: {
    loadModules: function () {
      this.moduleItemList = [];
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
            this.moduleItemList = res.data.data || [];
          } else {
            this.moduleItemList = [];
            if (res.data.message) {
              alert("Failed to load module list: " + res.data.message);
            }
          }
        }
      );
    },
    loadInterface: function () {
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/assignment/search",
        data: window.JSON.stringify(
          {
            moduleIds: this.selectedModuleList,
            searchText: this.searchKeyword,
            page: this.currentPage,
            size: this.pageSize
          }
        )
      }).then(res => {
        if (0 === res.data.code) {
          this.currentPage = res.data.pagination.page;
          this.pageSize = res.data.pagination.size;
          this.totalCount = res.data.pagination.total;
          this.allData = res.data.data;
        } else {
          alert("Failed to load list: " + res.data.message);
        }
      }
      );
    },
    handleSearch () {
      this.currentPage = 1
      this.loadInterface();
    },
    handleSizeChange (val) {
      this.pageSize = val
      this.currentPage = 1
      this.loadInterface();
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.loadInterface();
    },
    handleGoDetail (row) {
      this.$router.push("/service/detail?id=" + row.id + "&commitId=" + row.commitId);
    }
  },
  created () {
    this.loadModules();
    this.loadInterface();
  }
}
</script>

<style scoped>
.el-card {
  width: 100%;
}

.method-tag {
  font-weight: bold;
}

.page {
  margin-top: 20px;
}
</style>
