package by.training.restaurant.web.servlets.catalog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.restaurant.db.dao.Dao;
import by.training.restaurant.db.entity.Category;
import by.training.restaurant.db.entity.Dish;
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

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(CatalogServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int category = Integer.parseInt(req.getParameter("categories"));
        int page = Integer.parseInt(req.getParameter("page"));
        int dishesInPage = Integer.parseInt(req.getParameter("dishesOnPage"));
        String sortBy = req.getParameter("sortBy");
        HttpSession session = req.getSession();
        try {
            if (session.getAttribute("categories") == null) {
                List<Category> categories = Dao.getDao().getCategoryDao().getAllCategories();
                session.setAttribute("categories", categories);
            }

            List<Dish> dishes;
            int maxPage;
            if (category == 0) {
                dishes = Dao.getDao().getDishDao().getSortedDishesOnPage(sortBy, dishesInPage, page);
                maxPage = Dao.getDao().getDishDao().getDishesNumber();
            } else {
                dishes = Dao.getDao().getDishDao().getSortedDishesFromCategoryOnPage(category, sortBy, dishesInPage, page);
                maxPage = Dao.getDao().getDishDao().getDishesNumberInCategory(category);
            }
            maxPage /= dishesInPage;

            session.setAttribute("maxPage", maxPage);
            session.setAttribute("dishes", dishes);
            req.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(req, resp);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }
}
