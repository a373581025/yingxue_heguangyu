package cn.baizhi.controller;

import cn.baizhi.service.VideoService;
import cn.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/yingx/app")
public class AppController {

    @Autowired
    private VideoService vs;

    @RequestMapping("queryByReleaseTime")
    public void queryByReleaseTime(){
        List<VideoVo> list = vs.queryByCreateDate();

    }
}
