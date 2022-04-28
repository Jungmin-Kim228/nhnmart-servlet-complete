package com.nhnacademy.nhnmart.controller;

import com.nhnacademy.nhnmart.Command;
import com.nhnacademy.nhnmart.domain.Basket;
import com.nhnacademy.nhnmart.domain.Food;
import com.nhnacademy.nhnmart.domain.FoodStand;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartPostController implements Command {
    private FoodStand foodStand;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int idx = 0;
        this.foodStand = (FoodStand) request.getServletContext().getAttribute("foodStand");
        int[] foodAmounts;
        try {
            foodAmounts = checkAmountValid(request, response, idx);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            throw new RuntimeException(ex);
        }

        request.getServletContext().setAttribute("basket", initBasketAndTotal(foodAmounts, idx));
        request.getServletContext().setAttribute("foodStand", foodStand);

        return "/cart.jsp";
    }

    private int[] checkAmountValid(HttpServletRequest req, HttpServletResponse resp, int idx) throws
        IOException {
        String[] received = req.getParameterValues("food");
        int[] foodAmounts = new int[received.length];

        for (String foodValue : received) {
            foodAmounts[idx] = Optional.of(Integer.parseInt(foodValue)).orElse(0);
            if (foodAmounts[idx] > this.foodStand.getFoods().get(idx).getAmount() || foodAmounts[idx] < 0) {
                resp.setStatus(417);
                resp.sendError(417, "Out of amount");
                return new int[0];
            }
            idx++;
        }
        return foodAmounts;
    }

    private Basket initBasketAndTotal(int[] foodAmounts, int idx) {
        Basket basket = new Basket();
        for (Food food : this.foodStand.getFoods()) {
            basket.add(new Food(food.getName(), food.getPrice(), foodAmounts[idx]));
            food.setAmount(food.getAmount()-foodAmounts[idx]);
            idx++;
        }
        return basket;
    }
}
