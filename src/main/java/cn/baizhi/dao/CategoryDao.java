package cn.baizhi.dao;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao<Category,String> extends Bean {
    //根据级别查所有
    List<Category> selectByLevels(int levels);

    //根据父项id查询所有的二级类别
    List<Category> selectByParend(int parentId);

    //根据父项id添加二级类别
    Integer insert(Object category);

    //根据name查一个
    Category selectByName(String name);

    //根据id查这条数据的信息
    Category selectById(String id);
}
