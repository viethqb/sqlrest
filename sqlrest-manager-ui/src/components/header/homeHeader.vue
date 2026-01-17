<template>
  <div class="head">
    <div style="padding: 5px 10px">
      <img src="@/assets/LOGO.png" alt="SQLREST" class="logo2"/>
    </div>
    <span class="version">{{ version }}</span>
    <div class="menus">
      <div class="menu" :class="{'activeMenu': isDashboardActive}" @click="clickMenu('/dashboard')">
        Dashboard
      </div>
      <div class="menu" 
           :class="{'activeMenu': isDatasourceActive || isDatasourceDriverActive || isDatasourceListActive}">
        DataSource
        <i class="el-icon-arrow-down menu-arrow"></i>
        <div class="submenus">
          <div class="submenu" :class="{'activeMenu': isDatasourceDriverActive}" @click.stop="clickMenu('/datasource/driver')">Driver Config</div>
          <div class="submenu" :class="{'activeMenu': isDatasourceListActive}" @click.stop="clickMenu('/datasource/list')">Connection Management</div>
        </div>
      </div>
      <div class="menu" 
           :class="{'activeMenu': isInterfaceActive || isServiceActive || isInterfaceModuleActive || isInterfaceListActive || isServiceSearchActive}">
        API
        <i class="el-icon-arrow-down menu-arrow"></i>
        <div class="submenus">
          <div class="submenu" :class="{'activeMenu': isInterfaceModuleActive}" @click.stop="clickMenu('/interface/module')">Module Config</div>
          <div class="submenu" :class="{'activeMenu': isInterfaceListActive}" @click.stop="clickMenu('/interface/list')">API Config</div>
          <div class="submenu" :class="{'activeMenu': isServiceSearchActive}" @click.stop="clickMenu('/service/search')">API Online</div>
        </div>
      </div>
      <div class="menu" 
           :class="{'activeMenu': isSettingActive || isSettingGroupActive || isSettingClientActive || isSettingFirewallActive || isSettingAlarmActive || isSettingTopologyActive}">
        Setting
        <i class="el-icon-arrow-down menu-arrow"></i>
        <div class="submenus">
          <div class="submenu" :class="{'activeMenu': isSettingGroupActive}" @click.stop="clickMenu('/setting/group')">Authorization Group</div>
          <div class="submenu" :class="{'activeMenu': isSettingClientActive}" @click.stop="clickMenu('/setting/client')">Client Application</div>
          <div class="submenu" :class="{'activeMenu': isSettingFirewallActive}" @click.stop="clickMenu('/setting/firewall')">Firewall IP</div>
          <div class="submenu" :class="{'activeMenu': isSettingAlarmActive}" @click.stop="clickMenu('/setting/alarm')">Alarm Config</div>
          <div class="submenu" :class="{'activeMenu': isSettingTopologyActive}" @click.stop="clickMenu('/setting/topology')">Topology</div>
        </div>
      </div>
      <div class="menu" 
           :class="{'activeMenu': isMcpActive || isMcpClientActive || isMcpToolActive}">
        MCP
        <i class="el-icon-arrow-down menu-arrow"></i>
        <div class="submenus">
          <div class="submenu" :class="{'activeMenu': isMcpClientActive}" @click.stop="clickMenu('/mcp/client')">Token Config</div>
          <div class="submenu" :class="{'activeMenu': isMcpToolActive}" @click.stop="clickMenu('/mcp/tool')">Tool Config</div>
        </div>
      </div>
      <div class="menu" :class="{'activeMenu': isAboutActive}" @click="clickMenu('/aboutme')">
        About
      </div>
    </div>
    <div class="right">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link" style="color: #606266">
          <i class="el-icon-user"></i>{{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="profile">
            <i class="el-icon-s-custom"></i>Personal Information
          </el-dropdown-item>
          <el-dropdown-item command="logout" divided>
            <i class="el-icon-switch-button"></i>Logout
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "homeHeader",
  data() {
    return {
      version: "1.0.0",
      username: "",
      nickname: ""
    };
  },
  computed: {
    currentPath() {
      return this.$route.path;
    },
    isDashboardActive() {
      return this.currentPath === '/dashboard';
    },
    isDatasourceActive() {
      return this.currentPath.startsWith('/datasource');
    },
    isDatasourceDriverActive() {
      return this.currentPath === '/datasource/driver' || this.currentPath === '/datasource';
    },
    isDatasourceListActive() {
      return this.currentPath === '/datasource/list';
    },
    isInterfaceActive() {
      return this.currentPath.startsWith('/interface');
    },
    isApiActive() {
      return this.currentPath.startsWith('/interface') || this.currentPath.startsWith('/service');
    },
    isInterfaceModuleActive() {
      return this.currentPath === '/interface/module';
    },
    isInterfaceListActive() {
      return this.currentPath === '/interface/list' || 
             this.currentPath.startsWith('/interface/create') || 
             this.currentPath.startsWith('/interface/update') || 
             this.currentPath.startsWith('/interface/detail');
    },
    isServiceActive() {
      return this.currentPath.startsWith('/service');
    },
    isServiceSearchActive() {
      return this.currentPath === '/service/search' || 
             this.currentPath === '/service' || 
             this.currentPath.startsWith('/service/detail');
    },
    isSettingActive() {
      return this.currentPath.startsWith('/setting');
    },
    isSettingGroupActive() {
      return this.currentPath === '/setting/group' || this.currentPath === '/setting';
    },
    isSettingClientActive() {
      return this.currentPath === '/setting/client';
    },
    isSettingFirewallActive() {
      return this.currentPath === '/setting/firewall';
    },
    isSettingAlarmActive() {
      return this.currentPath === '/setting/alarm';
    },
    isSettingTopologyActive() {
      return this.currentPath === '/setting/topology';
    },
    isMcpActive() {
      return this.currentPath.startsWith('/mcp');
    },
    isMcpClientActive() {
      return this.currentPath === '/mcp/client' || this.currentPath === '/mcp';
    },
    isMcpToolActive() {
      return this.currentPath === '/mcp/tool';
    },
    isAboutActive() {
      return this.currentPath === '/aboutme';
    }
  },
  created() {
    this.username = window.sessionStorage.getItem("username") || "";
    this.nickname = window.sessionStorage.getItem("realname") || "";
    this.version = window.sessionStorage.getItem("version") || "1.0.0";
  },
  methods: {
    handleCommand(command) {
      if (command == 'logout') {
        window.sessionStorage.clear();
        this.$http({
          method: 'GET',
          url: '/user/logout'
        }).catch(() => {});
        this.$router.push("/login");
      } else if (command == 'profile') {
        this.$router.push("/user/self");
      }
    },
    clickMenu(path) {
      this.$router.push(path);
    }
  }
};
</script>

<style scoped>
.head {
  display: flex;
  background-color: #fff;
  color: #606266;
  width: 100%;
  line-height: 60px;
  border-bottom: 1px solid #ebeef5;
}

.head .logo2 {
  flex-shrink: 0;
  display: block;
  height: 50px;
}

.head .version {
  padding: 30px 20px 0px 0px;
  font-size: 14px;
  line-height: 20px;
}

.head .menus {
  flex-shrink: 0;
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.head .menus .activeMenu {
  color: #b142af;
  font-size: 16px;
  font-weight: 600;
  border-bottom: 2px solid #b142af;
  padding-bottom: 0;
}

.head .menus .menu {
  margin: 0 5px;
  padding: 0 10px;
  font-size: 15px;
  font-weight: 500;
  color: #606266;
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
  border-bottom: 2px solid transparent;
}

.head .menus .menu:hover {
  color: #b142af;
}

/* Keep active state even when not hovering */
.head .menus .menu.activeMenu {
  color: #b142af;
  border-bottom: 2px solid #b142af;
}

.head .menus .menu .menu-arrow {
  font-size: 12px;
  margin-left: 5px;
  transition: transform 0.3s ease;
}

.head .menus .menu:hover .menu-arrow {
  transform: rotate(180deg);
}

.head .menus .menu .submenus {
  padding: 5px 0;
  display: none;
  z-index: 1000;
  position: absolute;
  top: 60px;
  left: 0px;
  background-color: #fff;
  width: 200px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border: 1px solid #ebeef5;
}

.head .menus .menu .submenus .submenu {
  font-size: 14px;
  line-height: 40px;
  padding: 0 15px;
  font-weight: 400;
  color: #606266;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  cursor: pointer;
  transition: all 0.3s ease;
}

.head .menus .menu .submenus .submenu:hover {
  background-color: #f5f7fa;
  color: #b142af;
}

.head .menus .menu .submenus .submenu.activeMenu {
  color: #b142af;
  background-color: #f5f7fa;
  font-weight: 500;
  border-left: 3px solid #b142af;
  padding-left: 12px;
}

.head .menus .menu:hover {
  background-color: transparent;
}

.head .menus .menu:hover .submenus {
  display: block;
}


.head .right {
  margin: 0 20px;
  flex-shrink: 0;
  display: flex;
}

.head .version {
  padding: 30px 20px 0px 0px;
  font-size: 14px;
  line-height: 20px;
}
</style>
