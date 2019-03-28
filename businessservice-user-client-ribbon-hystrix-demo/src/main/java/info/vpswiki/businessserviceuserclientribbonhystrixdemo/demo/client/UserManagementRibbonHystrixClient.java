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
            //@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")},fallbackMethod="listUsersByRibbonFallback")
    public String listUsersByRibbon(){
        String result = this.restTemplate.getForObject("http://service-user/listUsers", String.class);
        return result;
    }

    public String listUsersByRibbonFallback(){
        return "listUsersByRibbon异常，端口：" + port;
    }
}
