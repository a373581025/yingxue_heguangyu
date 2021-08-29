package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    List<Category> queryByLevels(int levels);

    List<Category> queryByParent(int parentId);

    Map<String, Integer> save(Category category);

    Map<String, Object> remove(String id);
}
