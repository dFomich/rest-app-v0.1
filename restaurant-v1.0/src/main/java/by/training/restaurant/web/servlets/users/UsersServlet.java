package by.training.restaurant.web.servlets.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.restaurant.db.dao.Dao;
import by.training.restaurant.db.entity.User;
import by.training.restaurant.exceptions.AppException;
import by.training.restaurant.exceptions.DbException;
import by.training.restaurant.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(UsersServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = Dao.getDao().getUserDao().getAllUsers();
            users.removeIf(user -> user.getId() == ((User) req.getSession().getAttribute("users")).getId());
            req.setAttribute("users", users);
            req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int usersId = Integer.parseInt(req.getParameter("users_id"));
        int rolesId = Integer.parseInt(req.getParameter("roles_id"));
        try {
            if (rolesId == -1) {
                Dao.getDao().getUserDao().deleteUser(usersId);
            } else {
                Dao.getDao().getUserDao().changeRole(usersId, rolesId);
            }
            resp.sendRedirect(req.getContextPath() + "/users");
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }
}
