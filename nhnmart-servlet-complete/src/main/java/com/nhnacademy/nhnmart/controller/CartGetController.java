package com.nhnacademy.nhnmart.controller;

import com.nhnacademy.nhnmart.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartGetController implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/cart.jsp";
    }
}
