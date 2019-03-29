package info.vpswiki.businessserviceuserclientfeignhystrixdemo.client.fallback;

import info.vpswiki.businessserviceuserclientfeignhystrixdemo.client.UserFeignHystrixClient;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserFallback
 * @Description 断路器失败回调
 * @Author zcluo
 * @Date 2019-3-29 20:11
 * @Version 1.0
 **/
@Component
public class UserFallback implements UserFeignHystrixClient {
    @Override
    public String listUsers() {

        return "服务调用失败";
    }
}
