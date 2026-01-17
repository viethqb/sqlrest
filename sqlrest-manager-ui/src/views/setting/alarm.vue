<template>
  <div>
    <h1 class="page-title">ALARM CONFIG</h1>
    <el-card>
      <el-form label-width="200px">
        <el-form-item label="Alarm Configuration">
          <el-switch v-model="status"
                     active-color="#13ce66"
                     active-value="ON"
                     inactive-value="OFF"
                     active-text="On"
                     inactive-text="Off">
          </el-switch>
        </el-form-item>
        <div v-show="status=='ON'">
          <el-form-item label="API Endpoint">
            <span slot="label"
                  style="display:inline-block;">
              API Endpoint
              <el-tooltip effect="dark"
                          content="Please refer to the message sending API provided by the corresponding alarm system"
                          placement="bottom">
                <i class='el-icon-question' />
              </el-tooltip>
            </span>
            <el-input v-model="endpoint"
                      placeholder="Please enter API path, e.g.: http://127.0.0.1:8000/api/v1/message/send">
            </el-input>
          </el-form-item>
          <el-form-item label="Input Format">
            <span slot="label"
                  style="display:inline-block;">
              Input Format
              <el-tooltip effect="dark"
                          content="Input data format, currently only supports Content-Type as application/json"
                          placement="bottom">
                <i class='el-icon-question' />
              </el-tooltip>
            </span>
            <el-select v-model="contentType">
              <el-option label="application/json"
                         value="application/json"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Input Template">
            <span slot="label"
                  style="display:inline-block;">
              Input Template
              <el-tooltip effect="dark"
                          content="Please refer to the corresponding alarm system for input data format; click 'Test' button to view all supported variable parameters in the template."
                          placement="bottom">
                <i class='el-icon-question' />
              </el-tooltip>
            </span>
            <el-input type="textarea"
                      :autosize="{ minRows: 8, maxRows: 20 }"
                      v-model="inputTemplate"
                      placeholder="Please enter input content template, use ${xxx} as placeholder for variable parts.">
            </el-input>
          </el-form-item>
        </div>
        <el-form-item>
          <el-button type="primary"
                     v-show="status=='ON'"
                     @click="handleShowTest"
                     plain>Test</el-button>
          <el-button type="primary"
                     @click="handleSave"
                     plain>Save</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog title="Test Alarm Configuration"
               :visible.sync="testFormVisible"
               :showClose="false"
               :before-close="handleClose">
      <el-form label-width="200px">
        <el-form-item v-for="item in dataModel"
                      :key="item.key"
                      v-bind="item"
                      :label="`\$\{${item.key}\}`">
          <el-input type="text"
                    :key="item.key"
                    v-model="item.value"
                    :value="item.value"> </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="primary"
                   @click="handleSendTest"
                   plain>Test</el-button>
        <el-button type="primary"
                   @click="testFormVisible = false"
                   plain>Close</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: "alarm",
  data () {
    return {
      status: "ON",
      endpoint: "",
      contentType: "application/json",
      inputTemplate: "",
      testFormVisible: false,
      dataModel: {},
    }
  },
  methods: {
    loadData: function () {
      this.$http.get("/sqlrest/manager/api/v1/alarm/detail").then(res => {
        if (0 === res.data.code) {
          this.status = res.data.data.status;
          this.endpoint = res.data.data.endpoint;
          this.contentType = res.data.data.contentType;
          this.inputTemplate = res.data.data.inputTemplate;
        } else {
          alert("Failed to load data: " + res.data.message);
        }
      }
      );
    },
    handleShowTest () {
      this.$http.get("/sqlrest/manager/api/v1/alarm/example").then(res => {
        if (0 === res.data.code) {
          this.dataModel = res.data.data;
          this.testFormVisible = true;
        } else {
          alert("Failed to load data: " + res.data.message);
          return;
        }
      }
      );
    },
    handleClose (done) {
    },
    handleSendTest () {
      if (this.status === 'ON' && (!this.endpoint || /^\s*$/.test(this.endpoint))) {
        alert('API endpoint cannot be empty!')
        return
      }
      if (this.status === 'ON' && (!this.contentType || /^\s*$/.test(this.contentType))) {
        alert('Input type must be selected!')
        return
      }
      if (this.status === 'ON' && (!this.inputTemplate || /^\s*$/.test(this.inputTemplate))) {
        alert('Input template must be provided!')
        return
      }
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/alarm/test",
        data: JSON.stringify({
          endpoint: this.endpoint,
          contentType: this.contentType,
          inputTemplate: this.inputTemplate,
          dataModel: this.dataModel
        })
      }).then(res => {
        if (0 === res.data.code) {
          this.$alert("Test sent successfully, please check the alarm log in the alarm system", "Information",
            {
              confirmButtonText: "OK",
              type: "info"
            }
          );
        } else {
          this.$alert(res.data.message, "Information",
            {
              confirmButtonText: "OK",
              type: "error"
            }
          );
        }
      });
    },
    handleSave () {
      if (this.status === 'ON' && (!this.endpoint || /^\s*$/.test(this.endpoint))) {
        alert('API endpoint cannot be empty!')
        return
      }
      if (this.status === 'ON' && (!this.contentType || /^\s*$/.test(this.contentType))) {
        alert('Input type must be selected!')
        return
      }
      if (this.status === 'ON' && (!this.inputTemplate || /^\s*$/.test(this.inputTemplate))) {
        alert('Input template must be provided!')
        return
      }
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/alarm/save",
        data: JSON.stringify({
          status: this.status,
          endpoint: this.endpoint,
          contentType: this.contentType,
          inputTemplate: this.inputTemplate
        })
      }).then(res => {
        if (0 === res.data.code) {
          this.$alert("Alarm configuration saved successfully", "Information",
            {
              confirmButtonText: "OK",
              type: "info"
            }
          );
          this.loadData();
        } else {
          alert("Failed to save: " + res.data.message);
        }
      });
    }
  },
  created () {
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
</style>
