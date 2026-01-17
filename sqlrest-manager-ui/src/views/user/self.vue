<template>
  <div>
    <h1 class="page-title">PERSONAL CENTER</h1>
    <el-card class="box-card">
      <el-tabs v-model="activeName"
               @tab-click="handleClick">
        <image style="width: 100px; height: 100px"
               src="../../assets/logo.png" />
        <el-tab-pane label="Account Information"
                     name="userinfo">
          <el-card>
            <el-description title="Account Basic Information">
              <el-description-item label="Account"
                                   :span='15'
                                   :value="userinfo.username"></el-description-item>
              <el-description-item label="Name"
                                   :span='15'
                                   :value="userinfo.realName"></el-description-item>
              <el-description-item label="Email"
                                   :span='15'
                                   :value="userinfo.email"></el-description-item>
              <el-description-item label="Address"
                                   :span='15'
                                   :value="userinfo.address"></el-description-item>
              <el-description-item label="Locked"
                                   :span='15'
                                   :value="userinfo.locked"></el-description-item>
              <el-description-item label="Create Time"
                                   :span='15'
                                   :value="userinfo.createTime"></el-description-item>
            </el-description>

          </el-card>
        </el-tab-pane>
        <el-tab-pane label="Password Change"
                     name="modifyPassword">
          <el-card>
            <ul>
              <li>
                <p class="desc">
                  Change Password:
                  <a href="#"
                     @click="showPassword=true">Change Password</a>
                </p>
              </li>
            </ul>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <el-dialog :visible.sync="showPassword"
               @close="clearPassword"
               :showClose="false"
               title="Change My Password"
               width="360px"
               :before-close="handleClose">
      <el-form :model="pwdModify"
               :rules="rules"
               label-width="80px"
               ref="modifyPwdForm">
        <el-form-item :minlength="6"
                      label="Old Password"
                      prop="password">
          <el-input show-password
                    v-model="pwdModify.password"></el-input>
        </el-form-item>
        <el-form-item :minlength="6"
                      label="New Password"
                      prop="newPassword">
          <el-input show-password
                    v-model="pwdModify.newPassword"></el-input>
        </el-form-item>
        <el-form-item :minlength="6"
                      label="Confirm Password"
                      prop="confirmPassword">
          <el-input show-password
                    v-model="pwdModify.confirmPassword"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer"
           slot="footer">
        <el-button @click="showPassword=false">Cancel</el-button>
        <el-button @click="savePassword"
                   type="primary">OK</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import qs from "qs";
import ElDescription from '@/components/description/Description'
import ElDescriptionItem from '@/components/description/DescriptionItem'

export default {
  name: "Person",
  components: { ElDescription, ElDescriptionItem },
  data () {
    return {
      userinfo: {
        id: 0,
        username: "admin",
        realName: "Administrator",
        email: "admin@126.com",
        address: "",
        locked: false,
        createTime: "2021-07-19 20:26:06",
        updateTime: "2021-07-19 20:26:06"
      },
      activeName: "userinfo",
      showPassword: false,
      pwdModify: {},
      rules: {
        password: [
          { required: true, message: "Please enter password", trigger: "blur" },
          { min: 6, message: "Minimum 6 characters", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "Please enter new password", trigger: "blur" },
          { min: 6, message: "Minimum 6 characters", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "Please enter confirm password", trigger: "blur" },
          { min: 6, message: "Minimum 6 characters", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              if (value !== this.pwdModify.newPassword) {
                callback(new Error("Passwords do not match"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ]
      }
    };
  },
  created () {
    this.loadData();
    console.log(this.userinfo);
  },
  methods: {
    loadData: function () {
      this.$http
        .get(
          "/sqlrest/manager/api/v1/user/detail/name?username=" +
          window.sessionStorage.getItem("username")
        )
        .then(
          res => {
            if (0 === res.data.code) {
              this.userinfo = res.data.data;
            } else {
              alert("Failed to load data: " + res.data.message);
            }
          },
          error => {
            this.$message({
              showClose: true,
              message: "Data load error",
              type: "error"
            });
          }
        );
    },
    handleClose () { },
    savePassword () {
      this.$http({
        method: 'POST',
        url: '/sqlrest/manager/api/v1/user/changePassword',
        data: qs.stringify({
          oldPassword: this.pwdModify.password,
          newPassword: this.pwdModify.newPassword
        }),
      }).then(res => {
        console.log(res);
        if (0 === res.data.code) {
          this.showPassword = false;
          this.$message.success("Password changed successfully!");
        } else {
          this.showPassword = true;
          this.$message(res.data.message);
        }
      });
    },
    clearPassword () {
      this.pwdModify = {
        password: "",
        newPassword: "",
        confirmPassword: ""
      };
      this.$refs.modifyPwdForm.clearValidate();
    },
    handleClick () {

    }
  }
};
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 95%;
}

.my-label {
  background: #e1f3d8;
}

.my-content {
  background: #fde2e2;
}
</style>
