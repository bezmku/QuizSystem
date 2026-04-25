package com.beza.quizsystem;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/api/delete")
public class DeleteServlet extends HttpServlet {
    private final QuestionDAO questionDAO = new QuestionDAO();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
        HttpSession session =request.getSession(false);
        if(!"TEACHER".equals(session.getAttribute("role"))){

            response.getWriter().write("{\"success\":false}");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isDeleted = questionDAO.deleteQuestion(id);
        response.getWriter().write("{\"success\":"+isDeleted+"}");
    }
}
