package by.training.restaurant.util;



import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;

public class SqlUtils {

    public static final String LOG_IN = "SELECT * FROM users WHERE login LIKE ? AND password LIKE ?";
    public static final String SIGN_UP = "INSERT INTO users (login, password) VALUES (?, ?)";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login LIKE ?";
    public static final String CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String CHANGE_ROLE = "UPDATE users SET roles_id = ? WHERE id = ?";
    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    public static final String GET_DISH_BY_ID = "SELECT * FROM dishes WHERE id = ?";
    public static final String GET_ALL_DISHES = "SELECT * FROM dishes";
    public static final String GET_SORTED_DISHES_FROM_CATEGORY = "SELECT * FROM dishes WHERE categories_id = ? ORDER BY ";
    public static final String GET_SORTED_DISHES = "SELECT * FROM dishes ORDER BY ";
    public static final String GET_DISHES_COUNT = "SELECT COUNT(*) FROM dishes";
    public static final String GET_DISHES_COUNT_IN_CATEGORY = "SELECT COUNT(*) FROM dishes WHERE categories_id = ?";
    public static final String GET_ALL_CATEGORIES = "SELECT * FROM categories";

    public static final String ADD_DISH_TO_CART = "INSERT INTO cart_has_dishes (users_id, dishes_id, count) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE count = ?";
    public static final String GET_CART = "SELECT dishes_id AS id, name, categories_id, price, weight, description, count FROM cart_has_dishes AS chd INNER JOIN dishes ON dishes.id = chd.dishes_id WHERE chd.users_id = ?";
    public static final String CLEAN_USER_CART = "DELETE FROM cart_has_dishes WHERE users_id = ?";

    public static final String ADD_RECEIPT = "INSERT INTO receipts (users_id) VALUES (?)";
    public static final String ADD_RECEIPT_HAS_DISH = "INSERT INTO receipts_has_dishes (receipts_id, dishes_id, count, price) VALUES (?, ?, ?, ?)";
    public static final String REMOVE_DISH_FROM_CART = "DELETE FROM cart_has_dishes WHERE users_id = ? AND dishes_id = ?";

    public static final String GET_USER_RECEIPTS = "SELECT * FROM receipts WHERE users_id = ?";
    public static final String GET_RECEIPT_DISHES = "SELECT dishes.id, dishes.name, rhd.price, rhd.count FROM receipts_has_dishes AS rhd INNER JOIN dishes ON dishes.id = rhd.dishes_id WHERE receipts_id = ?";
    public static final String GET_ALL_RECEIPTS = "SELECT * FROM receipts";
    public static final String GET_RECEIPTS_APPROVED_BY = "SELECT * FROM receipts WHERE manager_id = ?";
    public static final String GET_NOT_APPROVED = "SELECT * FROM receipts WHERE manager_id IS NULL";
    public static final String CHANGE_RECEIPT_STATUS = "UPDATE receipts SET status_id = ?, manager_id = ? WHERE id = ?";

    public static final String GET_DISH_ORDERS_COUNT = "SELECT id, name, IFNULL((SELECT SUM(count) FROM receipts_has_dishes WHERE receipts_has_dishes.dishes_id = dishes.id), 0) AS orders FROM dishes ORDER BY id";

    public static final Map<String, String> sortingTypes = new HashMap<>();

    static {
        sortingTypes.put("Price", "price");
        sortingTypes.put("Name", "name");
        sortingTypes.put("Category", "categories_id");
    }

    
    public static void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            LogManager.getLogger(SqlUtils.class.getName()).error(e);
            
        }
    }

   
    public static void rollback(Connection con, Savepoint s) {
        try {
            con.rollback(s);
        } catch (SQLException e) {
            LogManager.getLogger(SqlUtils.class.getName()).error(e);
        }
    }

    
    public static void close(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            LogManager.getLogger(SqlUtils.class.getName()).error(e);
        }
    }
}
