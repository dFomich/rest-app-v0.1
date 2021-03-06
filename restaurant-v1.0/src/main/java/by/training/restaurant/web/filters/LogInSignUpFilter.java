package by.training.restaurant.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/account/login", "/account/signup"})
public class LogInSignUpFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute("users") != null) {
            res.sendRedirect(req.getContextPath() + "/account");
        } else {
            chain.doFilter(req, res);
        }
    }
}
