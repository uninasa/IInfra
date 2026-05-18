# iinfra

可复用业务基础设施框架，基于 Spring Boot + MyBatis Plus，支持单体/微服务灵活切换的模块化架构。

`dependence` 和 `module` 层设计为可发布到 Maven 私服、跨项目复用的基础能力；`business` 层承载具体业务实现，不对外发布。

## 技术栈

- **Java 8** + **Spring Boot 2.7.18**
- **Spring Cloud 2021.0.8** + **Spring Cloud Alibaba 2021.0.5.0**（微服务模式）
- **MyBatis Plus 3.5.3**
- **MySQL 8.0**
- **Redis**（缓存 / Token 存储）
- **JWT / jjwt 0.11.5**（认证鉴权）
- **Knife4j 4.3.0**（API 文档）
- **Druid 1.2.20**（数据库连接池）

## 项目结构

```
iinfra (根项目)
├── iinfra-dependence           ← 公共依赖层（可发布私服，跨项目复用）
│   ├── iinfra-common           -- 通用工具：DateUtil、JsonUtil 等纯工具类
│   ├── iinfra-protocol         -- 协议封装：MQTT、WebSocket、HTTP 客户端
│   └── iinfra-base-core        -- 基础核心：统一返回、全局异常、MP 配置、CORS
│
├── iinfra-module               ← 系统模块层（系统级能力，成熟后可沉淀到 dependence）
│   ├── iinfra-system           -- 系统管理：用户、角色、权限
│   └── iinfra-auth             -- 鉴权认证：JWT 生成/校验、登录、权限拦截
│
├── iinfra-business             ← 业务模块层（项目特有业务，不对外发布）
│   ├── iinfra-script           -- 剧本创作：剧本、角色、场景管理
│   └── iinfra-admin            -- 管理后台：系统监控、日志、统计
│
└── iinfra-starter              ← 启动器层（组装层，负责拼装并启动）
    └── iinfra-main             -- 单体启动入口
    <!-- 微服务模式（按需启用）
    ├── iinfra-gateway           -- 网关服务
    ├── iinfra-system-service    -- 系统服务
    ├── iinfra-script-service    -- 剧本服务
    └── iinfra-admin-service     -- 管理服务
    -->
```

## 分层依赖关系

```
starter（组装启动）
  ↓
business（业务实现）
  ↓
module（系统能力）
  ↓
dependence（基础设施）
  ↓
Spring Boot / Spring Cloud
```

各层只能向下依赖，不允许反向依赖。

## 模块归属原则

| 放 dependence | 放 module | 放 business |
|---|---|---|
| 统一返回、全局异常 | 用户/角色/权限管理 | 具体业务功能 |
| 工具类（DateUtil 等） | JWT 鉴权认证 | 业务枚举 |
| MP 配置、CORS 配置 | 短信、文件上传（待扩展） | 永远不发布到 Maven |
| 多租户数据隔离 | 第三方服务集成 | |
| 通用枚举（性别等） | | |

判断标准：**"把这个模块复制到另一个完全不同的项目，能直接用吗？"**
- 能 → dependence
- 有业务上下文但可复用 → module
- 项目特有 → business

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 初始化数据库

```bash
mysql -u root -p < doc/sql/init.sql
```

### 修改配置

编辑 `iinfra-starter/iinfra-main/src/main/resources/application.yml`，配置数据库和 Redis 连接信息。

### 启动（单体模式）

```bash
mvn spring-boot:run -pl iinfra-starter/iinfra-main
```

访问：http://localhost:8080
API 文档：http://localhost:8080/doc.html

## 单体 / 微服务切换

默认以单体模式运行。切换微服务模式：

1. 取消根 `pom.xml` 中 Nacos、OpenFeign 依赖的注释
2. 启用 `iinfra-starter` 中的微服务模块
3. 各启动类添加 `@EnableDiscoveryClient` 和 `@EnableFeignClients`
4. 配置 Nacos 注册中心和网关路由

## 文档

- [设计决策记录](doc/design-decisions.md) — 架构与设计取舍的完整记录
- [数据库初始化](doc/sql/init.sql)
