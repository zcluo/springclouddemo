# 带熔断服务消费者ribbon
>本章节介绍服务消费者者，并通过ribbon调用，带有熔断功能
## module 
> businessservice-user-client-ribbon-hystrix-demo
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
              <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
          </dependency>
  <!--
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
          </dependency>
          -->
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
## BusinessserviceUserClientRibbonHystrixDemoApplication.java
```java
package info.vpswiki.businessserviceuserclientribbonhystrixdemo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class BusinessserviceUserClientRibbonHystrixDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(BusinessserviceUserClientRibbonHystrixDemoApplication.class, args);
  }

  @Bean
  @LoadBalanced
  RestTemplate restTemplate(){
    return new RestTemplate();
  }
}

```
> 使用@EnableHystrix 注解声明开启熔断
## UserManagementRibbonHystrixClient.java
```java
package info.vpswiki.businessserviceuserclientribbonhystrixdemo.demo.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName UserManagementRibbonHystrixClient
 * @Description 增加断路器
 * @Author zcluo
 * @Date 2019-3-28 20:36
 * @Version 1.0
 **/
@RestController
public class UserManagementRibbonHystrixClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${server.port}")
    String port;

    @GetMapping("/listUsersByRibbonHystrix")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  // 启动熔断
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "20")},fallbackMethod="listUsersByRibbonFallback")
    public String listUsersByRibbon(){
        String result = this.restTemplate.getForObject("http://service-user/listUsers", String.class);
        return result;
    }

    public String listUsersByRibbonFallback(){
        return "listUsersByRibbon异常，端口：" + port;
    }
}


```

## bootstrap.yml
```yaml
spring:
  application:
    name: service-user-client-ribbon-hystrix
server:
  port: 8701

# 端点管理 hystrixDashboard
management:
  endpoints:
    web:
      exposure:
        include: "*"
```
