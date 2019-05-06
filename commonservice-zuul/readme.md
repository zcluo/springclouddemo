# 服务网关
>本章节介绍服务网关
## module 
> commonservice-zuul
## pom.xml
```xml
  <dependencies>
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-config</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
          </dependency>
  
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
              <scope>test</scope>
          </dependency>
      </dependencies>
```
## CommonserviceZuulApplication.java
```java
package info.vpswiki.commonservicezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class CommonserviceZuulApplication {

  public static void main(String[] args) {
    SpringApplication.run(CommonserviceZuulApplication.class, args);
  }
}


```

## UserFallbackProvider.java
```java
package info.vpswiki.commonservicezuul.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName UserFallbackProvider
 * @Description fallbackprovider
 * @Author zcluo
 * @Date 2019-4-8 20:12
 * @Version 1.0
 **/
@Component
public class UserFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "service-user";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("fallback".getBytes());
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public void close() {

            }
        };
    }
}

```

## bootstrap.yml
```yaml
spring:
  application:
    name: service-zuul
  cloud:
    config:
      discovery:
        enabled: true
        service-id: service-config
      label: master
      profile: dev

```
## service-zuul-dev.yml
```yaml
server:
  port: 1100
zuul:
  ignoredServices: '*' #忽略所有未配置的service
  host:
    connect-timeout-millis: 20000
    socket-timeout-millis: 20000
  routes:
    user-service: #自定义名称
      path: /user/**
      serviceId: service-user #/user/开头的路径转发至service-user微服务
hystrix: #hystrix配置
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 2500
ribbon: #ribbon负载均衡参数配置
  ReadTimeout: 5000
  ConnectTimeout: 5000
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```
