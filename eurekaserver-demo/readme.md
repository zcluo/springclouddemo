# 服务发现
##module 
> eurekaserver-demo
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
## EurekaserverDemoApplication.java
```java
package info.vpswiki.eurekaserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaserverDemoApplication.class, args);
    }

}
```
## bootstrap.yml
```yaml
server:
  port: 8761
spring:
  application:
    name: service-registry
eureka:
  instance:
    prefer-ip-address: true
  client:
    fetch-registry: false
    register-with-eureka: false
  server:
    wait-time-in-ms-when-sync-empty: 0

```
## docker maven插件
#### pom.xml依赖配置
```xml
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.1.0</version>
                <configuration>
                    <!-- 注意imageName要符合正则[a-z0-9-_.]的，否则构建不会成功 -->
                    <imageName>commonservice-eureka</imageName>
                    <baseImage>java</baseImage>
                    <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
#### maven构建命令
```cmd
mvn clean package docker:build -DskipTests
```
#### windows 10下的docker运行注意事项
>- Expose daemon on tcp://localhost:2375 without tls 必须勾选
>- 遇到java基础镜像下载不下来需科学上网，而且需要再cmd下docker login重新登陆docker hub

