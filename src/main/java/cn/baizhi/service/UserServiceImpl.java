package cn.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.OssUtil;
import cn.baizhi.vo.UserMonthVo;
import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao ud;

    //查所有
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> queryAll() {
        List<User> list = ud.selectAll();
        return list;
    }

    //根据id查一个
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryById(String id) {
        User user = (User) ud.selectOneById(id);
        return user;
    }

    @DeleteCache
    @Override
    public void chengeStatus(String id, int status) {
        ud.updateStauts(id, status);
    }

    //分页查所有
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String, Object> map = new HashMap<>();

        List<User> list = ud.selectByPage((page - 1) * size, size);
        map.put("data", list);
        map.put("page", page);
        int countNum = ud.queryPageNum();
        int pageNum = countNum % size == 0 ? countNum / size : countNum / size + 1;
        map.put("pageNum", pageNum);



        return map;
    }

    //添加
    @DeleteCache
    @Override
    public void insert(MultipartFile file, User user) {
        user.setUserid(UUID.randomUUID().toString());
        user.setCreatedate(new Date());
        user.setStatus(0);
        //上传文件改名
        String fileName = UUID.randomUUID().toString().replace("-","");
        //上传文件名
        String uploadName = file.getOriginalFilename();
        //文件名后缀
        String substring = uploadName.substring(uploadName.lastIndexOf("."));
        String finalName = fileName + substring;
        user.setHeadimg("http://2021updown.oss-cn-beijing.aliyuncs.com/"+finalName);
        OssUtil.uploadByBytes(file, finalName);
        ud.insert(user);
        Map<String, Object> map = this.queryUserBySex();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-9f6ea244c92742f0abe6ae8416446d54");
        goEasy.publish("mychannel", JSON.toJSONString(map));
    }

    //修改
    @DeleteCache
    @Override
    public void update(MultipartFile file,User user) {
        User user1 = (User) ud.selectOneById(user.getUserid());
        user.setCreatedate(user1.getCreatedate());
        user.setStatus(user1.getStatus());
        this.deleteById(user.getUserid());
        //上传文件改名
        String fileName = UUID.randomUUID().toString().replace("-","");
        //上传文件名
        String uploadName = file.getOriginalFilename();
        //文件名后缀
        String substring = uploadName.substring(uploadName.lastIndexOf("."));
        String finalName = fileName + substring;
        user.setHeadimg("http://2021updown.oss-cn-beijing.aliyuncs.com/"+finalName);
        OssUtil.uploadByBytes(file, finalName);
        ud.update(user);
    }

    //根据id删除
    @DeleteCache
    @Override
    public void deleteById(String id) {
        User user = (User) ud.selectOneById(id);
        int i = user.getHeadimg().lastIndexOf("/");
        String headimg = user.getHeadimg().substring(i+1);
        OssUtil.removeById(headimg);
        ud.delete(id);
        Map<String, Object> map = this.queryUserBySex();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-9f6ea244c92742f0abe6ae8416446d54");
        goEasy.publish("mychannel", JSON.toJSONString(map));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> updown() {
        List<User> list = ud.selectPoi();
        for (User user : list) {
            String headimg = user.getHeadimg();
            int i = headimg.lastIndexOf("/");
            String substring = headimg.substring(i+1);
            System.out.println(substring);
            OssUtil.download(substring);
            user.setHeadimg("D:\\abc\\"+substring);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","学生"),User.class, list);

        try {
            workbook.write(new FileOutputStream(new File("D:/easypoi.xls")));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryUserBySex() {
        List<String> data = new ArrayList<>();
        List<Integer> manCount = new ArrayList<>();
        List<Integer> womanCount = new ArrayList<>();
        List<UserMonthVo> man = ud.selectByMonth("男");
        List<UserMonthVo> woman = ud.selectByMonth("女");
        for (int i = 0;i<=11;i++){
            data.add(i+1+"月");
            boolean flag = false;
            for (UserMonthVo userMonthVo : man) {
                if(userMonthVo.getMonth() == i){
                    manCount.add(userMonthVo.getCount());
                    flag = true;
                }
            }
            boolean flag2 = false;
            for (UserMonthVo userMonthVo1 : woman) {
                if(userMonthVo1.getMonth() == i){
                    womanCount.add(userMonthVo1.getCount());
                    flag2 = true;
                }
            }
            if(!flag || !flag2){
                manCount.add(0);
                womanCount.add(0);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("manCount",manCount);
        map.put("womanCount",womanCount);
        return map;
    }
}
