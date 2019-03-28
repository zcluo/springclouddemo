package info.vpswiki.businessserviceuserclientribbonhystrixdemo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
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
