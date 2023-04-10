# RuoYi框架学习

# 00.前言

基于SpringBoot开发的轻量级Java快速开发框架

> 若依是给女儿取的名字（寓意：你若不离不弃，我必生死相依）

演示地址：[http://ruoyi.vip](https://gitee.com/link?target=http%3A%2F%2Fruoyi.vip)
文档地址：[http://doc.ruoyi.vip](https://gitee.com/link?target=http%3A%2F%2Fdoc.ruoyi.vip)

## 0️⃣前后端技术栈

后端

* SpringBoot框架
* Shiro安全控制

前端

* Thymeleaf模板

## 1️⃣内置功能

1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2. 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3. 岗位管理：配置系统用户所属担任职务。
4. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7. 参数管理：对系统动态配置常用参数。
8. 通知公告：系统通知公告信息发布维护。
9. 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 缓存监控：对系统的缓存查询，删除、清空等操作。
17. 在线构建器：拖动表单元素生成相应的HTML代码。
18. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。





# 01.部署运行

## 0️⃣开发环境准备

### ①  版本控制

```text
JDK >= 1.8 (推荐1.8版本)
Mysql >= 5.7.0 (推荐5.7版本)
Maven >= 3.0
```



### ②  配置本地hosts

```Shell
# 配置hosts
vim /etc/hosts
127.0.0.1   ry-mysql
127.0.0.1   ry-redis
```

## 1️⃣ 代码部署

 ### **① 项目下载**

```shell
# 建议使用Git克隆，因为克隆的方式可以和RuoYi随时保持更新同步。使用Git命令克隆
git clone https://gitee.com/y_project/RuoYi.git
```



### **② 初始化数据库**

> **● **版本： mysql5.7.8+
> **● **默认字符集: utf8mb4
> **● **默认排序规则: utf8mb4_general_ci

创建数据库`ry`并导入数据脚本`ry_2021xxxx.sql`，`quartz.sql`

```shell
# 脚本文件说明
核心数据库: ruoyi/sql/ry_2021xxxx.sql
job管理: ruoyi/sql/quartz.sql
```



### **③ 配置文件修改**

 **数据库源信息修改**

```yml
ruoyi-admin/src/main/resources/application-druid.yml
# 数据源配置
spring:
    datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driverClassName: com.mysql.cj.jdbc.Driver
        druid:
            # 主库数据源
            master:
                url: 数据库地址
                username: 数据库账号
                password: 数据库密码
```

**服务器配置修改**

```yml
# 开发环境配置
server:
  # 服务器的HTTP端口，默认为80
  port: 端口
  servlet:
    # 应用的访问路径
    context-path: /应用路径
```



### **④ 打包工程文件**

在`ruoyi`项目的`bin`目录下执行`package.bat`打包Web工程，生成war/jar包文件。
然后会在项目下生成`target`文件夹包含`war`或`jar`

> 提示
>
> 多模块版本会生成在`ruoyi/ruoyi-admin`模块下`target`文件夹

- 部署工程文件

**1、jar部署方式**
使用命令行执行：`java –jar ruoyi.jar` 或者执行脚本：`ruoyi/bin/run.bat`

```shell
# 后台运行jar包
nohup java –jar ruoyi.jar >/dev/null 2>&1 &
```

**2、war部署方式**
`ruoyi/pom.xml`中的`packaging`修改为`war`，放入`tomcat`服务器`webapps`

```xml
<packaging>war</packaging>
```



### **⑤ 浏览器访问**

打开浏览器，输入：([http://localhost:80 (opens new window)](http://localhost/)) （默认账户/密码 `admin/admin123`）



### ⑥ 遇到的问题

> Gitee issues

* **login登录界面图片验证码不显示 **[issues](https://gitee.com/y_project/RuoYi/issues/I658NR)

```shell
yum -y install freetype fontconfig dejavu-sans-fonts
```



## 2️⃣ 系统账号说明

前端默认登录 http://ip:80

| 用户名 | 密码     |
| ------ | -------- |
| admin  | admin123 |



# 02.SpringBoot通用

## 多环境使用

创建不同的配置文件，且命名规则遵循**application-${profile}.properties**

- 开发环境配置文件：application-**dev**.properties
- 测试环境配置文件：application-**test**.properties
- 生产环境配置文件：application-**prod**.properties

在 Spring Boot 中，您可以使用 `SpringApplication.setDefaultProperties` 方法来设置默认属性

 `spring.profiles.active` 属性来设置当前激活的配置文件

```shell
# 运行开发环境
java -jar myapp.jar --spring.profiles.active=dev

# 运行生产环境
java -jar myapp.jar --spring.profiles.active=prod
```

`spring.profiles.include` 属性来包含其他配置文件

```shell
# 包含 "dev" 和 "database" 配置文件
java -jar myapp.jar --spring.profiles.active=dev,database
```

 `spring.config.name` 属性来指定配置文件的名称

```shell
# 使用 "myapp-prod.properties" 作为配置文件
java -jar myapp.jar --spring.config.name=myapp-prod
```

## 配置文件获取

使用 `@Value` 注解来获取配置文件中的值，例如，如果您有以下配置文件：

```properties
app.name=My App
app.version=1.0
```

您可以在您的类中使用 `@Value` 注解来获取这些值：

```java
@Value("${app.name}")
private String appName;

@Value("${app.version}")
private String appVersion;
```

您还可以使用 `@ConfigurationProperties` 注解来绑定属性到一个 Bean 中：

```java
@ConfigurationProperties(prefix = "app")
@Component
@Data
public class AppProperties {

    private String name;
    private String version;

}
```

然后，您可以在其他地方注入这个 Bean：

```java
@Autowired
private AppProperties appProperties;
```

您还可以使用 `Environment` 对象来访问配置文件中的值。例如：

```java
@Autowired
private Environment env;

public void printProperties() {
    System.out.println(env.getProperty("app.name"));
    System.out.println(env.getProperty("app.version"));
}
```

命令行参数和属性文件中加载配置，并支持自动配置。

## 自定义资源映射

> WebMvcConfigurer 实现 **资源解析器**

Spring Boot使用`ResourceHandlerRegistry`来注册资源映射。 可以使用`addResourceLocations`方法指定文件路径，然后使用`addResourceHandler`方法指定对应的URL路径。

例如，如果你想将`/static`目录中的文件映射到`/resources`URL路径，你可以这样写：

```java
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //resourceLocations可以指定本地路径 使用`file:`开头 
        registry.addResourceHandler("/resources/**").addResourceLocations("/static/");
    }
}
```

这样，当你访问`http://localhost:8080/resources/image.jpg`时，实际上会加载`/static/image.jpg`文件。

注意，如果你使用`Spring MVC`的`@RequestMapping`注解，请确保资源URL没有与控制器方法的映射冲突。

```java
// 效果相当于以下方法
@GetMapping("resources")
public String hello(){
    return "static";
}
```

你还可以使用`WebMvcConfigurer`接口实现许多其他功能，例如配置**视图解析器、拦截器、内容协商器**等。

## 实现拦截器

要在Spring Boot应用程序中实现拦截器，你需要做以下几件事：

1. 创建一个类实现`HandlerInterceptor`接口。
2. 在类上使用`@Component`注解，表明它是一个可被Spring扫描并注册的组件。
3. 实现接口中的方法，以定制拦截器的行为。
4. 在`WebMvcConfigurer`的实现中使用`addInterceptors`方法将拦截器添加到拦截器链中。

下面是一个简单的例子，使用`WebMvcConfigurer`接口来配置拦截器：

```java
@Configuration
public class MyConfiguration implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor);
    }
}


@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在这里执行拦截器的逻辑
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在这里执行拦截器的逻辑
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在这里执行拦截器的逻辑
    }
}
```



## 缓存使用

Spring Boot 支持使用缓存来提高应用程序的性能

首先，在你的项目中添加依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

然后，在你的应用程序类上添加 `@EnableCaching` 注解，来启用缓存支持

```java
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

接下来，你需要创建一个服务类来处理缓存。通过接口和一些与缓存相关的注解，

包括`@Cacheable`, `@CachePut`, `@CacheEvict`，提供了一个简单易用的缓存抽象`@Caching`。

下面是在 Spring Boot 应用程序中使用缓存抽象的示例：

```java
@Service
public class UserService {

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        // 从数据库中提取用户
        return user;
    }

    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        // 更新数据库中的用户
        return user;
    }

    @CacheEvict(value = "users", key = "#id")
    //@CacheEvict(value = "users", allEntries = true)  删除全部
    public void deleteUser(Long id) {
        // 从数据库中删除用户
    }

    @Caching(evict = {
        @CacheEvict(value = "users", key = "#id"),
        @CacheEvict(value = "userNames", key = "#user.username")
    })
    public void updateUser(Long id, User user) {
        // 更新数据库中的用户
    }
}
```

## 线程池配置

在生产环境中，通常使用线程池来处理并发请求，这样可以有效地提高应用的性能。

在 Spring Boot 中，配置线程池有多种方式，其中一种是使用 `spring.task.execution` 配置属性

注意：需要添加对 `spring-boot-starter-web` 依赖。

例如，你可以在配置文件中（如 application.properties 或 application.yml）中添加如下配置来配置线程池：

```properties
spring.task.execution.pool.core-size=20
spring.task.execution.pool.max-size=200
spring.task.execution.pool.queue-capacity=50
```

这会创建一个核心线程数为 20、最大线程数为 200、队列容量为 50 的线程池。

你也可以在应用中创建一个 `ThreadPoolTaskExecutor` 对象，并设置它的属性。例如：

```java
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(50);
        return executor;
    }
}
```

这些属性的含义如下：

- `corePoolSize`：线程池中保留的核心线程数，即使它们是空闲的。默认值是 1。
- `maxPoolSize`：线程池中允许的最大线程数。如果任务队列已满，并且当前线程数小于最大线程数，则可以创建新线程来处理任务。默认值是 `Integer.MAX_VALUE`。
- `queueCapacity`：任务队列的容量。当任务数量超过了线程池的最大线程数时，剩余的任务会被放到任务队列中。默认值是 `Integer.MAX_VALUE`。
- `keepAliveSeconds`：线程池中线程的存活时间。如果当前线程数量大于核心线程数，并且空闲时间超过了这个值，则线程会被回收。默认值是 60。
- `threadNamePrefix`：新创建的线程的名称前缀。
- `rejectedExecutionHandler`：当任务队列已满，并且无法创建新线程来处理任务时，所采取的拒绝策略。可以使用预定义的拒绝策略，比如 `ThreadPoolExecutor.AbortPolicy`、`ThreadPoolExecutor.CallerRunsPolicy` 等，也可以实现自己的拒绝策略。默认值是 `ThreadPoolExecutor.AbortPolicy`。



**生产环境ThreadPoolTaskExecutor配置**

在生产环境中，仔细调整您的配置`ThreadPoolTaskExecutor`以确保其高效且可扩展非常重要。`ThreadPoolTaskExecutor`以下是在生产环境中配置时的一些最佳实践：

1. 根据CPU内核的数量和应用程序的工作负载，适当设置`corePoolSize`和`maxPoolSize`。通常，最好将`corePoolSize`设置为CPU核数，将`maxPoolSize`设置成略高于corePoolSize的值。

2. 根据队列中的预期任务数设置`queueCapacity`。如果队列太小，如果池已满，任务可能会被拒绝。如果队列太大，可能会占用大量内存。

3. 使用`allowCoreThreadTimeOut`属性可以在空闲线程的空闲时间超过`keepAliveTime`时终止它们。这有助于防止池变大。

4. 使用getActiveCount（）、getPoolSize（）、getQueue.size（）和getRejectedExecutionCount()方法监视线程池的性能。这可以帮助您识别应用程序中的任何瓶颈，并相应地调整配置。

   使用活动线程数、线程池数量，队列大小和拒绝率（因队列已满而被拒绝的任务数）等指标监控线程池的性能。这些指标可以帮助您识别瓶颈并微调线程池的配置。

5. 考虑使用 `RejectedExecutionHandler`来处理因队列已满或线程池已达到最大大小而被拒绝的任务。有几个可用的内置`RejectedExecutionHandler`实现，例如`AbortPolicy`（抛出`RejectedExecutionException`）和`CallerRunsPolicy`（在调用线程中运行任务）。

6. 如果您的任务是长时间运行或阻塞的，请考虑为它们使用单独的线程池，以避免影响主线程池的性能。

以下是生产就绪`ThreadPoolTaskExecutor`配置的示例：

```java
@Configuration
public class AsyncConfiguration {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 将核心池大小设置为CPU核心数
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // 将最大池大小设置为略高于核心池大小的值
        executor.setMaxPoolSize(executor.getCorePoolSize() + 1);
		// 根据预期任务数设置任务队列的容
        executor.setQueueCapacity(1000);
        // 如果空闲线程的空闲时间超过keepAliveTime，则允许终止它们
        executor.setAllowCoreThreadTimeOut(true);
        // 设置线程名称前缀
        executor.setThreadNamePrefix("MyThreadPool-");
        // 使用AbortPolicy处理由于队列已满或线程池已达到其最大大小而被拒绝的任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 初始化执行器
        executor.initialize();
        return executor;
    }
}
```



## 异步调用

`@Async`这是有关如何在 Spring Boot 应用程序中使用注释的分步教程：

1. 将`spring-boot-starter-aop`依赖项添加到您的项目。这是`@Async`注释工作所必需的。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

2. `@Async`通过将注释添加`@EnableAsync`添加到您的其中一个`@Configuration`类或使用文件中的`spring.main.enable-auto-configuration`属性来启用注释`application.properties`。

```java
@Configuration
@EnableAsync
public class AsyncConfiguration {
}
```

```properties
# application.properties
spring.main.enable-auto-configuration=true
```

3. 创建一个服务类并用 注释一个方法`@Async`。方法的返回类型必须为 void。

```java
@Service
public class DataProcessingService {

