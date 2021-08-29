package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.entity.Video;
import cn.baizhi.util.OssUtil;
import cn.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao vd;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String, Object> map = new HashMap<>();
        List<Video> list = vd.selectByPage((page - 1) * size, size);
        map.put("data",list);
        map.put("page",page);
        int countNum = vd.selectCount();
        int pageNum = countNum % size == 0 ? countNum / size : countNum / size +1;
        map.put("pageNum",pageNum);
        return map;
    }

    @DeleteCache
    @Override
    public void save(MultipartFile file, Video video){
        if (video.getVideoId() == null){
            video.setVideoId(UUID.randomUUID().toString());
            video.setCreateDate(new Date());
            //上传文件改名
            String fileName = "video/" + UUID.randomUUID().toString().replace("-","");
            //上传文件名
            String uploadName = file.getOriginalFilename();
            //文件名后缀
            String substring = uploadName.substring(uploadName.lastIndexOf("."));
            String finalName = fileName + substring;
            video.setVideoPath("http://2021updown.oss-cn-beijing.aliyuncs.com/"+finalName);
            video.setCoverPath("http://2021updown.oss-cn-beijing.aliyuncs.com/"+fileName+".jpg");
            System.out.println(finalName);

            OssUtil.uploadByBytes(file,finalName);
            OssUtil.video(finalName);
            vd.save(video);
        }else {
            String videoId = video.getVideoId();
            this.remove(videoId);
            video.setCreateDate(new Date());
            //上传文件改名
            String fileName = "video/" + UUID.randomUUID().toString().replace("-","");
            //上传文件名
            String uploadName = file.getOriginalFilename();
            //文件名后缀
            String substring = uploadName.substring(uploadName.lastIndexOf("."));
            String finalName = fileName + substring;
            video.setVideoPath("http://2021updown.oss-cn-beijing.aliyuncs.com/"+finalName);
            video.setCoverPath("http://2021updown.oss-cn-beijing.aliyuncs.com/"+fileName+".jpg");
            System.out.println(finalName);

            OssUtil.uploadByBytes(file,finalName);
            OssUtil.video(finalName);
            vd.save(video);
        }
    }

    @DeleteCache
    @Override
    public void remove(String id) {
        Video video = (Video) vd.selectOneById(id);

        int i = video.getVideoPath().lastIndexOf("/");
        String videoPath = "video/"+video.getVideoPath().substring(i + 1);
        int i2 = video.getCoverPath().lastIndexOf("/");
        String coverPath = "video/"+video.getCoverPath().substring(i + 1);
        OssUtil.removeById(videoPath);
        OssUtil.removeById(coverPath);
        vd.delete(id);
    }

    //根据id查一个
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Video queryById(String id){
        Video video = (Video) vd.selectOneById(id);
        return video;
    }

    @Autowired
    private VideoVoDao vvd;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoVo> queryByCreateDate() {
        List<VideoVo> videoVos = vvd.queryAllVideoVo();
        return videoVos;
    }
}
