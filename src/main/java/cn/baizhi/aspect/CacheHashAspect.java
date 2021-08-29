package cn.baizhi.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class CacheHashAspect {

    @Autowired
    private RedisTemplate redisTemplate;

   @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){
       System.out.println("进入环绕通知");
       StringBuilder sb = new StringBuilder();
       //获取类的全路径
       String className = joinPoint.getTarget().getClass().getName();
       System.out.println(className);


//        获取方法名
       String methodName = joinPoint.getSignature().getName();
       System.out.println(methodName);

       sb.append(className).append(methodName);

//        实参值
       Object[] args = joinPoint.getArgs();
       for (Object arg : args) {
           System.out.println(arg);
           sb.append(arg);
       }
       System.out.println(sb);
       redisTemplate.setKeySerializer(new StringRedisSerializer());
       redisTemplate.setHashKeySerializer(new StringRedisSerializer());
       HashOperations hashOperations = redisTemplate.opsForHash();

       Object obj = null;
       if (hashOperations.hasKey(className,sb.toString())){
           //如果有这个key
           obj = hashOperations.get(className,sb.toString());
       }else {
           //没有这个key
           try {
               obj = joinPoint.proceed();//放行请求
           } catch (Throwable e) {
               e.printStackTrace();
           }
           hashOperations.put(className,sb.toString(),obj);
       }

//       System.out.println(obj);

       return obj;
   }

    //清除缓存
    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delectCache(JoinPoint joinPoint){
        System.out.println("后置通知");
        //类的全限定名
        String name = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(name);
    }

}
