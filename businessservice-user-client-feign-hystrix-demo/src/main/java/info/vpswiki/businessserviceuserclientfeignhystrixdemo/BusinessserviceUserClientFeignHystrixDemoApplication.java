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
