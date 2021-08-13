package com.ltp.web.filter;

import com.ltp.web.exception.RegistryException;
import com.ltp.web.security.SecurityContext;
import com.ltp.web.security.registry.RolesRegistry;
import com.ltp.web.service.impl.UserServiceImpl;
import com.ltp.web.util.JWTUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter("/*")
public class JwtFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(JwtFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, PUT, POST, DELETE, HEAD");

        if (((HttpServletRequest)servletRequest).getMethod().equals("OPTIONS")) {
            ((HttpServletResponse)servletResponse).setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        if(((HttpServletRequest) servletRequest).getServletPath().startsWith("/static/")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = ((HttpServletRequest)servletRequest).getHeader("token");

        String role = "SHARED";

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
            role = SecurityContext.getInstance().getAuthenticated().get().getRole().name();
        }

        String currentContext = ((HttpServletRequest) servletRequest).getServletPath();
        try {
            if(!RolesRegistry.getInstance().validate(role, currentContext)){
                ((HttpServletResponse)servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (RegistryException e) {
            LOGGER.error(String.format("Registry error -> %s", e.getMessage()));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
