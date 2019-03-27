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