    @Async
    //@Async(value = "taskExecutor")  指定线程池异步执行
    public void processData() {
        // Do some processing here
    }
}
```

4. 注入`TaskExecutor`or `AsyncTaskExecutor`bean 并使用它来执行该`@Async`方法。

```java
@Autowired
private TaskExecutor taskExecutor;

// 注入自定义线程池调用
// @Autowired
// private ThreadPoolTaskExecutor taskExecutor;

@Autowired
private AsyncTaskExecutor asyncTaskExecutor;

@Autowired
private DataProcessingService dataProcessingService;

// 使用 TaskExecutor
taskExecutor.execute(() -> dataProcessingService.processData());

// 使用 AsyncTaskExecutor
asyncTaskExecutor.execute(() -> dataProcessingService.processData());
```

您的`@Async`方法现在将在单独的线程中异步执行。

注意：如果需要从异步方法返回值，可以使用`CompletableFuture`。

> TaskExecutor 和 AsyncTaskExecutor 和 ThreadPoolTaskExecutor 的区别

`TaskExecutor`是定义任务执行策略的接口，可以用来异步执行Runnables。它有一个`execute(Runnable task)`接受 Runnable 并异步执行它的方法。

`AsyncTaskExecutor`是的一个子接口`TaskExecutor`，增加了对调度任务的支持。它有额外的方法来安排任务在稍后的时间或以固定的速率执行。

`ThreadPoolTaskExecutor`是`AsyncTaskExecutor`使用线程池执行任务的实现。它创建固定数量的线程并将任务添加到队列中。当一个线程可用时，它从队列中取出一个任务并执行它。

以下是这三种类型之间主要区别的总结：

- `TaskExecutor`：定义任务执行策略。可用于异步执行 Runnable。
- `AsyncTaskExecutor`：扩展`TaskExecutor`并添加了对调度任务的支持。
- `ThreadPoolTaskExecutor`：实现`AsyncTaskExecutor`并使用线程池来执行任务。

您可以使用该`TaskExecutor`接口异步执行任务，并使用该`AsyncTaskExecutor`接口安排稍后执行的任务。`ThreadPoolTaskExecutor`是这些接口的便捷实现，它使用线程池来执行任务。

## 定时任务

Spring 提供了 `@Scheduled` 注解来配置定时任务。

首先，需要在配置类中添加 `@EnableScheduling` 注解，以启用定时任务的配置：

```java
@Configuration
@EnableScheduling
public class SchedulingConfig {
}
```

然后，使用 `@Scheduled` 注解实现定时任务：

例如，使用 **fixedRate** 属性，设置任务每 5 秒执行一次：

```java
//指定任务执行的间隔时间（毫秒）,执行两次函数
@Scheduled(fixedRate = 5000)
public void doSomething() {
    // task logic
}
```

使用 **fixedDelay** 属性，设置任务结束后 10 秒再次执行：

```java
//指定任务结束后多久再次执行（毫秒）。
@Scheduled(fixedDelay = 10000)
public void doSomething() {
    // task logic
}
```

使用 **initialDelay** 属性表示第一次执行延迟10s：

```java
//指定任务第一次执行延迟时间（毫秒）
@Scheduled(fixedRate = 5000,initialDelay=10000)
public void doSomething() {
    // task logic
}
```

使用**zone**，指定在上海时区内按照设定的间隔时间执行任务：

```java
//指定时区，默认是UTC时间
@Scheduled(fixedRate = 5000, zone = "Asia/Shanghai")
public void doSomething() {
    // task logic
}
```

使用**cron**属性，设置任务每天0点执行一次：

```java
//指定任务执行的时间表达式,分别表示：秒，分，时，日，月，周，年
@Scheduled(cron = "0 0 0 * * ?")
public void doSomething() {
    // task logic
}
```

* 其中，可以通过https://cron.help/ 网站解释corn表达式
  * `*` 表示匹配所有
  * `/` 表示每隔一段时间执行一次
  * `?` 表示不指定值，只能用在星期几和每月的第几天

## 热部署

Spring Boot 支持热部署，你可以使用Spring Loaded和Devtools来实现。

Spring Loaded 是一个Java Agent，它能够在类加载时替换字节码，实现类的热更新。

Devtools 是 Spring Boot的一个工具，它可以监控项目的文件变化，并自动重启应用。

在项目中添加以下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
</dependency>
```

并在application.properties中配置

```properties
spring.devtools.restart.enabled=true
```

然后就可以实现热部署了

需要注意的是，热部署并不能替代完全重启应用程序，并且不能更新所有类型的更改，例如更改配置文件或更改类路径上的类。

# 03.Thymeleaf 模板引擎

**参考：**http://c.biancheng.net/spring_boot/thymeleaf.html

