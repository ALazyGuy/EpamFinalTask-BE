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
import java.util.Optional;

@WebServlet("/people/getById")
public class PeopleGetByIdController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(PeopleGetByIdController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");

            if(idParam == null){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Long id = Long.valueOf(idParam);

            Optional<PeopleEntity> people = PeopleServiceImpl.getInstance().getById(id);

            if(people.isEmpty()){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            }

            String response = JsonMapper.parseToString(people.get());

            resp.setContentType("application/json");
            resp.getWriter().write(response);
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error("Unable to read data from request [GET /people/getById?id=]");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
