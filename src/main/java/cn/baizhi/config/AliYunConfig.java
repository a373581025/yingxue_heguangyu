package cn.baizhi.config;

public interface AliYunConfig {
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    String ACCESSKEYID = "LTAI5tHi28B2NsftKJ86J4M8";
    String ACCESSKEYSECRET = "gU2q2mX3rLgUXOyXCfv60noG8Folwy";
    String BUCKETNAME = "2021updown";  //存储空间名
}
