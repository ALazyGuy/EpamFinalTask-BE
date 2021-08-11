package com.ltp.web.controller;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.mapper.JsonMapper;
import com.ltp.web.mapper.ServletRequestMapper;
import com.ltp.web.model.dto.JwtResponse;
import com.ltp.web.model.dto.PeopleAddRequest;
import com.ltp.web.model.dto.RegistrationRequest;
import com.ltp.web.service.impl.PeopleServiceImpl;
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
import java.sql.SQLException;

@WebServlet("/people/add")
public class PeopleAddController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(PeopleAddController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PeopleAddRequest peopleAddRequest = ServletRequestMapper.mapToObject(req, PeopleAddRequest.class);
            PeopleServiceImpl.getInstance().addPeople(peopleAddRequest);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Unable save people [POST /people/add]");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}