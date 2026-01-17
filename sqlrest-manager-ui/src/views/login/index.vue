```vue
<template>
  <div class="box body-bg">
    <div class="content">
      <div class="item header">
        SQLREST
      </div>
      <div class="item">
        <el-input v-model="loginForm.username" placeholder="Please enter username"></el-input>
      </div>
      <div class="item">
        <el-input v-model="loginForm.password" placeholder="Please enter password" type="password"></el-input>
      </div>
      <div class="item">
        <div class="button" @click="handleLogin" :class="{ 'disabled': loading }">
          <span v-if="!loading">Login</span>
          <span v-if="loading" class="loading-text">Logging in...</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      errors: {},
      loading: false
    }
  },
  computed: {
    currentYear() {
      return new Date().getFullYear();
    }
  },
  methods: {
    validateForm () {
      if (!this.loginForm.username || !this.loginForm.username.trim()) {
        this.$message.error('Please enter username');
        return false;
      }

      if (!this.loginForm.password) {
        this.$message.error('Please enter password');
        return false;
      }

      return true;
    },
    resetLoading () {
      this.loading = false;
    },
    handleLogin () {
      if (this.loading) {
        return;
      }
      if (!this.validateForm()) {
        return;
      }
      this.loading = true;
      this.$http({
        method: 'POST',
        url: '/user/login',
        data: {
          username: this.loginForm.username,
          password: this.loginForm.password
        },
      }).then(res => {
        if (0 === res.data.code) {
          window.sessionStorage.setItem('token', res.data.data.accessToken);
          window.sessionStorage.setItem('username', this.loginForm.username);
          window.sessionStorage.setItem('realname', res.data.data.realName);
          this.$http.get("/sqlrest/manager/api/v1/health/version").then(
            res => {
              if (0 === res.data.code) {
                window.sessionStorage.setItem('version', res.data.data);
                this.$message("Login successful!");
                this.$router.push({ path: '/dashboard' });
              } else {
                this.$message(res.data.message);
              }
              this.resetLoading();
            }
          );
        } else {
          this.$message.error("Login failed: " + res.data.message);
          this.resetLoading();
        }
      }).catch(error => {
        this.resetLoading();
        this.$message.error("Login failed: " + (error || "Unable to connect"));
      })
    }
  },
  created() {
    //bind login event to enter key
    document.onkeydown = (e) => {
      const key = window.event.keyCode;
      if (key == 13) {
        this.handleLogin();
      }
    };
  },
  destroyed() {
    //unbind login event to enter key when go to other pages
    document.onkeydown = null;
  }
}
</script>

<style scoped>
.box {
  display: flex;
  display: -webkit-flex;
  align-items: center;
  justify-content: center;
}

.content {
  padding-bottom: 100px;
}

.item {
  width: 400px;
  height: 70px;
}

.button {
  width: 400px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  background-color: #273446;
  border-radius: 10px;
  color: #bfcbd9;
  font-weight: 700;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.button:hover:not(.disabled) {
  background-color: #0b0f14;
  font-size: 21px;
}

.button.disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.header {
  text-align: center;
  color: #bfcbd9;
  font-weight: 700;
  font-size: 40px;
}

.body-bg {
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
  overflow-y: auto;
  background-image: radial-gradient( #486180, #222d3b);
  color: #bfcbd9;
}

.loading-text {
  display: inline-block;
  animation: pulse 1.5s ease-in-out infinite;
}

@keyframes pulse {
  0%,
  100% {
    opacity: 1;
  }
  50% {
    opacity: 0.7;
  }
}
</style>