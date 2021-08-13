package com.ltp.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@MultipartConfig
@WebServlet("/people/photo")
public class PeopleAddPhotoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = String.format("%sstatic/", getServletContext().getRealPath("/"));
        if(!(new File(filePath).exists())){
            new File(filePath).mkdirs();
        }

        for(Part part : req.getParts()){
            part.write(filePath + part.getSubmittedFileName());
        }

    }
}
