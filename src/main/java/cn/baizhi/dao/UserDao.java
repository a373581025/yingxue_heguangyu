package cn.baizhi.dao;


import cn.baizhi.vo.UserMonthVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao<User,String> extends Bean {
    //分页查
    List<User> selectByPage(@Param("start")int start,@Param("end")int end);

    //查总条数
    int queryPageNum();

    //修改状态
    void updateStauts(@Param("id")String id,@Param("status")int status);

    //用户信息导出
    List<User> selectPoi();

    //提供用户分析
    List<UserMonthVo> selectByMonth(String sex);
}
