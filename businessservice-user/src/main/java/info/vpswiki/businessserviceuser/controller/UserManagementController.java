package info.vpswiki.businessserviceuser.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class UserManagementController {
    @Value("${server.port}")
    String serverPort;

    @Value("${server.time}")
    String testValue;

    /*
     * @Author zcluo
     * @Description 模拟从数据库返回用户列表
     * @Date 20:16 2019-3-26
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/listUsers")
    public String ListUsers()
    {


        try {
            Thread.sleep(200);
            List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
            for(int i=1; i< 5; i++){
                Map<String, Object> user = new HashMap<String, Object>();
                user.put("id", i);
                user.put("name", "小明" + i);
                users.add(user);
            }
            return "服务器端口号：   " + serverPort + "   |   用户信息：   " + users.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    return "服务器端口号：   " + serverPort + " Exception occured!";
    }

    @GetMapping("/getTestValue")
    public String getTestValue(){
        return testValue;
    }

}