## Thymeleaf 简介

Thymeleaf 是新一代 Java 模板引擎，与 Velocity、FreeMarker 等传统 Java 模板引擎不同，Thymeleaf 支持 HTML 原型，其文件后缀为“.html”，因此它可以直接被浏览器打开，此时浏览器会忽略未定义的 Thymeleaf 标签属性，展示 thymeleaf 模板的静态页面效果；当通过 Web 应用程序访问时，Thymeleaf 会动态地替换掉静态内容，使页面动态显示。

Thymeleaf 通过在 html 标签中，增加额外属性来达到“模板+数据”的展示方式，示例代码如下。

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--th:text 为 Thymeleaf 属性，用于在展示文本-->
<h1 th:text="迎您来到Thymeleaf">欢迎您访问静态页面 HTML</h1>
</body>
</html>
```

## Thymeleaf 语法规则

在使用 Thymeleaf 之前，首先要在页面的 html 标签中声明名称空间，示例代码如下。

```html
xmlns:th="http://www.thymeleaf.org"
```

> 在 html 标签中声明此名称空间，可避免编辑器出现 html 验证错误，但这一步并非必须进行的，即使我们不声明该命名空间，也不影响 Thymeleaf 的使用。

Thymeleaf 作为一种模板引擎，它拥有自己的语法规则。Thymeleaf 语法分为以下 2 类：

### 标准表达式语法

1. 获取属性：`${object.property}`，其中 `object` 是从数据模型中获取的对象，`property` 是对象的属性名称。
2. 方法调用：`${object.method()}`，其中 `method()` 是从对象中调用的方法。
3. 使用方法参数：`${object.method(arg1, arg2)}`，其中 `arg1` 和 `arg2` 是方法的参数。
4. 集合操作：`${collection.size()}` 获取集合的大小，`${collection.isEmpty()}` 判断集合是否为空，`${collection.iterator()}` 获取集合的迭代器等。
5. 数组操作：`${array.length}` 获取数组的长度，`${array[0]}` 获取数组的第一个元素等。
6. 运算符：支持加、减、乘、除、取余、大于、小于、等于、不等于等运算符，例如 `${num1 + num2}`、`${str1 == str2}` 等。
7. 内联选择器：`${#ids.size()}` 获取文档中具有 `id` 属性的元素数量，`${#request.getParameter('id')}` 获取 HTTP 请求参数的值等。
8. 内置对象：Thymeleaf 提供一些内置对象，如 `${#ctx.request.contextPath}` 获取当前请求的上下文路径，`${#locale.language}` 获取当前区域设置的语言等。
9. 特殊字符：支持使用 `\` 转义特殊字符，如 `${'Hello\'s World!'}` 将显示为 `Hello's World!`。

### th 属性

- th:text：用于设置HTML标签的文本内容
- th:if：用于条件判断
- th:switch和th:case：用于实现类似于switch语句的多条件判断
- th:href：用于设置链接的URL地址
- th:src：用于设置图像或其他媒体的URL地址
- th:each：用于迭代集合或数组，并将每个元素应用到一个HTML块中
- th:object：用于指定用于当前表单的对象

这些th属性可以在HTML标签中使用，以便在模板中使用Thymeleaf的功能和语法。例如：

```html
<p th:text="${message}">Hello, World!</p>
```

## Spring Boot整合Thymeleaf

以下是使用Spring Boot和Thymeleaf构建Web应用程序的一些简单步骤：

1. 添加Thymeleaf依赖

在pom.xml中添加以下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

2. 配置Thymeleaf

```yaml
# 应用名称
spring:
  application:
    name: thymeleaf
  # 配置模板引擎
  thymeleaf:
    encoding: UTF-8
    mode: HTML
    prefix: classpath:/templates/
    suffix: .html
```

3. 创建Thymeleaf模板

在src/main/resources/templates目录下创建一个名为“index.html”的文件，并添加以下内容：

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Spring Boot and Thymeleaf</title>
</head>
<body>
    <h1 th:text="${message}"></h1>
</body>
</html>
```

4. 创建控制器

创建一个名为“MainController”的控制器，并添加以下内容：

```java
@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello, World!");
        return "index";
    }
}
```

5. 运行应用程序

运行应用程序并在浏览器中访问 [http://localhost:8080](http://localhost:8080 )，您应该会看到“Hello, World!”在页面上显示。

# 04.Kaptcha 验证码实现

## Google Kaptcha简介

Github：https://github.com/penggle/kaptcha

> Kaptcha是由Google的开源项目Kaptcha Project开发的。
>
> 该项目最初是为了开发一个能够防止机器人和自动化程序恶意攻击的验证码库而创建的。

Kaptcha 是一个 Java 库，用于生成验证码图像，其主要功能包括：

1. 生成随机字符：Kaptcha 可以生成包含随机字符的图像，用于验证码等应用场景。
2. 图像样式：Kaptcha 提供了多种图像样式，包括普通文本、带噪点的文本、扭曲的文本等。
3. 安全性：Kaptcha 支持防止恶意攻击的功能，如防止暴力破解、防止重放攻击等。
4. 可定制性：Kaptcha 提供了许多可定制的参数，如图像大小、字体大小、字体颜色、背景颜色等，使用户可以根据自己的需求自定义验证码图像。
5. 集成性：Kaptcha 可以与多种 Java 框架集成，如 Spring、Struts、Servlet 等，使其在不同的应用场景下更易于使用。

## 集成SpringBoot

Kaptcha是一个非常方便易用的验证码库，以下是使用Kaptcha的一般步骤：

1. 添加Kaptcha依赖

```xml
<!--验证码-->
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>
```

2. 创建一个`KaptchaConfig`配置类，并在其中定义两个`Producer`类型的Bean对象，用于生成验证码图片。

```java
/**
 * 验证码配置
 */
@Configuration
public class CaptchaConfig {
    @Bean(name = "captchaProducer")
    public DefaultKaptcha getKaptchaBean() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框 默认为true 我们可以自己设置yes，no
        properties.setProperty(KAPTCHA_BORDER, "yes");
        // 验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        // 验证码图片宽度 默认为200
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
        // 验证码图片高度 默认为50
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
        // 验证码文本字符大小 默认为40
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "38");
        // KAPTCHA_SESSION_KEY
        properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCode");
        // 验证码文本字符长度 默认为5
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        // 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
        // 图片样式 水纹com.google.code.kaptcha.impl.WaterRipple 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy 阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean(name = "captchaProducerMath")
    public DefaultKaptcha getKaptchaBeanMath() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框 默认为true 我们可以自己设置yes，no
        properties.setProperty(KAPTCHA_BORDER, "yes");
        // 边框颜色 默认为Color.BLACK
        properties.setProperty(KAPTCHA_BORDER_COLOR, "105,179,90");
        // 验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
        // 验证码图片宽度 默认为200
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
        // 验证码图片高度 默认为50
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
        // 验证码文本字符大小 默认为40
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "35");
        // KAPTCHA_SESSION_KEY
        properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCodeMath");
        // 验证码文本生成器
        properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.joeljhou.kaptcha.config.MathTextProducer");
        // 验证码文本字符间距 默认为2
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "3");
        // 验证码文本字符长度 默认为5
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");
        // 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
        // 验证码噪点颜色 默认为Color.BLACK
        properties.setProperty(KAPTCHA_NOISE_COLOR, "white");
        // 干扰实现类
        properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        // 图片样式
        // 水纹com.google.code.kaptcha.impl.WaterRipple
        // 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
        // 阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
```

## 自定义验证码生成器

```java
/**
 * 自定义算数验证码文本生成逻辑
 */
public class MathTextProducer implements TextProducer {

    //加减乘除
    private final static Character[] OPERATORS = {'+', '-', '*', '/'};
    private final static String CODE_FORMAT = "%d %c %d = ?@%d";

