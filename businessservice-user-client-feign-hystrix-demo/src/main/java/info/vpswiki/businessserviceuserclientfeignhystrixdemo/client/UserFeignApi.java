package info.vpswiki.businessserviceuserclientfeignhystrixdemo.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName UserFeignApi
 * @Description fergn的断路调用
 * @Author zcluo
 * @Date 2019-3-29 20:07
 * @Version 1.0
 **/
@RestController
public class UserFeignApi {





    @Autowired
    private UserFeignHystrixClient userFeignHystrixClient;

    @GetMapping("/listUsersByFeignHystrix")
    public String ListUsers(){
        String users = userFeignHystrixClient.listUsers();
        return users;
    }
}
