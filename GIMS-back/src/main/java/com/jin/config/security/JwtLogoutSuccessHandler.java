package com.jin.config.security;

import cn.hutool.json.JSONUtil;
import com.jin.common.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 金磊
 */
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        response.setHeader("Authorization","");

        Result result = Result.success("");

        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