    @Override
    public String getText() {
        // 自定义生成验证码文本的逻辑
        Integer result = 0;
        Random random = new Random();
        int x = random.nextInt(10); // 生成0~9之间的随机数
        int y = random.nextInt(10);
        String code = null;
        Character operator = OPERATORS[random.nextInt(OPERATORS.length)];
        switch (operator) {
            case '+':
                result = x + y;
                break;
            case '-':
                result = x - y;
                break;
            case '*':
                result = x * y;
                break;
            case '/':
                if (!(x == 0) && y % x == 0) {
                    result = x / y;
                } else {
                    result = x + y;
                }
                break;
        }
        return String.format(CODE_FORMAT, x, operator, y, result);
    }
}
```

4. 实现验证码接口

```java
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @GetMapping("/captchaImage")
    @SneakyThrows
    public void captchaChar(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        String type = request.getParameter("type");
        String capStr = null;
        String code = null;
        BufferedImage bi = null;
        if ("math".equals(type)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            bi = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(type)) {
            capStr = code = captchaProducer.createText();
            bi = captchaProducer.createImage(capStr);
        }
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        out.flush();
        out.close();
    }

}
```





# 05.Shiro安全框架入门

> 官网地址：https://shiro.apache.org/
>
> github地址：https://github.com/apache/shiro

## [Shiro简介](https://shiro.apache.org/introduction.html)

Apache Shiro 是一个强大而灵活的开源安全框架，可以干净地处理身份验证、授权、企业会话管理和加密。

**有哪些功能？**

![image-20230129021725557](http://www.joeljhou.ga/202301290217637.png)

- 身份验证（Authentication）：确定用户是谁。
- 授权（Authorization）：确定用户能做什么。
- 会话管理（Session Management）：管理用户的会话。
- 加密（Cryptography）：保护数据的安全性。
- 缓存（Caching）：减少身份验证和授权的开销。

**[Shiro架构](https://shiro.apache.org/architecture.html)**

![image-20230129021446180](http://www.joeljhou.ga/202301290214253.png)

Shiro的架构是由三个主要部分组成的：`Subject`、`SecurityManager`和`Realms`。

- `Subject`：主体，代表了当前用户。
- `SecurityManager`：安全管理器，管理所有的Subject。
- `Realm`：域，用于认证和授权。



![image-20230129021536368](http://www.joeljhou.ga/202301290220257.png)

- `Authentication`：认证，确定Subject是谁。
- `Authorization`：授权，确定Subject能做什么。
- `Cryptography`：密码技术，用于加密和解密。
- `Session Management`：会话管理，管理Subject的会话。
- `Web Support`：Web支持，为Web应用提供特定的安全机制。
- `Caching`：缓存，用于减少认证和授权的次数。
- `Concurrency`：并发，保证多个请求的安全性。

## 10分钟教程

[10分钟教程](https://shiro.apache.org/10-minute-tutorial.html)

Shiro的官网提供了一个10分钟快速入门教程，帮助开发人员快速了解和使用Shiro。

1. 首先，需要在项目中添加Shiro的依赖，可以在Maven中添加以下依赖：

```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-core</artifactId>
    <version>1.11.0</version>
</dependency>
<!-- Shiro uses SLF4J for logging.  We'll use the 'simple' binding
             in this example app.  See https://www.slf4j.org for more info. -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.21</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>1.7.21</version>
</dependency>
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

2. 在src/main/resources目录下创建一个ini配置文件，用于配置Shiro的环境。下面是一个简单的ini配置文件示例：

```ini
[users]
root = secret, admin
guest = guest, guest
presidentskroob = 12345, president
darkhelmet = ludicrousspeed, darklord, schwartz
lonestarr = vespa, goodguy, schwartz

# -----------------------------------------------------------------------------
# Roles with assigned permissions
# roleName = perm1, perm2, ..., permN
# -----------------------------------------------------------------------------
[roles]
admin = *
schwartz = lightsaber:*
goodguy = winnebago:drive:eagle5
```

3. 在src/main/resources目录下创建log4j2.xml文件，并配置日志输出相关参数。

```xml
<Configuration name="ConfigTest" status="ERROR" monitorInterval="5">

    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="org.springframework" level="warn" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="org.apache" level="warn" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="net.sf.ehcache" level="warn" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>
        <Logger name="org.apache.shiro.util.ThreadContext" level="warn" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

4. 演示如何使用Shiro的API

```java
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的快速启动应用程序，演示如何使用Shiro的API。
 */
public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);

    public static void main(String[] args) {

        // 版本问题
        // 通过工厂模式创建SecurityManager的实例对象
        // Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // SecurityManager securityManager = factory.getInstance();

        // 新方法 shiro更新问题
        // 创建SecurityManager的实例对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        securityManager.setRealm(iniRealm);

        // 设置为当前的 SecurityManager
        SecurityUtils.setSecurityManager(securityManager);

        // 获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();

        // Session的操作（不需要web或EJB容器！！！）
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("检索到正确的值！ [" + value + "]");
        }

        // 用户认证功能
        // 判断当前用户是否被认证
        if (!currentUser.isAuthenticated()) {
            //将用户名和密码封装为 UsernamePasswordToken ；
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            //记住我功能
            token.setRememberMe(true);
            try {
                //执行登录，可以登录成功的！
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("没有用户名为的用户 " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("帐户 " + token.getPrincipal() + " 的密码不正确！");
            } catch (LockedAccountException lae) {
                log.info("账户 " + token.getPrincipal() + " 已锁定。  " +
                        "请与管理员联系以解锁它。");
            }
            // ... 在这里捕捉更多异常（可能是特定于您的应用程序的自定义异常？
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //说出他们是谁:
        //打印他们的身份主体（在本例中为用户名）：
        log.info("使用者 [" + currentUser.getPrincipal() + "] 已成功登录。");

        //角色检查
        if (currentUser.hasRole("schwartz")) {
            log.info("拥有角色权限！!");
        } else {
            log.info("没有角色权限.");
        }

        //权限检查，粗粒度
        //测试用户是否具有某一个权限，行为
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("你可以使用 lightsaber(光剑). 用它 wisely(挥).");
        } else {
            log.info("抱歉, lightsaber(光剑) 仅仅拥有角色权限 schwartz");
        }

        //权限检查，细粒度
        //测试用户是否具有某一个权限，行为，比上面更加的具体！
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("您可以使用'eagle5(车牌)' 'drive(驾驶)'  'winnebago(温尼贝戈)'.  " +
                    "这里有钥匙 - 玩得开心!");
        } else {
            log.info("抱歉, 你不能使用'eagle5(车牌)' 'drive'(驾驶)  'winnebago(温尼贝戈)'!");
        }

        //注销操作
        currentUser.logout();

        System.exit(0);
    }
}
```

## 标签使用

Shiro 是一个 Java 安全框架，提供身份验证、授权和会话管理。以下是 Shiro 中使用的一些常用标签：

1. `<shiro:hasRole>`：此标签用于检查当前用户是否具有指定角色。
2. `<shiro:hasPermission>`: 该标签用于检查当前用户是否具有指定的权限。
3. `<shiro:lacksRole>`: 该标签用于检查当前用户是否缺少指定的角色。
4. `<shiro:lacksPermission>`: 该标签用于检查当前用户是否缺少指定的权限。
5. `<shiro:guest>`：该标签用于仅当当前用户是访客（未认证）时显示内容。
6. `<shiro:authenticated>`：该标签用于仅在当前用户通过身份验证时显示内容。
7. `<shiro:user>`：此标签用于仅在当前用户通过身份验证而非访客时显示内容。

这些标签允许您根据用户的安全状态控制内容的显示，并且通常与 JSP 或 Thymeleaf 等 Web 框架结合使用。

## 注解使用

Shiro 使用注解来声明方法和类的安全约束。以下是 Shiro 中常用的一些注解：

1. `@RequiresAuthentication`：此注释用于指定方法或类需要对用户进行身份验证。
2. `@RequiresUser`：此注释用于指定方法或类需要对用户进行身份验证而不是来宾。
3. `@RequiresGuest`：此注解用于指定方法或类要求用户是来宾（未经过身份验证）。
4. `@RequiresRoles`：此注解用于指定方法或类要求用户具有特定角色。
5. `@RequiresPermissions`: 该注解用于指定某个方法或类需要用户具有特定的权限。
6. `@RequiresPermissions(value = {"permission1", "permission2"})`: 该注解用于指定某个方法或类需要用户具有特定的权限。
7. `@PermissionRequired`：这是一个自定义注释，用于声明方法或类的权限要求。

这些注释提供了使用 Shiro 标记库的替代方法，可用于以更简洁和集中的方式实施安全策略。







## SpringBoot集成Shiro

### 集成Thymeleaf

1. 使用IDEA `Spring Initializr`新建一个项目，勾选`WEB`依赖

2. 将 Thymeleaf 依赖项添加到项目的 pom.xml 文件或 build.gradle 文件中：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

3. 在项目的资源目录中创建一个名为“templates”的新文件夹，用于存储 Thymeleaf 模板。

4. 在 application.properties 或 application.yml 文件中配置模板引擎：

```properties 
# 应用服务 WEB 访问端口
server.port=8080
# 应用名称
spring.application.name=shiro-spring-boot

# 配置模板引擎
spring.thymeleaf.prefix= classpath:/templates/
spring.thymeleaf.suffix= .html
spring.thymeleaf.mode= HTML
spring.thymeleaf.encoding= UTF-8
```

5.在您的控制器中，使用`@Controller`注释和`ModelAndView`类将 Thymeleaf 模板返回给客户端。例子：

```java
@Controller
public class MyController {

    @RequestMapping({"/", "/index", "index.html"})
    public String index(Model model) {
        Map attributes = new HashMap<>();
        attributes.put("msg", "hello,Shiro");
        model.addAllAttributes(attributes);
        return "index";
    }

}
```

6. 在模板文件夹中创建 templates/index.html 文件，这将是访问“/”端点时呈现的模板。

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>首页</h1>
<!--th:text 为 Thymeleaf 属性，用于在展示文本-->
<p th:text="${msg}">欢迎您访问静态页面 HTML</p>
</body>
</html>
```

### 集成Shiro

1. 添加Shiro依赖

```xml
<!--shiro-->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-web-starter</artifactId>
    <version>1.10.1</version>
</dependency>
```

2. 自定义Realm，实现认证与授权的逻辑

```java
package com.joeljhou.shiro.config;

import com.joeljhou.shiro.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

//自定义Realm
public class MyRealm extends AuthorizingRealm {

    //实现身份认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了==》认证doGetAuthenticationInfo");
        return null;
    }


    //实现授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了==》授权doGetAuthorizationInfo");
        return null;
    }

}
```

3.添加Shiro配置类，并注入到bean中！

