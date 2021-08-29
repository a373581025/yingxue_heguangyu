package cn.baizhi.controller;


import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;



@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {


    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService as;

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody Admin admin){
        log.debug(admin.getUsername());
        log.debug(admin.getPassword());
        Map<String, Object> map = as.login(admin.getUsername(),admin.getPassword());

        return map;
    }
}
