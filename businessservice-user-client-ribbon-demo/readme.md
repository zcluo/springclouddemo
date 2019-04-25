# 服务消费者ribbon
>本章节介绍服务消费者，通过ribbon实现客户端的负载均衡
## module 
> businessservice-user-client-ribbon-demo
## pom.xml
```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
## BusinessserviceUserClientRibbonDemoApplication.java
```java
package info.vpswiki.businessserviceuserclientribbondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class BusinessserviceUserClientRibbonDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(BusinessserviceUserClientRibbonDemoApplication.class, args);
  }

  @Bean
  @LoadBalanced
  RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
```
> 使用@LoadBalanced 注解声明开启 负载均衡
## UserManagementRibbonClient.java
```java
package info.vpswiki.businessserviceuserclientribbondemo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName UserManagementRibbonClient
 * @Description 负载均衡Ribbon客户端
 * @Author zcluo
 * @Date 2019-3-26 20:42
 * @Version 1.0
 **/
@RestController
public class UserManagementRibbonClient {

    @Autowired
    RestTemplate restTemplate;

    /*
     * @Author zcluo
     * @Description 通过Ribbon负载均衡调用listUsers服务
     * @Date 20:46 2019-3-26
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/listUsersByRibbon")
    public String ListUsersByRibbon(){
        String result =
        this.restTemplate.getForObject("http://service-user/listUsers", String.class);
        return result;
    }
}

```
> 通过RestTemplate实例去调用服务