```java
package com.joeljhou.shiro.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//声明为配置类
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultSecurityManager securityManager,
                                                         @Qualifier("shiroFilterChainDefinition") ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置安全策略（过滤器链配置）
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

        //设置登录失败跳转页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置未授权跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("realm") Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public Realm realm() {
        return new MyRealm();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        /**
         * anon: 允许匿名访问。
         * authc: 需要身份验证才能访问。
         * authcBasic: 需要HTTP Basic身份验证才能访问。
         * logout: 用来处理退出登录的请求。
         * noSessionCreation: 禁止创建会话。
         * perms: 拥有指定权限的用户才能访问。
         * port: 根据请求端口进行过滤。
         * rest: 实现restful方式访问接口。
         * roles: 拥有指定角色的用户才能访问。
         * ssl: 只允许通过HTTPS访问。
         * user: 已经登录成功的用户才能访问。
         *
         * 还有其他一些过滤器，如：
         *
         * async: 异步请求过滤器。
         * authcBearer: JWT token 身份验证过滤器。
         * cors: 跨域请求过滤器。
         * jwt: JWT token 身份验证过滤器。
         * session: 会话过滤器。
         * stateless: 无状态认证过滤器。
         * userFilter: 自定义用户过滤器。
         */
        definition.addPathDefinition("/login", "anon");
        definition.addPathDefinition("/index", "authc");
        definition.addPathDefinition("/user/add", "perms[user:add]");
        definition.addPathDefinition("/user/update", "perms[user:update]");
        return definition;
    }

}
```

4. 页面实现

登录页 templates/login.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>登录</h1>
<hr>

<p th:text="${msg}" style="color: red"></p>

<form th:action="@{/login}">
    <p>用户名：<input type="text" name="username"></p>

    <p>密 码：<input type="password" name="password"></p>

    <p><input type="submit"></p>
</form>

</body>
</html>
```

主页 templates/index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>首页</h1>
<!--th:text 为 Thymeleaf 属性，用于在展示文本-->
<p th:text="${msg}">欢迎您访问静态页面 HTML</p>
<hr>

<a th:href="@{/user/add}">add</a> | <a th:href="@{/user/update}">update</a>

</body>
</html>
```

其他跳转页面 templates/user/add.html|update.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>add</h1>

</body>
</html>
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1>update</h1>

</body>
</html>
```

5. 实现页面跳转器 MyController

```java
package com.joeljhou.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MyController {

    @GetMapping({"/", "/index", "index.html"})
    public String index(Model model) {
        Map attributes = new HashMap<>();
        attributes.put("msg", "hello,Shiro");
        model.addAllAttributes(attributes);
        return "index";
    }

    @GetMapping("user/add")
    public String add() {
        return "user/add";
    }

    @GetMapping("user/update")
    public String update() {
        return "user/update";
    }

    //登录
    @GetMapping("/login")
    public String login(String username, String password, Model model) {
        //获取当前的用户
        Subject currentUser = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);  //记住我功能

        //执行登录的方法
        try {
            //执行登录，可以登录成功的！
            currentUser.login(token);
            //登录成功返回首页
            return "index";
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "用户不存在");
            return "login";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "密码不正确");
            return "login";
        } catch (LockedAccountException lae) {
            model.addAttribute("账户已锁定。 请与管理员联系以解锁它。");
            return "login";
        }
        // ... 在这里捕捉更多异常（可能是特定于您的应用程序的自定义异常？
        catch (AuthenticationException ae) {
            //unexpected condition?  error?
            return "login";
        }
    }

}
```

### 集成Mybatis

1. 导入Mybatis相关依赖

```xml
<!--mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.3.0</version>
</dependency>

<!--mybatis-plus-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version>
</dependency>

<!--druid-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.15</version>
</dependency>

<!--mysql-connector-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.31</version>
</dependency>
```

2. 编写配置文件(连接rouyi的数据库)

```properties
# 数据源配置
spring.datasource.url=jdbc:mysql://ry-mysql:3306/ry?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# 配置druid监控
spring.datasource.druid.stat-view-servlet.login-username=admin
spring.datasource.druid.stat-view-servlet.login-password=admin

# Mybatis配置
mybatis-plus.configuration.map-underscore-to-camel-case=true
mybatis-plus.type-aliases-package=com.joeljhou.shiro.pojo
mybatis-plus.mapper-locations=classpath:mapper/*.xml
# mybatis-plus配置日志打印
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

3. 编写实体类,引入Lombok

```java
/**
 * 用户信息表
 */
@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 登录账号
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 用户昵称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

}
```

```java
/**
 * 角色信息表
 */
@Data
@TableName(value = "sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @TableField("role_key")
    private String roleKey;

}
```

```java
/**
 * 用户和角色关联表
 */
@Data
@TableName(value = "sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户ID
     */
    @TableField("role_id")
    private Long roleId;

}
```

```java
/**
 * 菜单权限表
 */
@Data
@TableName(value = "sys_menu")
public class SysMenu implements Serializable {

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @TableField("menu_type")
    private String menuType;

    /**
     * 权限标识
     */
    @TableField("perms")
    private String perms;

}
```

```java
/**
 * 角色和菜单关联表
 */
@Data
@TableName(value = "sys_role_menu")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;

}
```

4. 编写Mapper接口

```java
@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 按用户名查找用户
     */
    SysUser findByLoginName(String userName);

}
```

```java
@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findRolesByUserId(Long userId);

}
```

```java
@Repository
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> findPermissionsByUserId(Long userId);

}
```

5. 编写Mapper.xml

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.joeljhou.shiro.mapper.SysUserMapper">

    <resultMap type="SysUser" id="SysUserResult">
        <id property="userId" column="user_id"/>
        <result property="loginName" column="login_name"/>
        <result property="userName" column="user_name"/>
        <result property="password" column="password"/>
    </resultMap>

    <select id="findByLoginName" parameterType="String" resultMap="SysUserResult">
        select user_id, login_name, user_name, password
        from sys_user u
        where u.login_name = #{userName}
    </select>

</mapper>
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.joeljhou.shiro.mapper.SysRoleMapper">

    <resultMap type="SysRole" id="SysRoleResult">
        <id property="roleId" column="role_id"/>
        <result property="roleName" column="role_name"/>
        <result property="roleKey" column="role_key"/>
    </resultMap>

    <select id="findRolesByUserId" parameterType="Long" resultMap="SysRoleResult">
        select distinct r.role_id, r.role_name, r.role_key
        from sys_role r
                 left join sys_user_role ur on ur.role_id = r.role_id
                 left join sys_user u on u.user_id = ur.user_id
        where ur.user_id = #{userId}
    </select>
</mapper>
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.joeljhou.shiro.mapper.SysMenuMapper">

    <select id="findPermissionsByUserId" parameterType="Long" resultType="String">
        select distinct m.perms
        from sys_menu m
                 left join sys_role_menu rm on m.menu_id = rm.menu_id
                 left join sys_user_role ur on rm.role_id = ur.role_id
                 left join sys_role r on r.role_id = ur.role_id
        where ur.user_id = #{userId}
    </select>

</mapper>
```

6. 编写Service接口

```java
public interface ISysUserService extends IService<SysUser> {

    SysUser findByLoginName(String loginName);

}
```

```java
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    Set<String> findRolesByUserId(Long userId);

}

```

```java
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> findPermissionsByUserId(Long userId);

}
```

7. 编写Service实现

```java
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public SysUser findByLoginName(String loginName) {
        return sysUserMapper.findByLoginName(loginName);
    }

}
```

```java
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMapper sysRoleMapper;

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public Set<String> findRolesByUserId(Long userId) {
        List<SysRole> perms = sysRoleMapper.findRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (perm != null) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }
}
```

```java
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final SysMenuMapper sysMenuMapper;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> findPermissionsByUserId(Long userId) {
        List<String> perms = sysMenuMapper.findPermissionsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (perm != null) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

}
```

### Realm认证授权

```java
package com.joeljhou.shiro.config;

import com.joeljhou.shiro.pojo.SysUser;
import com.joeljhou.shiro.service.ISysMenuService;
import com.joeljhou.shiro.service.ISysRoleService;
import com.joeljhou.shiro.service.ISysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

//自定义Realm
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    //实现授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了==》授权doGetAuthorizationInfo");
        SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
        //角色
        Set<String> roles = sysRoleService.findRolesByUserId(sysUser.getUserId());
        //权限
        Set<String> permissions = sysMenuService.findPermissionsByUserId(sysUser.getUserId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    //实现身份认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了==》认证doGetAuthenticationInfo");

        // 实现身份认证逻辑
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        // 根据登录账号从数据库中查询用户信息
        SysUser user = sysUserService.findByLoginName(username);

        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("密码不正确");
        }
        // 返回认证信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        //加盐加密
        info.setCredentialsSalt(ByteSource.Util.bytes("salt"));
        return info;
    }

    //返回Realm的名字
    @Override
    public String getName() {
        return "myRealm";
    }

    //判断此Realm是否支持此Token，此处可以根据需要更改，以实现对不同类型的Token的支持
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

}
```

### Shiro密码加密

```java
/**
 * Shiro加密工具类
 */
public class ShiroHashingUtil {

    /**
     * 生成随机盐
     */
    public static String randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        return hex;
    }

    /**
     * Shiro密码加密
     */
    public static String encryptPassword(String loginName, String password, String salt) {
        return new Md5Hash(loginName + password + salt).toHex();
    }

    /**
     * Shiro密码校验
     */
    public static boolean matches(SysUser user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

}
```

