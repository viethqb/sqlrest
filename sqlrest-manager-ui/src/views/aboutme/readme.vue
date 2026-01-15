<template>
  <div>
    <h3>About SQLREST Tool</h3>
    <el-tabs v-model="activeName"
             @tab-click="handleClick()">
      <el-tab-pane label="System Features"
                   name="first">
        <div class="content_tag">
          <el-tag type="danger">SQL API</el-tag>
          <el-tag type="success">Script API</el-tag>
        </div>
        <div class="content_card">
          <el-card class="box-card">
            <div class="text item">
              <p>
                In short, sqlrest tool provides a rapid RESTful <B>API interface</B> building tool, including SQL and script approaches. Features include:
              </p>
              <ul>
                <li>Project Hosting</li>
                <p>
                  Gitee Address: <a href="https://gitee.com/inrgihc/sqlrest">https://gitee.com/inrgihc/sqlrest</a>
                </p>
                <li>Build RESTful APIs using SQL approach</li>
                <p>
                  Provides MyBatis-like dynamic SQL syntax to build APIs.
                </p>
                <li>Build RESTful APIs using script approach</li>
                <p>
                  Build complex scenario APIs using Groovy script syntax.
                </p>
                <li>Support token-based security authentication</li>
                <p>
                  Executor supports token generation and token authentication.
                </p>
                <li>Support online API documentation generation</li>
                <p>
                  Provides online API documentation generation based on Swagger UI.
                </p>
                <li>Support API flow control</li>
                <p>
                  Executor supports API flow control based on Sentinel.
                </p>
                <li>Support API cache configuration</li>
                <p>
                  Executor supports cache configuration using hash and SpEL expressions.
                </p>
                <li>Support API version control management</li>
                <p>
                  Developed APIs support version control management and online version switching.
                </p>
                <li>Support converting APIs to MCP tools</li>
                <p>
                  Manager supports serving as MCP server to provide Tool functionality.
                </p>
              </ul>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Relational Databases"
                   name="second">
        <div class="content_tag">
          <el-tag type="success">JDBC</el-tag>
          <el-tag type="danger">sqlrest</el-tag>
        </div>
        <div class="content_card">
          <el-card class="box-card">
            <div class="text item">
              <p>
                sqlrest supports 20+ common relational databases, including:
              </p>
              <ul>
                <li>Oracle
                </li>
                <li>MySQL
                </li>
                <li>MariaDB
                </li>
                <li>Microsoft SQL Server
                </li>
                <li>PostgreSQL/Greenplum
                </li>
                <li>IBM DB2
                </li>
                <li>Sybase
                </li>
                <li>DMDB (Domestic)
                </li>
                <li>Kingbase8 (Domestic)
                </li>
                <li>HighGo (Domestic)
                </li>
                <li>Oscar (Domestic)
                </li>
                <li>GBase8a (Domestic)
                </li>
                <li>Apache Hive
                </li>
                <li>Cloudera Impala
                </li>
                <li>Apache Doris
                </li>
                <li>SQLite3
                </li>
                <li>OpenGauss
                </li>
                <li>ClickHouse
                </li>
                <li>StarRocks
                </li>
                <li>OceanBase
                </li>
                <li>TDengine
                </li>
              </ul>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Development Technology Stack"
                   name="third">
        <div class="content_tag">
          <el-tag type="success">SpringBoot</el-tag>
          <el-tag type="danger">SpringCloud</el-tag>
          <el-tag type="success">MyBatis</el-tag>
          <el-tag type="danger">Vue/ElementUI</el-tag>
        </div>
        <div class="content_card">
          <el-card class="box-card">
            <div class="text item">
              <p>
                sqlrest is based on SpringBoot scaffold for backend module development. Module structure is as follows:
              <pre>
            └── sqlrest
                ├── sqlrest-common                     // sqlrest common definition module
                ├── sqlrest-mcp                        // sqlrest MCP protocol module
                ├── sqlrest-template                   // sqlrest SQL content template module
                ├── sqlrest-persistence                // sqlrest database persistence module
                ├── sqlrest-core                       // sqlrest-core interface implementation module
                ├── sqlrest-gateway                    // Gateway gateway node
                ├── sqlrest-executor                   // Executor API execution node
                ├── sqlrest-manager                    // Manager management node
                ├── sqlrest-manager-ui                 // Vue2-based Manager frontend WEB interface
                ├── sqlrest-dist                       // maven-assembly-plugin based project packaging module
                </pre>
              </p>
              <ul>
                <li>SpringBoot</li>
                <p>
                  sqlrest uses SpringBoot as the project foundation scaffold.
                </p>
                <p>
                  sqlrest-gateway/sqlrest-executor/sqlrest-manager provide API configuration and access functionality based on SpringCloud microservices architecture.
                </p>
                <li>MyBatis</li>
                <p>
                  MyBatis is an excellent persistence framework that supports customized SQL, stored procedures, and advanced mappings.
                <p>
                  sqlrest-persistence module provides configuration data persistence based on MyBatis/MyBatis-Plus.
                </p>
                <li>Vue/ElementUI</li>
                <p>
                  Vue is a progressive JavaScript framework for building user interfaces. Element is a Vue 2.0-based desktop component library open-sourced by Ele.me team based on MVVM framework Vue.
                </p>
                <p>
                  sqlrest-manager-ui module provides visual WEB interface based on Vue and ElementUI.
                </p>
              </ul>
            </div>
          </el-card>
        </div>
      </el-tab-pane>

    </el-tabs>
  </div>
</template>

<script>
import layout from '../layout.vue';
export default {
  components: { layout },
  data () {
    return {
      activeName: 'first'
    };
  },
  methods: {
    handleClick (tab, event) {
      //console.log(tab, event);
    }
  }
}
</script>

<style scoped>
div h3 {
  text-align: center;
}

.content_tag {
  text-align: center;
}

.content_card {
  padding-top: 20px;
}
</style>
