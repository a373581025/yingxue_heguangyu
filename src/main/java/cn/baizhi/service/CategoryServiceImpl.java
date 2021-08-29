package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao cd;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByLevels(int levels) {
        List<Category> list = cd.selectByLevels(levels);
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByParent(int parentId) {
        List<Category> list = cd.selectByParend(parentId);
        return list;
    }

    @DeleteCache
    @Override
    public Map<String, Integer> save(Category category) {
        Map<String, Integer> map = new HashMap<>();
        category.setId(UUID.randomUUID().toString());
        Category category1 = (Category) cd.selectByName(category.getCateName());
        if (category1 !=null){
            map.put("msg",0);
            return map;
        }else {
            Integer integer = cd.insert(category);
            map.put("msg",1);
            return map;
        }

    }

    @DeleteCache
    @Override
    public Map<String, Object> remove(String id) {
        Map<String, Object> map = new HashMap<>();
        Category category = (Category) cd.selectById(id);
        Integer levels = category.getLevels();
        if (levels == 1){
            List<Category> list = cd.selectByParend(levels);
            if (list.size() == 0){
                cd.delete(id);
                map.put("flag",true);
            }else {
                map.put("flag",false);
                map.put("msg","无法删除，因为内部存在二级类别");
            }
        }else {
            cd.delete(id);
            map.put("flag",true);
        }
        return map;
    }
}