### Thymeleaf整合Shiro

要将 Thymeleaf 与 Spring Boot 项目集成并启用对 Shiro 标签的支持，您可以按照以下步骤操作：

1. 将必要的依赖项添加到项目的 pom.xml 中：

```xml
<!--thymeleaf-shiro-->
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>2.1.0</version>
</dependency>
```

2. 将 Shiro 方言添加到您的 Thymeleaf 配置类中：

```java
//配置ShiroDialect：方言，用于 thymeleaf 和 shiro 标签配合使用
@Bean
public ShiroDialect shiroDialect() {
    return new ShiroDialect();
}
```

3. 在您的 Thymeleaf 模板中使用 Shiro 标签以根据用户身份验证和授权数据保护内容：

```html
<html xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
  <body>
    <div shiro:hasRole="admin">
      此内容仅对具有“admin”角色的用户可见。
    </div>
    <div shiro:authenticated>
      此内容仅对经过身份验证的用户可见。
    </div>
    <div shiro:hasPermission="system:view">
       此内容仅对具有“system:view”权限的用户可见。
    </div>
  </body>
</html>
```

# 06.事务管理

Java 事务是在关系型数据库中处理并发操作的重要机制，能够保证数据库中数据的`一致性`和`完整性`。

## 事务隔离级别

事务隔离级别是指多个事务之间的隔离程度，主要有以下 4 种隔离级别：

* **读未提交（Read Uncommitted）**
  * 最低的隔离级别，一个事务可以读取另一个事务尚未提交的数据。
  * 这样可能会导致`脏读`（dirty read），即读取到未提交的数据，可能造成数据的不一致。
* **读已提交（Read Committed）**
  * 一个事务只能读取另一个事务已经提交的数据。避免了脏读。
  * 但是可能会造成`不可重复读`（non-repeatable read）的问题，即同一事务中两次读取同一数据，结果不一致。
* **可重复读（Repeatable Read）**
  * 在一个事务中多次读取同一数据，其结果是一致的，不保证读取到的数据是最新的。
  * 并发情况下可能会出现`幻读`（phantom read）的问题，即一个事务读取到了其他事务插入的数据。
* **串行化（Serializable）**
  * 最高的隔离级别，保证了事务的串行执行。
  * 事务串行化可以避免脏读、不可重复读和幻读等问题，但是会影响并发性能。

## 事务传播行为

事务传播行为是指一个事务方法调用另一个事务方法时，当前事务如何传播到被调用方法中。
Spring的事务传播行为有7种，分别是：

* **PROPAGATION_REQUIRED**`默认`
  * 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。

* **PROPAGATION_SUPPORTS**
  * 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。

* **PROPAGATION_MANDATORY**
  * 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。

* **PROPAGATION_REQUIRES_NEW**
  * 创建一个新的事务，如果当前存在事务，则把当前事务挂起。

* **PROPAGATION_NOT_SUPPORTED**
  * 以非事务方式运行，如果当前存在事务，则把当前事务挂起。
* **PROPAGATION_NEVER**
  * 以非事务方式运行，如果当前存在事务，则抛出异常。

* **PROPAGATION_NESTED**
  * 如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；
  * 如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。

## 分布式事务

分布式事务是指涉及多个资源（如多个数据库、消息队列等）的事务操作。由于多个资源的操作涉及到网络传输等因素，因此分布式事务的处理较为复杂。通常采用以下两种方式实现分布式事务：

### 两阶段提交（Two-Phase Commit）

两阶段提交是一种基于协调者-参与者模式的分布式事务协议，可以确保所有参与者在提交或回滚时达成一致。在该协议中，有一个`协调者`（Coordinator）协调多个`参与者`（Participant）的提交或回滚。

两阶段提交协议分为两个阶段：

* 预提交阶段
  * 协调者询问所有参与者是否可以提交，参与者回复协调者是否可以提交。
* 提交阶段
  * 如果所有参与者都同意提交，则协调者通知所有参与者提交。
  * 如果有任何一个参与者不能提交，则协调者通知所有参与者回滚。

两阶段提交协议可以保证事务的一致性，但是它的性能和可用性较低，因为需要等待所有参与者的响应。

### 补偿事务（Compensating Transaction）

补偿事务是一种基于撤销操作的分布式事务处理方式，即在分布式事务提交后，如果某个操作失败，则通过补偿操作来撤销已经提交的操作，使整个分布式事务回到原始状态。

补偿事务可以保证系统的可用性和性能，但是需要针对每个操作编写相应的补偿操作，增加了系统的复杂性。

## 使用 Spring 管理事务

Spring 是一款流行的 Java 开发框架，提供了完善的事务管理机制。在 Spring 中，事务管理是通过 AOP（面向切面编程）实现的。Spring 事务管理机制的主要组件包括：

- **PlatformTransactionManager**：
  - 事务管理器，管理事务的创建、提交和回滚。
- **TransactionDefinition**：
  - 事务定义，定义事务的隔离级别、传播行为和超时时间等。
- **TransactionStatus**：
  - 事务状态，表示事务的当前状态，如已提交、已回滚等。

 使用 Spring 管理事务可以大大简化事务的编写和管理，提高开发效率和系统性能。

综上所述，Java 事务在实际应用中具有重要作用，不仅能够保证数据的一致性和完整性，还能提高系统的并发性能和可用性。同时，隔离级别、传播行为和分布式事务等进阶应用也需要开发人员掌握。在实际开发中，使用 Spring管理事务时，通常会将 `@Transactional` 注解放在 Service 类的方法上。这样，当方法被调用时，Spring 将会在方法执行之前自动开启一个事务，并在方法执行完毕之后提交或回滚该事务。

除了在方法上使用 `@Transactional` 注解外，还可以在类级别上使用该注解，以便将注解应用到该类中的所有方法上。例如：

```java
@Transactional
public class MyService {
    public void method1() {
        // ...
    }
    
    public void method2() {
        // ...
    }
}
```

在这个例子中，`@Transactional` 注解被放在 `MyService` 类的头部，这意味着该注解将应用到该类中的所有方法上。

此外，`@Transactional` 注解还支持一些参数，以便进一步控制事务的行为，例如：

- `propagation`：指定事务的传播行为，默认值为 `REQUIRED`。
- `isolation`：指定事务的隔离级别，默认值为 `DEFAULT`。
- `readOnly`：指定事务是否只读，默认值为 `false`。
- `timeout`：指定事务的超时时间，默认值为 `-1`（永不超时）。
- `rollbackFor`：指定应该触发当前事务回滚的异常。{xxx1.class, xxx2.class,……}
- `noRollbackFor`：指定不应触发当前事务回滚的异常。


例如：

```java
@Transactional(propagation = Propagation.REQUIRED, 
               isolation = Isolation.READ_COMMITTED, 
               readOnly = false, 
               timeout = 30)
public void myMethod() {
    // ...
}

```

在这个例子中，`@Transactional` 注解被用来指定事务的传播行为为 `REQUIRED`，隔离级别为 `READ_COMMITTED`，只读标志为 `false`，超时时间为 `30` 秒。

最后需要注意的是，在使用 Spring 管理事务时，需要确保 Spring 的事务管理器配置正确，并且数据库驱动程序支持事务操作。

##  事务不回滚问题

**示例1：手动抛出检查异常导致事务不回滚**

```java
throw new SQLException("发生异常了..");
```

因为`Spring`的默认的事务规则是遇到运行异常`（RuntimeException）`和程序错误`（Error）`才会回滚。如果想针对检查异常进行事务回滚，可以在`@Transactional`注解里使用 `rollbackFor`属性明确指定异常。

**java中异常分类**

* `检查异常`（Checked Exception）：

是编译时异常，在代码中必须显式地进行处理或者在方法声明中抛出。例如 IOException、SQLException 等。

* `运行时异常`（RuntimeException）：

是在运行时可能出现的异常，通常由程序逻辑或者运行环境问题引起，不需要显式地在代码中处理或者抛出。例如 NullPointerException、IllegalArgumentException 等。

* `错误`（Error）：

是指程序运行时出现的错误，通常是由系统或者硬件问题引起，例如 、StackOverflowError 等。与异常不同，错误一般是不可恢复的，程序无法处理，只能让程序终止。



**示例2：在业务层手工捕捉并处理了异常，你都把异常“吃”掉了，当然不会回滚**

```java
try{
    // 谨慎：尽量不要在业务层捕捉异常并处理
    throw new SQLException("发生异常了..");
}catch (Exception e){
    e.printStackTrace();
}
```

推荐做法：在业务层统一抛出throw 异常，然后在控制层统一处理。

 

# 07.全局异常处理

在Java中，全局异常处理方案可以通过以下方式实现：

* 使用`try-catch`块：
  * 在可能抛出异常的代码块使用try-catch块捕获异常并处理
  * 这种方式需要手动编写try-catch代码块，存在代码冗余问题
* `自定义异常类`
  * 根据业务逻辑定义自己的异常类，继承RuntimeException或Exception
  * 在代码中按照需求抛出自定义的异常，使用try-catch捕获异常并处理，可以把所有的异常处理都放在一起
* 使用框架提供的`全局异常处理器`
  * Spring Boot框架提供了全局异常处理器（`@ControllerAdvice`和`@ExceptionHandler`注解）
  * 可以统一处理所有抛出的异常

