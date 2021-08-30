package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao ad;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> login(String name, String password, HttpServletRequest request) {
        System.out.println(request.getSession(true).getId());
        Map<String, Object> map = new HashMap<>();
        Admin admin = (Admin) ad.selectOneByName(name);
        if(admin != null){
            //有这个用户
            if(admin.getPassword().equals(password)){
                //登陆成功
                map.put("flag",true);
                map.put("admin",admin);
                ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
                String id = request.getSession(true).getId();
                stringStringValueOperations.set(id, JSONObject.toJSONString(admin),30, TimeUnit.MINUTES);
                map.put("token",id);
            }else {
                //密码错误
                map.put("flag",false);
                map.put("msg","密码错误");
            }
        }else {
            //没有这个用户
            map.put("msg","用户名不存在");
        }
        return map;
    }

    @Override
    public void addAdmin(Admin admin) {
        admin.setAdminid(UUID.randomUUID().toString());
        ad.insert(admin);
    }
}
