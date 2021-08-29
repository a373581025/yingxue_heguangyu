package cn.baizhi.service;

import cn.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface UserService {
    //查所有
    List<User> queryAll();
    //根据id查一个
    User queryById(String id);
    //修改状态
    void chengeStatus(String id,int status);
    //分页查所有
    Map<String,Object> queryByPage(int page,int size);
    //添加
    void insert(MultipartFile file,User user);
    //根据Id修改
    void update(MultipartFile file,User user);
    //根据id删除
    void deleteById(String id);

    //用户信息导出
    List<User> updown();

    //提供用户分析数据
    Map<String, Object> queryUserBySex();
}
