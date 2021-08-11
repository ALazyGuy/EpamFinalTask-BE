package com.ltp.web.controller;

import com.ltp.web.mapper.JsonMapper;
import com.ltp.web.mapper.ServletRequestMapper;
import com.ltp.web.model.dto.JwtResponse;
import com.ltp.web.model.dto.LoginRequest;
import com.ltp.web.service.impl.UserServiceImpl;
import com.ltp.web.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/user/auth")
public class LoginController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            LoginRequest loginRequest = ServletRequestMapper.mapToObject(req, LoginRequest.class);
            boolean result = UserServiceImpl.getInstance().authenticate(loginRequest);

            resp.setStatus(result ? HttpServletResponse.SC_OK : HttpServletResponse.SC_UNAUTHORIZED);

            if(result){
                resp.setContentType("application/json");
                JwtResponse jwtResponse = new JwtResponse(JWTUtil.generate(loginRequest.getEmail()));
                resp.getWriter().write(JsonMapper.parseToString(jwtResponse));
            }

        }catch(IOException e){
            LOGGER.error("Unable to read data from request [POST /user/auth]");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
