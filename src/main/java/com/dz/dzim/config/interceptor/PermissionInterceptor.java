package com.dz.dzim.config.interceptor;


import com.dz.dzim.exception.ErrorCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限校验拦截器
 *
 * @author yanpanyi
 * @date 2019/4/5
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String TOKEN = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ErrorCodeException {
        String token = request.getHeader(PermissionInterceptor.TOKEN);

        logger.info("权限校验 token -> {}", token);

//        if (CheckUtils.checkToken(token)) {
//            return true;
//        }

   //     logger.info("没有权限");

    //    throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        return true;
    }
}
