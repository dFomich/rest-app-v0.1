package by.training.restaurant.web.filters;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.restaurant.db.entity.User;

import java.io.IOException;

@WebFilter({"/users", "/receipts"})
public class AdminFilter extends HttpFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("users");
        if (user == null || user.getRolesId() == 1) {
            res.sendRedirect(req.getContextPath() + "/catalog");
        } else {
            chain.doFilter(req, res);
        }
    }
}
