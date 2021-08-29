package cn.baizhi.dao;


import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao<Video,String> extends Bean {
    //分页查所有
    List<Video> selectByPage(@Param("start")int start, @Param("end")int end);

    //查总条数
    int selectCount();

    void save(Video video);
}
