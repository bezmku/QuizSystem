package com.beza.quizsystem;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/signin")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
    private final Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        try(BufferedReader reader = request.getReader()){
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
        }
        User credentials = gson.fromJson(buffer.toString(), User.class);
        User logedInUser = userDAO.logInUser(credentials.getUsername(), credentials.getPassword());

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if(logedInUser!=null){
            HttpSession session = request.getSession();
            session.setAttribute("userId",logedInUser.getId());
            session.setAttribute("role",logedInUser.getRole());
            String destination = logedInUser.getRole().equals("TEACHER")?"teacherHome.html":"index.html";
            out.print("{\"success\":true,\"redirectURL\":\""+destination+"\"}");
        }
        else{
            out.print("{\"success\":false,\"message\":\"Invalid username or password\"}");
        }
        out.flush();
    }
}
