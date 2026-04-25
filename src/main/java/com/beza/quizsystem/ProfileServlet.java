package com.beza.quizsystem;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/api/profile")
public class ProfileServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final UserDAO userDAO = new UserDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null){
            response.sendRedirect(request.getContextPath() + "/signin.html");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        int id = (int) session.getAttribute("userId");
        User user = userDAO.getCredentials(id);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(user));
    }
}
