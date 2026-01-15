import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

///////////////////////////////////////////////////////////////////////////
// Router configuration
// Reference tutorial: https://blog.csdn.net/weixin_38404899/article/details/90229805
//
///////////////////////////////////////////////////////////////////////////
const constantRouter = new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/layout'),
      redirect: '/dashboard',
      children: [
        {
          path: '/dashboard',
          name: 'Overview',
          icon: "el-icon-menu",
          component: () => import('@/views/dashboard/index')
        },
        {
          path: '/datasource',
          name: 'Connection Config',
          icon: "el-icon-coin",
          component: () => import('@/views/datasource/index'),
          children: [
            {
              path: '/datasource/driver',
              name: 'Driver Config',
              icon: "el-icon-help",
              component: () => import('@/views/datasource/driver'),
            },
            {
              path: '/datasource/list',
              name: 'Connection Management',
              icon: "el-icon-bank-card",
              component: () => import('@/views/datasource/list')
            }
          ]
        },
        {
          path: '/setting',
          name: 'System Settings',
          icon: "el-icon-s-tools",
          component: () => import('@/views/setting/index'),
          children: [
            {
              path: '/setting/group',
              name: 'Authorization Group',
              icon: "el-icon-tickets",
              component: () => import('@/views/setting/group'),
            },
            {
              path: '/setting/client',
              name: 'Client Application',
              icon: "el-icon-pie-chart",
              component: () => import('@/views/setting/client')
            },
            {
              path: '/setting/firewall',
              name: 'Access Control',
              icon: "el-icon-notebook-2",
              component: () => import('@/views/setting/firewall')
            },
            {
              path: '/setting/alarm',
              name: 'Alarm Config',
              icon: "el-icon-message-solid",
              component: () => import('@/views/setting/alarm')
            },
            {
              path: '/setting/topology',
              name: 'Topology',
              icon: "el-icon-link",
              component: () => import('@/views/setting/topology')
            }
          ]
        },
        {
          path: '/interface',
          name: 'API Development',
          icon: "el-icon-edit-outline",
          component: () => import('@/views/interface/index'),
          children: [
            {
              path: '/interface/module',
              name: 'Module Config',
              icon: "el-icon-folder",
              component: () => import('@/views/interface/module'),
            },
            {
              path: '/interface/list',
              name: 'API Config',
              icon: "el-icon-refrigerator",
              component: () => import('@/views/interface/list'),
            }
          ]
        },
        {
          path: '/service',
          name: 'API Repository',
          icon: "el-icon-school",
          component: () => import('@/views/service/index'),
          children: [
            {
              path: '/service/search',
              name: 'Online APIs',
              icon: "el-icon-lightning",
              component: () => import('@/views/service/search'),
            }
          ]
        },
        {
          path: '/mcp',
          name: 'MCP Service',
          icon: "el-icon-s-promotion",
          component: () => import('@/views/mcp/index'),
          children: [
            {
              path: '/mcp/client',
              name: 'Token Config',
              icon: "el-icon-s-platform",
              component: () => import('@/views/mcp/client'),
            },
            {
              path: '/mcp/tool',
              name: 'Tool Config',
              icon: "el-icon-setting",
              component: () => import('@/views/mcp/tool'),
            }
          ]
        },
        {
          path: '/aboutme',
          name: 'About System',
          icon: "el-icon-s-custom",
          component: () => import('@/views/aboutme/readme')
        },
        {
          path: '/user/self',
          name: 'Personal Center',
          hidden: true,
          component: () => import('@/views/user/self')
        },
        {
          path: '/interface/create',
          name: 'Create API',
          hidden: true,
          component: () => import('@/views/interface/create')
        },
        {
          path: '/interface/update',
          name: 'Update API',
          hidden: true,
          component: () => import('@/views/interface/update')
        },
        {
          path: '/interface/detail',
          name: 'View API',
          hidden: true,
          component: () => import('@/views/interface/detail')
        },
        {
          path: '/service/detail',
          name: 'API Details',
          hidden: true,
          component: () => import('@/views/service/detail')
        }
      ],
    },

    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/login')
    }
  ]
});

export default constantRouter;
