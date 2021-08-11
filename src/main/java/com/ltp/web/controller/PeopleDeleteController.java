package com.ltp.web.controller;

import com.ltp.web.exception.ConnectionPoolException;
import com.ltp.web.service.impl.PeopleServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/people/remove")
public class PeopleDeleteController extends HttpServlet {

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
            e.printStackTrace();
        }
    }
}
