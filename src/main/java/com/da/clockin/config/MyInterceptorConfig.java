//开发时间 : 2023/3/14 19:43

package com.da.clockin.config;

import com.da.clockin.intercepter.MyInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyInterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 将上面自定义好的拦截器添加进去。
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/api/login","/api/register","/api/sms","/api/file/**","/api/getsms");
        super.addInterceptors(registry);
    }
    @Value("${file.uClass}")
    private String uClassFile;


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:"+uClassFile);
    }
}
