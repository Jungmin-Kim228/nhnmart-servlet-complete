package com.nhnacademy.nhnmart.servlet;

import com.nhnacademy.nhnmart.domain.Food;
import com.nhnacademy.nhnmart.domain.FoodStand;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "cartServlet", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {
    private FoodStand foodStand;
    private ArrayList<Food> basket = new ArrayList<>();
    private int totalPrice = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            showFoodInBasket(out);
            out.println("[Total: " + this.totalPrice + "]<br>");
            out.println("<a href='/logout'>logout</a>");
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        int idx = 0;
        this.foodStand = (FoodStand) getServletContext().getAttribute("foodStand");
        int[] foodAmounts = checkAmountValid(req, resp, idx);

        if (foodAmounts == null) {
            resp.setStatus(417);
            return;
        }

        initBasketAndTotal(foodAmounts, idx);
        getServletContext().setAttribute("foodStand", foodStand);

        resp.sendRedirect("/cart");
    }

    private void showFoodInBasket(PrintWriter out) {
        out.println("<h1>[Your Basket]</h1><br>");
        for (Food food : this.basket) {
            if (food.getAmount() != 0) {
                out.println("name: " + food.getName()+"<br>");
                out.println("amount: " + food.getAmount()+"<br>");
                out.println("price: " + food.getPrice()+"<br>");
                out.println("--------------------<br>");
            }
        }
    }

    private int[] checkAmountValid(HttpServletRequest req, HttpServletResponse resp, int idx) throws IOException {
        String[] received = req.getParameterValues("food");
        int[] foodAmounts = new int[received.length];

        for (String foodValue : received) {
            foodAmounts[idx] = Optional.of(Integer.parseInt(foodValue)).orElse(0);
            if (foodAmounts[idx] > this.foodStand.getFoods().get(idx).getAmount() || foodAmounts[idx] < 0) {
                resp.setStatus(417);
                resp.sendError(417, "Out of amount");
                return null;
            }
            idx++;
        }
        return foodAmounts;
    }

    private void initBasketAndTotal(int[] foodAmounts, int idx) {
        for (Food food : this.foodStand.getFoods()) {
            this.basket.add(new Food(food.getName(), food.getPrice(), foodAmounts[idx]));
            food.setAmount(food.getAmount()-foodAmounts[idx]);
            this.totalPrice += foodAmounts[idx] * food.getPrice();
            idx++;
        }
    }

}
