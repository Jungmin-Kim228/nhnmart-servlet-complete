package com.nhnacademy.nhnmart.controller;

import com.nhnacademy.nhnmart.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FoodsController implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/foods.jsp";
    }
}
