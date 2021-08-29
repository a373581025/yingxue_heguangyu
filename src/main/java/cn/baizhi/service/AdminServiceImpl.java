package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao ad;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> login(String name, String password) {
        Map<String, Object> map = new HashMap<>();
        Admin admin = (Admin) ad.selectOneByName(name);
        if(admin != null){
            //有这个用户
            if(admin.getPassword().equals(password)){
                //登陆成功
                map.put("flag",true);
                map.put("admin",admin);
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
