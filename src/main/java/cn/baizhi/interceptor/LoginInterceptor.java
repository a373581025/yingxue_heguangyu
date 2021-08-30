package cn.baizhi.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("放行了");
        String token = request.getHeader("token");
        System.out.println(token);
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }else {
            throw new Exception();
        }

    }
}
