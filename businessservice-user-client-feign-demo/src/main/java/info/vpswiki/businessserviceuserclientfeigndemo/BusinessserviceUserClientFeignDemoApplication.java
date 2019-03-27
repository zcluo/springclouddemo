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
