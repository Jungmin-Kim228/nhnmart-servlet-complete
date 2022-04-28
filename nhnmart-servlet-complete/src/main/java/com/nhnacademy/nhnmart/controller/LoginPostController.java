package com.nhnacademy.nhnmart.controller;

import com.nhnacademy.nhnmart.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPostController implements Command {
    private final String idInitParam;
    private final String pwInitParam;

    public LoginPostController(String idInitParam, String pwInitParam) {
        this.idInitParam = idInitParam;
        this.pwInitParam = pwInitParam;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String receivedId = request.getParameter("id");
        String receivedPw = request.getParameter("pw");

        if (idInitParam.equals(receivedId) && pwInitParam.equals(receivedPw)) {
            HttpSession session = request.getSession();
            session.setAttribute("id", receivedId);
            log.error("login post controller: session - " + session);

            return "/login.do";
        }
        return "/loginForm.jsp";
    }
}
