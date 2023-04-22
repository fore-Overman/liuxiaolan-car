package com.liuxiaolan.apipassenger.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean  //在初始化拦截器的时候就初始化bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }


    /** 方法描述 将 编写的token的拦截器 加配置
    * @return
    * @author liuxiaolan
    * @date 2023/4/20
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
        //拦截的路径
                .addPathPatterns("/**")
        //不拦截的路径   noauthTest 测试的路径  控制这个请求不判断token是否正确有效
                .excludePathPatterns("/verification-code")
                .excludePathPatterns("/verification-code-check")
                .excludePathPatterns("/token-refresh");
    }
}
