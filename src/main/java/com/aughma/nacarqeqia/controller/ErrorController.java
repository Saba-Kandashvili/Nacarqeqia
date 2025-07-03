package com.aughma.nacarqeqia.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    /**
     * Handles generic server errors (like 404 Not Found, 500 Internal Server Error).
     * Spring Boot routes these errors to the "/error" path.
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            }
            // You could add more specific cases here, like for 500 errors
            // else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            //     return "500"; // if you create a 500.html
            // }
        }

        // A fallback for any other error codes
        return "404";
    }

    /**
     * Handles the specific forward from Spring Security for Access Denied (403) errors.
     * This mapping is required because of `.accessDeniedPage("/403")` in SecurityConfig.
     */
    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}