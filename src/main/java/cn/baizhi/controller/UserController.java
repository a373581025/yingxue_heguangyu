package cn.baizhi.controller;

import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import cn.baizhi.util.OssUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import io.goeasy.GoEasy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService us;

    private OssUtil ou;

    //查所有
    @RequestMapping("/findAll")
    public List<User> findAll() {
        List<User> users = us.queryAll();
        return users;
    }

    //分页查所有
    @RequestMapping("findByPage")
    public Map<String, Object> findByPage(int page) {
        int size = 3;
        Map<String, Object> map = us.queryByPage(page, size);
        return map;
    }

    //根据Id查一个
    @RequestMapping("/findOne")
    public User findOne(String userid) {
        User user = us.queryById(userid);
        return user;
    }

    //修改状态
    @RequestMapping("/chengeStauts")
    public void chengeStauts(String id, int status) {
        us.chengeStatus(id, status);
    }


    //根据id删除
    @RequestMapping("/remove")
    public void remove(String userid) {
        User user = us.queryById(userid);
        us.deleteById(userid);
    }

    //添加一个用户
    @RequestMapping("/save")
    public void save(MultipartFile photo,String sex, String username, String phone, String brief){
        User user = new User(null,sex,phone,username,null,brief,null,null,null);
        us.insert(photo,user);
    }

    @RequestMapping("/chenge")
    public void chenge(MultipartFile photo, String userid, String sex,String username, String phone, String brief) {
        log.debug("方法执行了");
//        log.debug(userid);
//        log.debug(username);
//        log.debug(phone);
//        log.debug(brief);
//        log.debug(photo.getOriginalFilename());
        User user = new User(userid,sex, phone, username, null, brief, null, null, null);
        us.update(photo,user);
    }

    @RequestMapping("updown")
    public void updown(){
        us.updown();
    }

    @RequestMapping("/registCount")
    public Map<String, Object> registCount(){
        System.out.println("执行了");
        Map<String, Object> map = us.queryUserBySex();
        return map;
    }

}
