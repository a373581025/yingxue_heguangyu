package cn.baizhi.service;


import cn.baizhi.entity.Video;
import cn.baizhi.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    Map<String,Object> queryByPage(int page, int size);

    void save(MultipartFile file, Video video);

    void remove(String id);

    Video queryById(String id);

    List<VideoVo> queryByCreateDate();
}
