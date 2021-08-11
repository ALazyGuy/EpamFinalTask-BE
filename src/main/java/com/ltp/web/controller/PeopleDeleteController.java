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

@WebServlet("/people/remove")
public class PeopleDeleteController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(PeopleDeleteController.class);

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");

            if(idParam == null){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Long id = Long.valueOf(idParam);
            PeopleServiceImpl.getInstance().removePeople(id);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Unable to read data from request [DELETE /people/remove]");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
