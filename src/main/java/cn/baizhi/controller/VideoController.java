package cn.baizhi.controller;


import cn.baizhi.entity.Category;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/video")
public class VideoController {
    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService vs;

    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page) {
//        System.out.println(page);
        int size = 2;
        Map<String, Object> map = vs.queryByPage(page, size);
        return map;
    }

    @RequestMapping("/save")
    public void save(MultipartFile video, String title, String brief, String categoryid) {
//        log.debug("执行了");
//        System.out.println(video.getOriginalFilename());
//        log.debug(title);
//        log.debug(brief);
//        log.debug(categoryid);
        Video video1 = new Video(null, title, brief, null, null, null, new Category(categoryid, null, null, null),
                null, null);
        vs.save(video, video1);
    }

    @RequestMapping("/remove")
    public void save(String videoId) {
        log.debug("执行了");
        vs.remove(videoId);
    }

    @RequestMapping("/findOne")
    public Video findOne(String videoId) {
        log.debug("执行了");
        log.debug(videoId);
        Video video = vs.queryById(videoId);
        return video;

    }

    @RequestMapping("/chenge")
    public void chenge(MultipartFile video, String videoId, String title, String brief, String categoryid) {
//        log.debug("执行了");
//        log.debug(videoId);
//        log.debug(title);
//        log.debug(brief);
//        log.debug(categoryid);
//        log.debug(video.getOriginalFilename());
        Video video1 = new Video(videoId, title, brief, null, null, null, new Category(categoryid, null, null, null),
                null, null);
        vs.save(video, video1);
    }
}
