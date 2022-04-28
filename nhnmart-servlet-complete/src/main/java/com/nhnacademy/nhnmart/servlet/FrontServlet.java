package com.nhnacademy.nhnmart.servlet;

import com.nhnacademy.nhnmart.Command;
import com.nhnacademy.nhnmart.controller.CartGetController;
import com.nhnacademy.nhnmart.controller.CartPostController;
import com.nhnacademy.nhnmart.controller.FoodsController;
import com.nhnacademy.nhnmart.controller.LoginGetController;
import com.nhnacademy.nhnmart.controller.LoginPostController;
import com.nhnacademy.nhnmart.controller.LogoutController;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "frontServlet", urlPatterns = "*.do")
public class FrontServlet extends HttpServlet {
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // 공통 처리 - 응답 content-type, character encoding 지정.
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        log.error("frontservlet work");

        try {
//            // 실제 요청 처리할 Servlet 결정.
//            String processingServletPath = resolveServlet(req.getServletPath());
//
//            // 실제 요청을 처리할 Servlet으로 요청을 전달하여 처리 결과를 include시킴.
//            RequestDispatcher rd = req.getRequestDispatcher(processingServletPath);
//            rd.include(req, resp);
//
//            // 실제 요청을 처리한 Servlet이 `view`라는 request 속성 값으로 view를 전달해 줌.
//            String view = (String) req.getAttribute("view");

            Command command = resolveCommand(req.getServletPath(), req.getMethod());
            log.error("frontservlet: getServletPath - " + req.getServletPath());
            log.error("frontservlet: getMethod - " + String.valueOf(req.getMethod()));

            String view = command.execute(req, resp);
            log.error("frontservlet: view - " + view);

            if (view.startsWith(REDIRECT_PREFIX)) {
                // `redirect:`로 시작하면 redirect 처리.
                resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()));
            } else {
                // redirect가 아니면 JSP에게 view 처리를 위임하여 그 결과를 include시킴.
                RequestDispatcher rd = req.getRequestDispatcher(view);
                rd.include(req, resp);
            }
        } catch (Exception ex) {
            // 에러가 발생한 경우는 error page로 지정된 `/error.jsp`에게 view 처리를 위임.
            log.error("", ex);
            req.setAttribute("exception", ex);
            RequestDispatcher rd = req.getRequestDispatcher("/error.jsp");
            rd.forward(req, resp);
        }
    }

    private Command resolveCommand(String servletPath, String method) {
        Command command = null;

        if ("/foods.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new FoodsController();
        } else if ("/cart.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new CartGetController();
        } else if ("/cart.do".equals(servletPath) && "POST".equalsIgnoreCase(method)) {
            command = new CartPostController();
        } else if ("/login.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new LoginGetController();
        } else if ("/login.do".equals(servletPath) && "POST".equalsIgnoreCase(method)) {
            command = new LoginPostController("user", "1111");
        } else if ("/logout.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new LogoutController();
        } /* .. */

            return command;
        }
    }
