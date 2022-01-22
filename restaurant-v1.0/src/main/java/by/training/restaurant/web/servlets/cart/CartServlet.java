package by.training.restaurant.web.servlets.cart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.restaurant.db.dao.Dao;
import by.training.restaurant.db.entity.Dish;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(CartServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        Map<Dish, Integer> cart = new HashMap<>();
        if (session.getAttribute("users") == null) {
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", cart);
            }
        } else {
            User users = (User) session.getAttribute("users");
            try {
                cart = Dao.getDao().getCartDao().getCart(users.getId());
              
                Map<Dish, Integer> sCart = (Map<Dish, Integer>) session.getAttribute("cart");
                if (sCart != null && sCart != cart) {
                    for (Map.Entry<Dish, Integer> entry : sCart.entrySet()) {
                        Dao.getDao().getCartDao().addDishToCart(users.getId(), entry.getKey().getId(), entry.getValue());
                        cart.put(entry.getKey(), entry.getValue());
                    }
                }
                session.setAttribute("cart", cart);
            } catch (DbException e) {
                log.error(Utils.getErrMessage(e));
                throw new AppException(e);
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        int dishesId = Integer.parseInt(req.getParameter("id"));
        int count = Integer.parseInt(req.getParameter("count"));
 
        Map<Dish, Integer> cart;
        try {
            Dish dishes = Dao.getDao().getDishDao().getDishById(dishesId);
            if (session.getAttribute("users") == null) {
                if (session.getAttribute("cart") != null) {
                    cart = new HashMap<>((Map<Dish, Integer>) session.getAttribute("cart"));
                } else {
                    cart = new HashMap<>();
                }
                if (count == -1) {
                    cart.remove(dishes);
                } else {
                    cart.put(dishes, count);
                }
                session.setAttribute("cart", cart);
            } else {
                User users = (User) session.getAttribute("users");
                if (count == -1) {
                    Dao.getDao().getCartDao().removeDishFromCart(users.getId(), dishesId);
                } else {
                    Dao.getDao().getCartDao().addDishToCart(users.getId(), dishesId, count);
                }
                session.removeAttribute("cart");
            }
            res.sendRedirect(req.getContextPath() + "/cart");
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }
}
