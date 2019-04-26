# 服务消费者feign
>本章节介绍服务消费者者，并通过feign调用
## module 
> businessservice-user-client-feign-demo
## pom.xml
```xml
 <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
## BusinessserviceUserClientFeignDemoApplication.java
```java
package info.vpswiki.businessserviceuserclientfeigndemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BusinessserviceUserClientFeignDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(BusinessserviceUserClientFeignDemoApplication.class, args);
  }
}
```
> 使用@EnableFeignClients 注解声明开启feign客户端
## UserFeignClient.java
```java
package info.vpswiki.businessserviceuserclientfeigndemo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="service-user")
public interface UserFeignClient {
    @GetMapping("/listUsers")
    public String listUsers();
}


```
## UserFeignApi.java
```java
package info.vpswiki.businessserviceuserclientfeigndemo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserFeignApi
 * @Description Feign客户端API
 * @Author zcluo
 * @Date 2019-3-27 14:28
 * @Version 1.0
 **/
@RestController
public class UserFeignApi {
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/listUsersByFeign")
    public String ListUsers(){
        String users = userFeignClient.listUsers();
        return users;
    }
}

```
