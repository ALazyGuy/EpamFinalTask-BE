package com.ltp.web.controller;

import com.ltp.web.mapper.JsonMapper;
import com.ltp.web.model.dto.JwtResponse;
import com.ltp.web.model.dto.RegistrationRequest;
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

@WebServlet("/user/add")
public class RegistrationController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            StringBuilder buffer = new StringBuilder();
            String s;
            while((s = req.getReader().readLine()) != null){
                buffer.append(s);
            }

            RegistrationRequest registrationRequest = JsonMapper.parseToObject(buffer.toString(), RegistrationRequest.class);
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
