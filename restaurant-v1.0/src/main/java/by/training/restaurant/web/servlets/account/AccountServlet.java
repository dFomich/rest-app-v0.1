package by.training.restaurant.web.servlets.account;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.restaurant.db.dao.Dao;
import by.training.restaurant.db.entity.Dish;
import by.training.restaurant.db.entity.Receipt;
import by.training.restaurant.db.entity.User;
import by.training.restaurant.exceptions.AppException;
import by.training.restaurant.exceptions.DbException;
import by.training.restaurant.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(AccountServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("signout") != null) {
            Utils.logout(req, resp);
            return;
        }
        User user = (User) session.getAttribute("user");
        try {
            List<Receipt> receipts = Dao.getDao().getReceiptDao().getUserReceipts(user.getId());
            session.setAttribute("receipts", receipts);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Map<Dish, Integer> cart = (Map<Dish, Integer>) session.getAttribute("cart");
        try {
            Dao.getDao().getCartDao().makeAnOrder(user.getId(), cart);
            Dao.getDao().getCartDao().cleanCart(user.getId());
            session.removeAttribute("cart");
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/account");
    }
}
