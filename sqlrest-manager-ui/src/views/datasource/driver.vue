<template>
  <div>
    <h1 class="page-title">DRIVER CONFIG</h1>
    <el-card>
      <div class="container">
        <div class="leftSection">
          <div class="navsBox">
            <div class="sectionTitle">
              <span><b>Database Type List</b></span>
            </div>
            <el-select 
              v-model="selectedDatabaseType"
              filterable
              placeholder="Search database type..."
              @change="handleDatabaseTypeChange"
              style="width: 100%; margin-top: 10px;">
              <el-option
                v-for="(item, index) in connectionTypes"
                :key="index"
                :label="formatDatabaseName(item.type)"
                :value="item.type">
              </el-option>
            </el-select>
          </div>
        </div>

        <div class="contentBox">
          <div align="right"
               style="margin:10px 5px;"
               width="95%">
            <el-button type="primary"
                       size="small"
                       @click="dialogVisible=true">Add</el-button>
          </div>
          <el-table :data="versionDrivers"
                    size="small"
                    border>
            <template slot="empty">
              <span>Please select a database type to view corresponding driver version information</span>
            </template>
            <el-table-column property="driverVersion"
                             label="Driver Version"
                             min-width="20%"></el-table-column>
            <el-table-column property="driverClass"
                             label="Driver Class Name"
                             min-width="30%"></el-table-column>
            <el-table-column property="jarFiles"
                             :formatter="formatJarFileList"
                             label="Driver JAR Names"
                             min-width="40%"></el-table-column>
          </el-table>
        </div>
      </div>
    </el-card>
    <el-dialog title="Add Database Driver JAR Instructions"
               :visible.sync="dialogVisible"
               width="40%"
               :before-close="handleClose">
      <span>Please follow the driver path directory ${SQLREST_HOME}/drivers, create a directory named after the driver version number under the directory named after the database type, and place the corresponding driver jar files, then restart to take effect. For details, refer to the directory structure at https://gitee.com/inrgihc/sqlrest/tree/master/drivers.</span>
      <span></span>
      <span>Special Note: All JARs in the driver version directory must have no external dependencies, otherwise, their dependent JARs must also be placed in the corresponding directory.</span>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary"
                   @click="dialogVisible = false">OK</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dialogVisible: false,
      loading: true,
      connectionTypes: [],
      versionDrivers: [],
      isActive: -1,
      selectedDatabaseType: null,
    };
  },
  methods: {
    formatDatabaseName: function (type) {
      if (!type) return '';
      // Convert to lowercase, then capitalize first letter
      return type.charAt(0).toUpperCase() + type.slice(1).toLowerCase();
    },
    loadConnectionTypes: function () {
      this.$http({
        method: "GET",
        url: "/sqlrest/manager/api/v1/datasource/types"
      }).then(res => {
        if (0 === res.data.code) {
          this.connectionTypes = res.data.data;
          if (this.connectionTypes.length > 0) {
            const firstType = this.connectionTypes[0].type;
            this.selectedDatabaseType = firstType;
            this.handleChooseClick(firstType, 0);
          }
        } else {
          if (res.data.message) {
            alert("Failed to initialize database type information: " + res.data.message);
          }
        }
      }
      );
    },
    handleChooseClick: function (type, index) {
      this.isActive = index;
      this.selectedDatabaseType = type;
      this.loadDriverVersions(type);
    },
    handleDatabaseTypeChange: function (type) {
      if (type) {
        const index = this.connectionTypes.findIndex(item => item.type === type);
        this.isActive = index >= 0 ? index : -1;
        this.loadDriverVersions(type);
      }
    },
    loadDriverVersions: function (type) {
      this.$http.get(
        "/sqlrest/manager/api/v1/datasource/" + type + "/drivers"
      ).then(res => {
        if (0 === res.data.code) {
          this.versionDrivers = res.data.data;
        } else {
          if (res.data.message) {
            alert("Failed to query driver version information: " + res.data.message);
          }
        }
      });
    },
    handleClose (done) {
      this.$confirm('Confirm close?')
        .then(_ => {
          done();
        })
        .catch(_ => { });
    },
    formatJarFileList: function (row, column) {
      let jarFiles = row[column.property];
      return jarFiles.join(';\n');
    }
  },
  created () {
    this.loadConnectionTypes();
  },
  beforeDestroy () {
  },
};
</script>

<style scoped>
.el-table {
  width: 100%;
  border-collapse: collapse;
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

.filter {
  margin: 10px;
}

.container {
  display: flex;
  gap: 20px;
}

.container .leftSection {
  width: 25%;
  min-width: 250px;
  display: flex;
  flex-direction: column;
  padding: 10px;
  align-items: flex-start;
}

.container .navsBox {
  padding: 0;
  margin-top: 42px; /* Align with table header (button margin + button height) */
  width: 100%;
}

.container .leftSection .sectionTitle {
  padding: 0;
  text-align: left;
  font-size: 14px;
  color: #606266;
  font-weight: 500;
}

.container .contentBox {
  padding: 10px;
  flex: 1;
  min-width: 0;
}

.container .contentBox > div:first-child {
  margin-bottom: 10px;
}
</style>
