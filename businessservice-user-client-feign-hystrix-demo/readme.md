# 带熔断服务消费者feign
>本章节介绍服务消费者者，并通过feign调用，带有熔断功能
## module 
> businessservice-user-client-feign-hystrix-demo
## pom.xml
```xml
  <dependencies>
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
         </dependency>
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
         </dependency>
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-openfeign</artifactId>
         </dependency>
 
         <!--监控中心-->
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-actuator</artifactId>
         </dependency>
 
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-test</artifactId>
             <scope>test</scope>
         </dependency>
     </dependencies>
```
## BusinessserviceUserClientFeignHystrixDemoApplication.java
```java
package info.vpswiki.businessserviceuserclientfeignhystrixdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker //必須要不然出不來dashboard
public class BusinessserviceUserClientFeignHystrixDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(BusinessserviceUserClientFeignHystrixDemoApplication.class, args);
  }
}

```
> 使用@EnableCircuitBreaker 注解声明开启熔断
## UserFeignHystrixClient.java
```java
package info.vpswiki.businessserviceuserclientfeignhystrixdemo.client;

import info.vpswiki.businessserviceuserclientfeignhystrixdemo.client.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="service-user", fallback= UserFallback.class)
public interface UserFeignHystrixClient {
    @GetMapping("/listUsers")
    public String listUsers();
}

```
## UserFeignApi.java
```java
package info.vpswiki.businessserviceuserclientfeignhystrixdemo.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName UserFeignApi
 * @Description fergn的断路调用
 * @Author zcluo
 * @Date 2019-3-29 20:07
 * @Version 1.0
 **/
@RestController
public class UserFeignApi {

    @Autowired
    private UserFeignHystrixClient userFeignHystrixClient;

    @GetMapping("/listUsersByFeignHystrix")
    public String ListUsers(){
        String users = userFeignHystrixClient.listUsers();
        return users;
    }
}

```
## UserFallback.java
```java
package info.vpswiki.businessserviceuserclientfeignhystrixdemo.client.fallback;

import info.vpswiki.businessserviceuserclientfeignhystrixdemo.client.UserFeignHystrixClient;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserFallback
 * @Description 断路器失败回调
 * @Author zcluo
 * @Date 2019-3-29 20:11
 * @Version 1.0
 **/
@Component
public class UserFallback implements UserFeignHystrixClient {
    @Override
    public String listUsers() {

        return "服务调用失败";
    }
}
```
## bootstrap.yml
```yaml
spring:
  application:
    name: service-user-client-feign-hystrix
server:
  port: 9101
management:
  endpoints:
    web:
      exposure:
        include: "*"
feign:
  hystrix:
    enabled: true
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 1000
      circuitBreaker:
        requestVolumeThreshold: 10
        sleepWindowInMilliseconds: 10000
        errorThresholdPercentage: 20
```
