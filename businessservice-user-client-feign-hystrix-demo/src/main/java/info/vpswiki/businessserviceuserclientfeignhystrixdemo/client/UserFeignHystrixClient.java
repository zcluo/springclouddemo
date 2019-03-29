package info.vpswiki.businessserviceuserclientfeignhystrixdemo.client;

import info.vpswiki.businessserviceuserclientfeignhystrixdemo.client.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="service-user", fallback= UserFallback.class)
public interface UserFeignHystrixClient {
    @GetMapping("/listUsers")
    public String listUsers();
}
