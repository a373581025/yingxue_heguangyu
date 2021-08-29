package cn.baizhi.dao;

import cn.baizhi.entity.Admin;

public interface AdminDao<Admin,String> extends Bean {
    //查一个
    Admin selectOneByName(String name);
}
