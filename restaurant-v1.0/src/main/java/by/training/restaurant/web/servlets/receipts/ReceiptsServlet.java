package by.training.restaurant.web.servlets.receipts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.restaurant.db.dao.Dao;
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

@WebServlet("/receipts")
public class ReceiptsServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ReceiptsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String filterParam = req.getParameter("filter");
        String filterAttr = (String) session.getAttribute("filter");
        String filter = filterParam == null ? filterAttr == null ? "all" : filterAttr : filterParam;
        session.setAttribute("filter", filter);
        User user = (User) session.getAttribute("user");
        try {
            List<Receipt> receipts;
            switch (filter) {
                case "all":
                    receipts = Dao.getDao().getReceiptDao().getAllReceipts();
                    break;
                case "not-approved":
                    receipts = Dao.getDao().getReceiptDao().getNotApproved();
                    break;
                case "approved-by-me":
                    receipts = Dao.getDao().getReceiptDao().getAllReceiptsAcceptedBy(user.getId());
                    break;
                default:
                    log.error("unknown filter = " + filter);
                    throw new AppException("unknown filter");
            }
            session.setAttribute("receipts", receipts);
            req.getRequestDispatcher("/WEB-INF/jsp/receipts.jsp").forward(req, resp);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User users = (User) req.getSession().getAttribute("users");
        long receiptsId = Long.parseLong(req.getParameter("id"));
        long newStatusId = Long.parseLong(req.getParameter("status-id"));
        try {
            Dao.getDao().getReceiptDao().changeStatus(receiptsId, newStatusId, users.getId());
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/receipts");
    }
}
