# 服务发现客户端
## module 
> eurekaclient-demo
## pom.xml
```xml
 <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
## EurekaclientDemoApplication.java
```java
package info.vpswiki.eurekaclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaclientDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(EurekaclientDemoApplication.class, args);
  }
}
```
## bootstrap.yml
```yaml
spring:
  application:
    name: service-eureka-client
server:
  port: 8800

```


