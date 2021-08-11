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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/people/getAll")
public class PeopleGetAllController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<PeopleEntity> peopleEntities = PeopleServiceImpl.getInstance().findAll();
            String response = JsonMapper.parseToString(peopleEntities);
            resp.setContentType("application/json");
            resp.getWriter().write(response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
