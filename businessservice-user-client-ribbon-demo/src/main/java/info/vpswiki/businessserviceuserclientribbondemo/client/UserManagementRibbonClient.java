package info.vpswiki.businessserviceuserclientribbondemo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName UserManagementRibbonClient
 * @Description 负载均衡Ribbon客户端
 * @Author zcluo
 * @Date 2019-3-26 20:42
 * @Version 1.0
 **/
@RestController
public class UserManagementRibbonClient {

    @Autowired
    RestTemplate restTemplate;

    /*
     * @Author zcluo
     * @Description 通过Ribbon负载均衡调用listUsers服务
     * @Date 20:46 2019-3-26
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/listUsersByRibbon")
    public String ListUsersByRibbon(){
        String result =
        this.restTemplate.getForObject("http://service-user/listUsers", String.class);


        return result;
    }
}