## GlobalExceptionHandler

当使用全局异常处理器来统一处理异常时，通常需要经过以下几个步骤：

1. **定义返回统一实体**

为了让异常统一返回给客户端，需要定义一个统一格式的返回实体类，表示请求、相应时的结果。

```java
/**
 * 响应信息主体
 * 异常码规范：200-成功，400-客户端错误，500-服务端错误
 *
 * @param <T>
 */
@ToString
@NoArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public static final int SUCCESS_CODE = 200;
    public static final int FAILED_CODE = 500;

    public static <T> R<T> success() {
        return restResult(null, SUCCESS_CODE, "success");
    }

    public static <T> R<T> success(T data) {
        return restResult(data, SUCCESS_CODE, "success");
    }

    public static <T> R<T> success(T data, String msg) {
        return restResult(data, SUCCESS_CODE, msg);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, FAILED_CODE, msg);
    }

    public static <T> R<T> failed(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static boolean isSucceed(R r) {
        return r.getCode() == SUCCESS_CODE;
    }

    public static boolean isFailed(R r) {
        return r.getCode() != SUCCESS_CODE;
    }

}
```

2. **定义应用程序`顶级异常基类`**

在实际应用中，使用 `ApplicationException` 作为顶级异常基类，继承自 `RuntimeException`，我们可以定义一些子类来扩展不同类型的异常，例如 `DataException`、`PermissionException` 等等，分别代表数据异常和权限异常等业务场景。

```java
/**
 * 应用程序异常（继承自 RuntimeException）
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码
     */
    private Integer code;

    /**
     * 异常消息
     */
    private String message;

    /**
     * 异常数据
     */
    private Object data;

    /**
     * 异常参数
     */
    private Object[] args;

    /**
     * 构造函数，用于抛出非编码异常，消息已经定义好了
     *
     * @param message 异常消息
     * @param args    异常消息中格式化参数列表
     */
    public ApplicationException(String message, Object... args) {
        super(String.format(message, args));
        this.args = args;
    }

    /**
     * 构造函数，使用特定的异常编码和异常消息来构造 ApplicationException
     *
     * @param code    异常编码
     * @param message 异常消息
     */
    public ApplicationException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数，使用特定的异常编码、异常消息和异常数据来构造 ApplicationException
     *
     * @param code    异常编码
     * @param message 异常消息
     * @param data    异常数据
     */
    public ApplicationException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造函数，使用特定的异常编码、异常消息和异常参数来构造 ApplicationException
     *
     * @param code    异常编码
     * @param message 异常消息
     * @param args    异常参数
     */
    public ApplicationException(Integer code, String message, Object[] args) {
        super(String.format(message, args));
        this.code = code;
        this.message = String.format(message, args);
        this.args = args;
    }


    /**
     * 构造函数，使用特定的异常编码、异常消息、异常数据和异常参数来构造 ApplicationException
     *
     * @param code    异常编码
     * @param message 异常消息
     * @param data    异常数据
     * @param args    异常参数
     */
    public ApplicationException(Integer code, String message, Object data, Object[] args) {
        super(String.format(message, args));
        this.code = code;
        this.message = String.format(message, args);
        this.data = data;
        this.args = args;
    }

}
```

`BusinessException` 继承自 `ApplicationException` 的主要目的是让我们能够根据业务场景定制不同类型的异常。它可以作为 `ApplicationException` 的一种子类，代表种类更加具体的业务异常。

```java
/**
 * 业务异常（继承自 ApplicationException）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends ApplicationException {
    private static final long serialVersionUID = 1L;


}
```

3. **定义`ErrorWrapper`接口**

ErrorWrapper接口定义了一些处理错误的通用方法

```java
public interface ErrorWrapper {

    /**
     * 获取个性化的错误消息
     *
     * @param args 错误消息参数
     * @return 个性化的错误消息
     */
    String getMessage(Object... args);

    /**
     * 抛出指定异常
     *
     * @param args 异常参数
     * @return ApplicationException
     */
    ApplicationException exception(Object... args);

    /**
     * 返回标准的失败响应 R
     *
     * @return R
     */
    R response();

}
```

4. **定义`异常枚举类`**

* 定义异常枚举`ApplicationErrorCode`并实现`ErrorWrapper`接口


```java
@Getter
@AllArgsConstructor
public enum ApplicationErrorCode implements ErrorWrapper {

    INVALID_PARAMETER(10001, "无效的参数"),

    MISSING_PARAMETER(10002, "缺失的参数"),

    FILE_IO_EXCEPTION(10003, "文件读写异常"),

    DATABASE_EXCEPTION(10004, "数据库异常"),

    NETWORK_EXCEPTION(10005, "网络异常"),

    CONNECTION_REFUSED_EXCEPTION(10006, "连接被拒绝"),

    UNAUTHORIZED_EXCEPTION(10007, "未授权"),

    FORBIDDEN_EXCEPTION(10008, "禁止访问"),

    RESOURCE_NOT_FOUND_EXCEPTION(10009, "资源不存在"),

    UNSUPPORTED_MEDIA_TYPE_EXCEPTION(10010, "不支持的媒体类型"),

    SERVICE_UNAVAILABLE_EXCEPTION(10011, "服务不可用"),

    INTERNAL_SERVER_ERROR_EXCEPTION(10012, "服务器内部错误"),

    TIMEOUT_EXCEPTION(10013, "超时异常"),

    DUPLICATE_KEY_EXCEPTION(10014, "唯一键冲突"),

    UNSUPPORTED_OPERATION_EXCEPTION(10015, "不支持的操作"),

    SYSTEM_SERVICE_ERROR(99999, "系统服务错误"),

    ;


    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 获取个性化的错误消息
     *
     * @param args 错误消息参数
     * @return 个性化的错误消息
     */
    @Override
    public String getMessage(Object... args) {
        return String.format(message, args);
    }

    /**
     * 抛出指定异常
     *
     * @param args 异常参数
     * @return ApplicationException
     */
    @Override
    public ApplicationException exception(Object... args) {
        return new ApplicationException(code, message, args);
    }

    /**
     * 返回标准的失败响应 R
     *
     * @return R
     */
    @Override
    public R response() {
        return R.failed(code, message);
    }

}
```

* 定义异常枚举`BusinessErrorCode`并实现`ErrorWrapper`接口

```java
@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements ErrorWrapper {

    //定义一个登录异常
    LOGIN_ERROR(20001, "登录失败，用户名 【%s】 的密码错误。"),

    ;


    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String message;


    /**
     * 获取个性化的错误消息
     *
     * @param args 错误消息参数
     * @return 个性化的错误消息
     */
    @Override
    public String getMessage(Object... args) {
        return String.format(message, args);
    }

    /**
     * 抛出指定异常
     *
     * @param args 异常参数
     * @return ApplicationException
     */
    @Override
    public ApplicationException exception(Object... args) {
        return new ApplicationException(code, message, args);
    }

    /**
     * 返回标准的失败响应 R
     *
     * @return R
     */
    @Override
    public R response() {
        return R.failed(code, message);
    }

}
```

5. **实现全局异常处理器**

自定义一个全局异常处理器类，使用@RestControllerAdvice进行标记，并在其内部通过@ExceptionHandler注解处理异常。

```java
/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleBindException(BindException e) {
        String message = e.getFieldError().getDefaultMessage();
        return R.failed(message);
    }

    /**
     * 处理校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return R.failed(message);
    }


    /**
     * 捕获自定义异常 ApplicationException
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public R<Void> handleApplicationException(ApplicationException e) {
        return R.failed(e.getCode(), e.getMessage());
    }

    /**
     * 捕获全局异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        //可根据需要选择打印日志或发送邮件通知等
        return R.failed(e.getMessage());
    }
}
```

6. **在Controller中抛出异常**

当发生业务异常时，在Controller中抛出RuntimeException及其子类的实例即可。全局异常处理器会自动拦截，并以统一格式返回给前端。

```java
@RestController
public class SysIndexController {
    /**
     * 首页方法
     */
    @GetMapping("/login")
    public R<Void> login(@Validated User user) {
        //模拟用户未登录，抛出业务逻辑异常
        throw BusinessErrorCode.LOGIN_ERROR.exception(user.getUsername());
    }
}
```

## 后端异常信息国际化

**① 配置 i18n 错误文案**

在resources/i18n下配置`messages_zh_CN.properties`和`messages_en_US.properties`文件

```properties
LOGIN_ERROR=登录失败，用户名 {0} 的密码错误。

LOGIN_ERROR=Login failed. The password of user name [%s] is incorrect.
```

**② 国际化配置**

```java
/**
 * 国际化配置
 */
@Configuration
public class I18nConfig {

    /**
     * 系统国际化文件配置
     * @return MessageSource
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        return messageSource;
    }

}
```

**③ 使用MsgUtils获取国际化信息**

```java
MsgUtils.getMessage(i18nCode, args);
```

**④ 修改异常枚举，使其支持国际化异常**

