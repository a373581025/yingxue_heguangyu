package cn.baizhi.dao;

import java.util.List;

public interface Bean<T,K> {
    //查所有
    List<T> selectAll();
    //根据id查一下
    T selectOneById(K k);
    //添加
    Integer insert(T t);
    //修改
    void update(T t);
    //删除
    void delete(K k);
}
