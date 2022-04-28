package com.nhnacademy.nhnmart.controller;

import com.nhnacademy.nhnmart.Command;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginGetController implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (Objects.isNull(session)) {
            return "/loginForm.jsp";
        }
        return String.valueOf(request.getServletContext().getAttribute("redirect"));
    }
}
