package cn.baizhi.yingxue_heguangyu;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.dao.UserDao;
import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.entity.Admin;
import cn.baizhi.entity.Category;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import cn.baizhi.service.AdminService;
import cn.baizhi.service.CategoryService;
import cn.baizhi.service.UserService;
import cn.baizhi.service.VideoService;
import cn.baizhi.util.OssUtil;
import cn.baizhi.vo.UserMonthVo;
import cn.baizhi.vo.VideoVo;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class YingxueHeguangyuApplicationTests {

    @Autowired
    private UserDao ud;

    @Autowired
    private UserService us;
    @Test
    public void test(){
        List<UserMonthVo> man = ud.selectByMonth("男");
        List<Integer> manCount = new ArrayList<>();
        for (int i = 0;i<=11;i++){
            for (UserMonthVo userMonthVo : man) {
                if(userMonthVo.getMonth() == i){
                    manCount.add(userMonthVo.getCount());
                }
            }
        }
        for (Integer integer : manCount) {
            System.out.println(integer);
        }
    }

    @Test
    public void test2(){
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-9f6ea244c92742f0abe6ae8416446d54");
        goEasy.publish("mychannel","Hello,GoEasy");
    }

}
