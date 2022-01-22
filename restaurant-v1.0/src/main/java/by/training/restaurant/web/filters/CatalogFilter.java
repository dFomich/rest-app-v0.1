package by.training.restaurant.web.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.restaurant.db.entity.User;

import java.io.IOException;

@WebFilter({"/", "/catalog"})
public class CatalogFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String categories = req.getParameter("categories");
        String sortBy = req.getParameter("sortBy");
        User user = (User) req.getSession().getAttribute("users");
        if (user != null && user.getRolesId() == 2) {
            res.sendRedirect(req.getContextPath() + "/users");
            return;
        }
        if (categories == null || sortBy == null) {
            res.sendRedirect(req.getContextPath() + "/catalog?categories=0&sortBy=categories_id&page=0&dishesOnPage=5");
        } else {
            chain.doFilter(req, res);
        }
    }
}
