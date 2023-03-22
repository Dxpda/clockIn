//开发时间 : 2023/3/14 19:38

package com.da.clockin.intercepter;

import cn.hutool.core.date.DateUtil;
import com.da.clockin.common.exception.CurrencyException;
import com.da.clockin.common.util.Jwt;
import com.da.clockin.common.util.ThreadUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义拦截器
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//        String methodName = method.getName();

        String authorization = request.getHeader("Authorization");
        if (authorization == null){
            throw new CurrencyException("登录异常");
        }
        try{
            String s = authorization.split(" ")[1];
            Map<String, Object> map = Jwt.verifyToken(s);
            if (map == null){
                throw new CurrencyException("登录异常");
            }

            Map<String, Object> objectMap = new LinkedHashMap<>();
            objectMap.put("id", map.get("id"));
            objectMap.put("user", map.get("user"));
            objectMap.put("power",map.get("power"));
            ThreadUtil.setThreadLocal(objectMap);

            Date time = DateUtil.parse(String.valueOf(map.get("time")));
            System.out.println(map);

            Date date = DateUtil.date();
            Date dateTime = DateUtil.offsetDay(date, 2);
            int i = time.compareTo(dateTime);
            if (i > 0){
                throw new CurrencyException("登录过期");
            }


        }catch(Exception e){
            throw new CurrencyException("登录异常");
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse
            response, Object handler, Exception ex) throws Exception {
//        System.out.println("整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可以做一些清理的工作了");
    }
}
