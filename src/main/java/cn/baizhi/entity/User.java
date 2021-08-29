package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    @Excel(name = "id")
    private String userid;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "姓名")
    private String username;
    @Excel(name = "头像",type = 2)
    private String headimg;
    @Excel(name = "描述")
    private String brief;
    @Excel(name = "微信")
    private String wechat;
    @Excel(name = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdate;
    private Integer status;
}
