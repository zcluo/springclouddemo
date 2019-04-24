# 服务提供者
>本章节介绍服务提供者，并介绍与rabbitmq整合动态加载刷新配置变量
## module 
> businessservice-user
## pom.xml
```xml
 <dependencies>
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
         </dependency>
 
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-config</artifactId>
         </dependency>
 
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
         <!-- 监控微服务自身信息 -->
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-actuator</artifactId>
         </dependency>
         <!-- 集成rocketmq 用于配置动态加载 -->
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-bus-amqp</artifactId>
         </dependency>
 
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-test</artifactId>
             <scope>test</scope>
         </dependency>
     </dependencies>
```
## BusinessserviceUserApplication.java
>- 服务启动类
```java
package info.vpswiki.businessserviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BusinessserviceUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(BusinessserviceUserApplication.class, args);
  }
}

```
>- 服务暴露
```java
package info.vpswiki.businessserviceuser.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class UserManagementController {
    @Value("${server.port}")
    String serverPort;

    @Value("${server.time}")
    String testValue;

    /*
     * @Author zcluo
     * @Description 模拟从数据库返回用户列表
     * @Date 20:16 2019-3-26
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/listUsers")
    public String ListUsers()
    {


        try {
            Thread.sleep(500);
            List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
            for(int i=1; i< 5; i++){
                Map<String, Object> user = new HashMap<String, Object>();
                user.put("id", i);
                user.put("name", "小明" + i);
                users.add(user);
            }
            return "服务器端口号：   " + serverPort + "   |   用户信息：   " + users.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    return "服务器端口号：   " + serverPort + " Exception occured!";
    }

    @GetMapping("/getTestValue")
    public String getTestValue(){
        return testValue;
    }

}

```
>其中@RefreshScope注解用于动态刷新配置变量
## bootstrap.yml
```yaml
spring:
  application:
    name: service-user
  #配置中心
  #service-id ： 指向配置中心的 微服务名，这样就实现了高可用
  #profile: 此配置为了方便 开发、测试、线上环境的配置文件的切换 该属性和配置中心的配置文件后缀相关
  #配置中心中配置文件的名称需要遵守service-user的application.name + 中划线 + profile 约定，否则无法读取
  cloud:
    config:
      discovery:
        enabled: true
        service-id: service-config
      profile: dev
      label: master
      uri: http://localhost:8877
  #rabbitmq集成
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
management:
  endpoints:
    web:
      exposure:
        include: "*"
      cors:
        allowed-origins: "*"
        allowed-methods: "*"

```
> 测试环境为了多开可以配置多个bootstrap-dev[n].yml 然后在idea中设置允许并行运行，可用来模拟服务集群。示例如下：
```yaml
spring:
  application:
    name: service-user
  cloud:
    config:
      discovery:
        enabled: true
        service-id: service-config
      profile: dev1
      label: master
      uri: http://localhost:8877

  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
management:
  endpoints:
    web:
      exposure:
        include: "*"
      cors:
        allowed-origins: "*"
        allowed-methods: "*"
```
## 配置中心的配置文件
>- service-user-dev.yml
```yaml
server:
  port: 8801
  time: update version200

```
>- service-user-dev1.yml
```yaml
server:
  port: 8802
  time: update version1

```
