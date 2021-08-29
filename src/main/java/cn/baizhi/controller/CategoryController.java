package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService cs;

    @RequestMapping("/queryByLevels")
    public List<Category> queryByLevels(int levels){
        List<Category> list = cs.queryByLevels(levels);
        return list;
    }

    @RequestMapping("/queryByParent")
    public List<Category> queryByParent(int parentId){
        List<Category> list = cs.queryByParent(parentId);
        return list;
    }

    @RequestMapping("/save")
    public Map<String, Integer> save(@RequestBody Category category){
        Map<String, Integer> save = cs.save(category);
        return save;
    }

    @RequestMapping("/saveLevel1")
    public Map<String, Integer> saveLevel1(@RequestBody Category category){
        Map<String, Integer> save = cs.save(category);
        return save;
    }

    @RequestMapping("/remove")
    public Map<String, Object> remove(String id){
        Map<String, Object> map = cs.remove(id);
        return map;
    }
}
