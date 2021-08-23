package com.ltp.web.controller;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.mapper.ServletRequestMapper;
import com.ltp.web.model.dto.PeopleEditRequest;
import com.ltp.web.service.impl.PeopleServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/people/edit")
public class PeopleEditController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(PeopleEditController.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PeopleEditRequest peopleEditRequest = ServletRequestMapper.mapToObject(req, PeopleEditRequest.class);
            PeopleServiceImpl.getInstance().edit(peopleEditRequest);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Unable to edit people [PUT /people/edit]");
        }
    }
}
