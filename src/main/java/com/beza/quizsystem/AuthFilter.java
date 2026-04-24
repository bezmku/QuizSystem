package com.beza.quizsystem;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        if (path.contains("/assets/") || path.endsWith(".css") || path.endsWith(".js")) {
            chain.doFilter(request, response);
            return;
        }
        boolean isLoginPage = path.endsWith("signup.html") || path.endsWith("signin.html") || path.endsWith("api/signin") || path.endsWith("api/signup");

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session!=null && session.getAttribute("userId") != null);

        if(isLoginPage){
            chain.doFilter(request, response);
            return;
        }

        if(isLoggedIn){
            String role = session.getAttribute("role").toString();
            if("STUDENT".equals(role) && (path.endsWith("teacherHome.html") || path.endsWith("createQuiz.html")) ){
                res.sendRedirect(req.getContextPath()+"/index.html");
                return;
            }
            if("TEACHER".equals(role) && (path.endsWith("index.html") || path.endsWith("quiz.html") || path.endsWith("result.html") || path.endsWith("studentHome.html"))){
                res.sendRedirect(req.getContextPath()+"/teacherHome.html");
                return;
            }

            chain.doFilter(request, response);
        }else{
            res.sendRedirect(req.getContextPath() + "/signin.html");
            return;
        }

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}
}