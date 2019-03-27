package info.vpswiki.businessserviceuserclientfeigndemo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserFeignApi
 * @Description Feign客户端API
 * @Author zcluo
 * @Date 2019-3-27 14:28
 * @Version 1.0
 **/
@RestController
public class UserFeignApi {
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/listUsersByFeign")
    public String ListUsers(){
        String users = userFeignClient.listUsers();
        return users;
    }
}
