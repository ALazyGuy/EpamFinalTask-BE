package com.ltp.web.controller;

import com.ltp.web.exception.RegistryException;
import com.ltp.web.mapper.JsonMapper;
import com.ltp.web.model.dto.UserInfo;
import com.ltp.web.model.entity.UserEntity;
import com.ltp.web.security.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/user/current")
public class CurrentUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<UserEntity> current = SecurityContext.getInstance().getAuthenticated();
        resp.setContentType("application/json");
        UserInfo responseUser = current.map(UserInfo::new).get();
        resp.getWriter().write(JsonMapper.parseToString(responseUser));
    }
}
