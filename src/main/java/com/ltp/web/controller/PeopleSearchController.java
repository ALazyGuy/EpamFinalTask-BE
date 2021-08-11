package com.ltp.web.controller;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.mapper.JsonMapper;
import com.ltp.web.model.entity.PeopleEntity;
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
import java.util.List;

@WebServlet("/people/search")
public class PeopleSearchController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(PeopleSearchController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String fullName = req.getParameter("fullName");

            if(fullName == null){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            List<PeopleEntity> peoples = PeopleServiceImpl.getInstance().findSimilar(fullName);

            String response = JsonMapper.parseToString(peoples);

            resp.setContentType("application/json");
            resp.getWriter().write(response);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Unable to read data from request [GET /people/search?fullName=]");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
