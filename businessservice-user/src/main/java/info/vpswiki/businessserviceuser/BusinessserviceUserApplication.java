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
