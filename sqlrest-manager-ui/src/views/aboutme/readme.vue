<template>
  <div>
    <h1 class="page-title">ABOUT</h1>
    <h3 class="about-subtitle">About SQLREST Tool</h3>
    <div class="tabs-container">
      <el-tabs v-model="activeName"
               @tab-click="handleClick()"
               class="centered-tabs">
      <el-tab-pane label="System Features"
                   name="first">
        <div class="content_tag">
          <el-tag type="danger" size="medium">SQL API</el-tag>
          <el-tag type="success" size="medium">Script API</el-tag>
        </div>
        <div class="content_card">
          <el-card class="box-card">
            <div class="text item">
              <p class="intro-text">
                In short, sqlrest tool provides a rapid RESTful <strong>API interface</strong> building tool, including SQL and script approaches. Features include:
              </p>
              <ul class="feature-list">
                <li class="feature-item">
                  <strong>Project Hosting</strong>
                  <p class="feature-desc">
                    Gitee Address: <a href="https://gitee.com/inrgihc/sqlrest" target="_blank">https://gitee.com/inrgihc/sqlrest</a>
                  </p>
                </li>
                <li class="feature-item">
                  <strong>Build RESTful APIs using SQL approach</strong>
                  <p class="feature-desc">
                    Provides MyBatis-like dynamic SQL syntax to build APIs.
                  </p>
                </li>
                <li class="feature-item">
                  <strong>Build RESTful APIs using script approach</strong>
                  <p class="feature-desc">
                    Build complex scenario APIs using Groovy script syntax.
                  </p>
                </li>
                <li class="feature-item">
                  <strong>Support token-based security authentication</strong>
                  <p class="feature-desc">
                    Executor supports token generation and token authentication.
                  </p>
                </li>
                <li class="feature-item">
                  <strong>Support online API documentation generation</strong>
                  <p class="feature-desc">
                    Provides online API documentation generation based on Swagger UI.
                  </p>
                </li>
                <li class="feature-item">
                  <strong>Support API flow control</strong>
                  <p class="feature-desc">
                    Executor supports API flow control based on Sentinel.
                  </p>
                </li>
                <li class="feature-item">
                  <strong>Support API cache configuration</strong>
                  <p class="feature-desc">
                    Executor supports cache configuration using hash and SpEL expressions.
                  </p>
                </li>
                <li class="feature-item">
                  <strong>Support API version control management</strong>
                  <p class="feature-desc">
                    Developed APIs support version control management and online version switching.
                  </p>
                </li>
                <li class="feature-item">
                  <strong>Support converting APIs to MCP tools</strong>
                  <p class="feature-desc">
                    Manager supports serving as MCP server to provide Tool functionality.
                  </p>
                </li>
              </ul>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Relational Databases"
                   name="second">
        <div class="content_tag">
          <el-tag type="success" size="medium">JDBC</el-tag>
          <el-tag type="danger" size="medium">sqlrest</el-tag>
        </div>
        <div class="content_card">
          <el-card class="box-card">
            <div class="text item">
              <p class="intro-text">
                sqlrest supports 20+ common relational databases, including:
              </p>
              <ul class="database-list">
                <li>Oracle</li>
                <li>MySQL</li>
                <li>MariaDB</li>
                <li>Microsoft SQL Server</li>
                <li>PostgreSQL/Greenplum</li>
                <li>IBM DB2</li>
                <li>Sybase</li>
                <li>DMDB (Domestic)</li>
                <li>Kingbase8 (Domestic)</li>
                <li>HighGo (Domestic)</li>
                <li>Oscar (Domestic)</li>
                <li>GBase8a (Domestic)</li>
                <li>Apache Hive</li>
                <li>Cloudera Impala</li>
                <li>Apache Doris</li>
                <li>SQLite3</li>
                <li>OpenGauss</li>
                <li>ClickHouse</li>
                <li>StarRocks</li>
                <li>OceanBase</li>
                <li>TDengine</li>
              </ul>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Development Technology Stack"
                   name="third">
        <div class="content_tag">
          <el-tag type="success" size="medium">SpringBoot</el-tag>
          <el-tag type="danger" size="medium">SpringCloud</el-tag>
          <el-tag type="success" size="medium">MyBatis</el-tag>
          <el-tag type="danger" size="medium">Vue/ElementUI</el-tag>
        </div>
        <div class="content_card">
          <el-card class="box-card">
            <div class="text item">
              <p class="intro-text">
                sqlrest is based on SpringBoot scaffold for backend module development. Module structure is as follows:
              </p>
              <pre class="code-block">
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
    └── sqlrest-dist                       // maven-assembly-plugin based project packaging module
              </pre>
              <ul class="tech-list">
                <li class="tech-item">
                  <strong>SpringBoot</strong>
                  <p class="tech-desc">
                    sqlrest uses SpringBoot as the project foundation scaffold.
                  </p>
                  <p class="tech-desc">
                    sqlrest-gateway/sqlrest-executor/sqlrest-manager provide API configuration and access functionality based on SpringCloud microservices architecture.
                  </p>
                </li>
                <li class="tech-item">
                  <strong>MyBatis</strong>
                  <p class="tech-desc">
                    MyBatis is an excellent persistence framework that supports customized SQL, stored procedures, and advanced mappings.
                  </p>
                  <p class="tech-desc">
                    sqlrest-persistence module provides configuration data persistence based on MyBatis/MyBatis-Plus.
                  </p>
                </li>
                <li class="tech-item">
                  <strong>Vue/ElementUI</strong>
                  <p class="tech-desc">
                    Vue is a progressive JavaScript framework for building user interfaces. Element is a Vue 2.0-based desktop component library open-sourced by Ele.me team based on MVVM framework Vue.
                  </p>
                  <p class="tech-desc">
                    sqlrest-manager-ui module provides visual WEB interface based on Vue and ElementUI.
                  </p>
                </li>
              </ul>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
      </el-tabs>
    </div>
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
.about-subtitle {
  text-align: center;
  font-size: 18px;
  font-weight: 500;
  color: #606266;
  margin: 20px 0;
}

