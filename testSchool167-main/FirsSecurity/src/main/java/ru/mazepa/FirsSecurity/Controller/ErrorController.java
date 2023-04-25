package ru.mazepa.FirsSecurity.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController{

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model) {
        // Получаем код ошибки из запроса
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        // Добавляем код ошибки в модель
        model.addAttribute("statusCode", statusCode);

        // Возвращаем страницу ошибки
        return "error/error404";
    }


    public String getErrorPath() {
        return "/error404";
    }


}
