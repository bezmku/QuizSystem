package com.beza.quizsystem;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/signup")
public class SignupServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
    private final Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws IOException {

        StringBuilder buffer = new StringBuilder();
        String line;
        try(BufferedReader reader = request.getReader()){
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
        }

        String jsonBody = buffer.toString();
        User newUser = gson.fromJson(jsonBody, User.class);

        boolean isRegisterd = userDAO.registerUser(newUser);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if(isRegisterd){
            out.print("{\"success\":true, \"redirectURL\":\"signin.html\", \"message\":\"user created!!\"}");
        }
        else{
            out.print("{\"success\":false,\"message\":\"Registration failed. Try using different username!\"}");
        }
        out.flush();
    }
}
