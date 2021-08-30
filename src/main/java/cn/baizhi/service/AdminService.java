package cn.baizhi.service;


import cn.baizhi.entity.Admin;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public interface AdminService {
    //登陆
    Map<String,Object> login(String name, String password, HttpServletRequest request);

    //注册
    void addAdmin(Admin admin);
}
