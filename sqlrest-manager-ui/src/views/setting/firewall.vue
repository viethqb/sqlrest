<template>
  <div>
    <h1 class="page-title">FIREWALL IP</h1>
    <el-card>
      <el-form label-width="200px">
        <el-form-item label="Firewall IP">
          <el-switch v-model="status"
                     active-color="#13ce66"
                     active-value="ON"
                     inactive-value="OFF"
                     active-text="On"
                     inactive-text="Off">
          </el-switch>
        </el-form-item>
        <div v-show="status=='ON'">
          <el-form-item label="List">
            <el-radio-group v-model="mode"
                            @change="modeChange">
              <el-radio label="BLACK">Blacklist</el-radio>
              <el-radio label="WHITE">Whitelist</el-radio>
            </el-radio-group>
            <el-alert title="IPs in the blacklist are denied access to APIs, all other IPs are allowed"
                      type="warning"
                      :closable="false"
                      v-show="mode == 'BLACK'"></el-alert>
            <el-alert title="Only IPs in the whitelist are allowed to access APIs, all other IPs are denied"
                      type="warning"
                      :closable="false"
                      v-show="mode == 'WHITE'"></el-alert>
          </el-form-item>
          <el-form-item label="Blacklist IPs"
                        v-show="mode == 'BLACK'">
            <el-input type="textarea"
                      :autosize="{ minRows: 8, maxRows: 20 }"
                      v-model="addresses"
                      placeholder="One IP per line, multiple IPs separated by newlines.">
            </el-input>
          </el-form-item>
          <el-form-item label="Whitelist IPs"
                        v-show="mode == 'WHITE'">
            <el-input type="textarea"
                      :autosize="{ minRows: 8, maxRows: 20 }"
                      v-model="addresses"
                      placeholder="One IP per line, multiple IPs separated by newlines.">
            </el-input>
          </el-form-item>
        </div>
        <el-form-item>
          <el-button type="primary"
                     @click="handleSave"
                     plain>Save</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>

export default {
  name: "firewall",
  data () {
    return {
      status: "ON",
      mode: "BLACK",
      addresses: ""
    }
  },
  methods: {
    loadData: function () {
      this.$http.get("/sqlrest/manager/api/v1/firewall/detail").then(res => {
        if (0 === res.data.code) {
          this.status = res.data.data.status;
          this.mode = res.data.data.mode;
          this.addresses = res.data.data.addresses;
        } else {
          alert("Failed to load data: " + res.data.message);
        }
      }
      );
    },
    modeChange (p) {
      console.log(p)
    },
    handleSave () {
      if (this.status === 'ON' && (!this.addresses || /^\s*$/.test(this.addresses))) {
        alert('IP list cannot be empty!')
        return
      }
      this.$http({
        method: "POST",
        headers: {
          'Content-Type': 'application/json'
        },
        url: "/sqlrest/manager/api/v1/firewall/save",
        data: JSON.stringify({
          status: this.status,
          mode: this.mode,
          addresses: this.addresses
        })
      }).then(res => {
        if (0 === res.data.code) {
          this.$alert("Firewall IP saved successfully", "Information",
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