```java
@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements ErrorWrapper {

    //定义一个登录异常
    LOGIN_ERROR(20001, "登录失败，用户名 【{0}】 的密码错误。"),

    ;


    /**
     * 错误码
     */
    private final int code;


    /**
     * 错误信息
     */
    private final String message;


    /**
     * 获取国际化的错误消息
     * @param args 错误消息参数
     * @return 个性化的错误消息
     */
    @Override
    public String getMessage(Object... args) {
        try {
            return MsgUtils.getMessage(name(), args);
        } catch (NoSuchMessageException e) {
            //如果没有找到国际化的错误消息，就使用默认的错误消息
            return MessageFormat.format(message, args);
        }
    }

    /**
     * 抛出指定异常
     * @param args 异常参数
     * @return ApplicationException
     */
    @Override
    public ApplicationException exception(Object... args) {
        return new ApplicationException(code, getMessage(args));
    }

    /**
     * 返回标准的失败响应 R
     * @return R
     */
    @Override
    public R response() {
        return R.failed(code, getMessage());
    }

}
```

# 08.参数验证

在实际的开发中，我们需要对参数的有效性和合法性进行验证，防止恶意攻击和误操作。参数验证是一个非常重要的环节，可以有效地保证系统的安全性和稳定性。

## 原生@Validated校验数据

**添加依赖**

```xml
<!-- 参数验证 -->
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
</dependency>
```

 **参数验证支持的注解**

| 注解名称                   | 功能                                                         |
| -------------------------- | ------------------------------------------------------------ |
| @Null                      | 检查该字段为空                                               |
| @NotNull                   | 不能为null                                                   |
| @NotBlank                  | 不能为空，常用于检查空字符串                                 |
| @NotEmpty                  | 不能为空，多用于检测list是否size是0                          |
| @Max                       | 该字段的值只能小于或等于该值                                 |
| @Min                       | 该字段的值只能大于或等于该值                                 |
| @Past                      | 检查该字段的日期是在过去                                     |
| @Future                    | 检查该字段的日期是否是属于将来的日期                         |
| @Digits                    | 检查数字是否符合指定的精度和范围                             |
| @Email                     | 检查是否是一个有效的email地址                                |
| @Pattern(regex=,flag=)     | 被注释的元素必须符合指定的正则表达式                         |
| @Range(min=,max=,message=) | 被注释的元素必须在合适的范围内                               |
| @Size(min=, max=)          | 检查该字段的size是否在min和max之间，可以是字符串、数组、集合、Map等 |
| @Length(min=,max=)         | 检查所属的字段的长度是否在min和max之间,只能用于字符串        |
| @AssertTrue                | 用于boolean字段，该字段只能为true                            |
| @AssertFalse               | 用于boolean字段，该字段只能为false                           |

**数据校验使用**

在SpringBoot中使用参数验证非常简单，只需要在需要验证参数的实体类中添加相应的注解即可，如下所示：

* `@Null`/`@NotNull`/`@NotBlank`/`@NotEmpty`

```java
@Data
public class User {
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotNull(message = "头像不能为空")
    @NotEmpty(message = "头像不能为空")
    private byte[] avatar;
}
```

* `@Max`/`@Min`

```java
@Data
public class User {
    @Min(value = 18, message = "年龄不能小于{value}")
    @Max(value = 100, message = "年龄不能大于{value}")
    private Integer age;
}
```

* `@Past`/`@Future`

```java
@Data
public class User {

    @Past(message = "生日必须是一个过去的时间")
    private Date birthday;

    @Future(message = "到期日必须是一个将来的时间")
    private Date expiry;

}
```

* `@Digits`

```java
@Data
public class User {
    @Digits(integer = 3, fraction = 2, message = "工资格式不正确")
    private BigDecimal salary;
}
```

* `@Email`

```java
@Data
public class User {
    @Email(message = "请输入正确的邮箱")
    private String email;
}
```

* `@Pattern`

```java
@Data
public class User {
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "请填写符合规范的密码")
    private String password;
}
```

* `@Range`

```java
@Data
public class User {
    @Range(min = 50, max = 200, message = "请输入合法的身高")
    private Integer height;
}
```

* `@Size`

```java
@Data
public class User {
    @Size(min = 6, max = 20, message = "用户名长度必须在{min}-{max}之间")
    private String username;
}
```

* `@Length`

```java
@Data
public class User {
    @Length(min = 6, max = 20, message = "用户名长度必须在{min}-{max}之间")
    private String username;
}
```

* `@AssertTrue`/`@AssertFalse`

```java
@Data
public class User {
    @AssertTrue(message = "已满18周岁")
    private boolean adult;

    @AssertFalse(message = "不能是管理员")
    private boolean isAdmin;
}
```

## 自定义注解校验

使用原生的`@Validated`进行参数校验时，都是特定的注解去校验（例如字段长度、大小、不为空等），我们也可以用自定义的注解去进行参数校验，进行自定义预处理。

**自定义校验注解**

我们可以自定义参数校验注解来扩展SpringBoot的默认参数校验注解。

比如，我们要校验用户输入的日期是否为本月的工作日，可以自定义注解`@Workday`。

首先，我们定义一个实现了`ConstraintValidator`接口的类，该类实现了自定义注解的具体校验逻辑。该类必须实现以下两个方法：

* `initialize()`：在该方法中可以使用注解中的属性对校验器进行初始化。

* `isValid()`：在该方法中实现校验逻辑，返回true表示校验通过，返回false表示校验失败。

```java
// 自定义校验器
public class WorkdayValidator implements ConstraintValidator<Workday, LocalDate> {

    @Override
    public void initialize(Workday constraintAnnotation) {}

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        // 判断是否为工作日
        ...
    }
}

```

然后，我们定义一个自定义注解`@Workday`，用于标记需要校验的参数。在自定义注解中，我们可以定义需要校验的属性的名称、默认校验消息等。

```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {WorkdayValidator.class})
public @interface Workday {

    String message() default "Must be a workday";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

```

在该注解中，我们使用了`@Constraint`注解，该注解表示该注解需要使用自定义的校验器进行校验。

然后在需要进行校验的参数上使用@Workday注解即可。

```java
@Data
public class ExampleDto {
    @Workday
    private LocalDate date;
}
```

**自定义校验器的组合**

有时候我们需要使用多个校验规则进行校验，这时候可以使用Spring的多个校验注解进行组合。

例如，我们需要校验用户名在3到10个字符之间，并且不能包含特殊字符，可以使用如下注解组合：

```java
@Size(min=3, max=10, message="用户名长度必须为3到10个字符")
@Pattern(regexp="[a-zA-Z0-9]*", message="用户名只能使用字母和数字")
```

同时，我们可以自定义一个注解，该注解包含了多个校验规则。使用时只需要标记需要校验的参数即可。例如：

```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Size(min = 3, max = 10, message = "用户名长度必须为3到10个字符")
@Pattern(regexp = "[a-zA-Z0-9]*", message = "用户名只能使用字母和数字")
public @interface UserName {

    String message() default "Invalid user name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

然后在需要进行校验的参数上使用@UserName注解即可。

```java
public class ExampleDto {

    @UserName
    private String userName;

    // getter and setter
}
```

最后在使用@Validated校验参数时，Spring会自动使用该注解中标记的多个校验规则进行校验。我们只需要在参数上添加@Validated注解即可。

## 自定义参数预处理器

有时候我们需要在校验之前对参数进行一些自定义的预处理，例如，需要对参数进行去空格、需要对参数进行特殊字符过滤等。可以使用Spring的自定义参数预处理器实现该功能。

首先，创建MyRequest类，用来封装请求参数

```java
public class MyRequest {
    private String name;
    private int age;
    // 省略getter/setter方法
}
```

然后，定义一个实现了`HandlerMethodArgumentResolver`接口的参数处理器类。在该类中实现预处理逻辑，在最后返回处理后的参数即可。

```java
public class CustomArgumentResolver implements HandlerMethodArgumentResolver {

    // 判断是否需要处理该参数...
    @Override
    public boolean supportsParameter(MethodParameter parameter) { 
        return parameter.getParameterType().equals(MyRequest.class);
    }

    // 进行参数预处理...
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 创建MyRequest对象
        MyRequest request = new MyRequest();
        // 获取请求参数name
        String name = webRequest.getParameter("name");
        if (StringUtils.isNotEmpty(name)) {
            // 对请求参数进行解密等处理
            name = decrypt(name);
            // 对MyRequest对象赋值
            request.setName(name);
        }
        
        // 获取请求参数age
        String age = webRequest.getParameter("age");
        if (StringUtils.isNotEmpty(age)) {
            // 将请求参数转换为int类型
            request.setAge(Integer.parseInt(age));
        }
        return request;
    }
    
    private String decrypt(String input) {
        // 解密方法
        return input;
    }
}
```

然后在配置类中添加自定义参数预处理器：

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

     // 添加自定义参数预处理器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CustomArgumentResolver());
    }
}
```

最后，在控制器中使用该参数即可：

```java
@RestController
public class MyController {

    @GetMapping("/myUrl")
    public String handleRequest(MyRequest request) {
        // 处理请求参数
        String name = request.getName();
        int age = request.getAge();
        // ...
        return "name: " + name +", age: " + age;
    }
}
```

在处理该请求时，Spring会自动调用我们实现的参数预处理器。

```json
GET /myUrl?name=abc&age=25
```

请求参数会被MyArgumentResolver预处理器所处理：

- name参数首先经过解密处理，然后赋值给MyRequest对象的name属性；
- age参数被转换成int类型，然后赋值给MyRequest对象的age属性。

最终，在Controller方法中可以直接使用MyRequest对象，而不用再处理请求参数。





























