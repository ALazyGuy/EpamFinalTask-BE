package com.ltp.web.controller;

import com.ltp.web.exception.ConnectionPoolException;
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

@WebServlet("/people/found")
public class PeopleFoundMapping extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(PeopleFoundMapping.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");

            if(idParam == null){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Long id = Long.valueOf(idParam);
            PeopleServiceImpl.getInstance().changeStatus(id);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Unable to read data from request [PUT /people/found]");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
