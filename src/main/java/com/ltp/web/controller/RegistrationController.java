package com.ltp.web.controller;

import com.ltp.web.mapper.JsonMapper;
import com.ltp.web.mapper.ServletRequestMapper;
import com.ltp.web.model.dto.JwtResponse;
import com.ltp.web.model.dto.RegistrationRequest;
import com.ltp.web.service.impl.UserServiceImpl;
import com.ltp.web.util.JWTUtil;
import com.ltp.web.validator.AuthValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/user/add")
public class RegistrationController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            RegistrationRequest registrationRequest = ServletRequestMapper.mapToObject(req, RegistrationRequest.class);

            if(!AuthValidator.validateRegistration(registrationRequest)){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            boolean result = UserServiceImpl.getInstance().addUser(registrationRequest);

            resp.setStatus(result ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);

            if(result){
                resp.setContentType("application/json");
                JwtResponse jwtResponse = new JwtResponse(JWTUtil.generate(registrationRequest.getEmail()));
                resp.getWriter().write(JsonMapper.parseToString(jwtResponse));
            }

        }catch(IOException e){
            LOGGER.error("Unable to read data from request [POST /user/add]");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }
}