.tabs-container {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.centered-tabs {
  width: 100%;
  max-width: 1200px;
}

.centered-tabs >>> .el-tabs__header {
  display: flex;
  justify-content: center;
}

.centered-tabs >>> .el-tabs__nav-wrap {
  display: flex;
  justify-content: center;
}

.content_tag {
  text-align: center;
  margin: 20px 0;
}

.content_tag .el-tag {
  margin: 0 5px;
}

.content_card {
  padding-top: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.box-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.intro-text {
  font-size: 15px;
  line-height: 1.8;
  color: #303133;
  margin-bottom: 20px;
}

.feature-list,
.tech-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.feature-item,
.tech-item {
  margin-bottom: 25px;
  padding-left: 20px;
  position: relative;
}

.feature-item::before,
.tech-item::before {
  content: "•";
  position: absolute;
  left: 0;
  color: #b142af;
  font-size: 20px;
  font-weight: bold;
}

.feature-item strong,
.tech-item strong {
  font-size: 15px;
  color: #303133;
  display: block;
  margin-bottom: 8px;
}

.feature-desc,
.tech-desc {
  font-size: 14px;
  line-height: 1.7;
  color: #606266;
  margin: 5px 0;
}

.feature-desc a,
.tech-desc a {
  color: #409eff;
  text-decoration: none;
}

.feature-desc a:hover,
.tech-desc a:hover {
  text-decoration: underline;
}

.database-list {
  list-style: none;
  padding: 0;
  margin: 20px 0;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 10px;
}

.database-list li {
  padding: 8px 12px;
  background-color: #f5f7fa;
  border-radius: 4px;
  font-size: 14px;
  color: #606266;
  text-align: center;
}

.code-block {
  background-color: #f5f7fa;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 15px;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 13px;
  line-height: 1.6;
  color: #303133;
  overflow-x: auto;
  margin: 15px 0;
}
</style>
