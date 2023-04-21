package com.liuxiaolan.apipassenger.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /** 方法描述 将 编写的token的拦截器 加配置
    * @return
    * @author liuxiaolan
    * @date 2023/4/20
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( new JwtInterceptor())
        //拦截的路径
                .addPathPatterns("/**")
        //不拦截的路径   noauthTest 测试的路径  控制这个请求不判断token是否正确有效
                .excludePathPatterns("/noauthTest");
    }
}
