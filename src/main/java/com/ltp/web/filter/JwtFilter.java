package com.ltp.web.filter;

import com.ltp.web.service.impl.UserServiceImpl;
import com.ltp.web.util.JWTUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest)servletRequest).getHeader("token");

        if(token != null){

            if(!token.startsWith("JWT")){
                ((HttpServletResponse)servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            token = token.substring(3);
            String email = JWTUtil.getEmail(token);
            boolean result = JWTUtil.validate(token, email);

            if(!result){
                ((HttpServletResponse)servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            UserServiceImpl.getInstance().authenticate(email);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
