package by.training.restaurant.db.mysql;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import by.training.restaurant.db.dao.*;
import by.training.restaurant.db.entity.Dish;
import by.training.restaurant.exceptions.DbException;
import by.training.restaurant.util.SqlUtils;

public class MySqlCartDao implements CartDao {

    @Override
    public void addDishToCart(long usersId, long dishesId, int count) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.ADD_DISH_TO_CART)) {
            int k = 0;
            ps.setLong(++k, usersId);
            ps.setLong(++k, dishesId);
            ps.setInt(++k, count);
            ps.setInt(++k, count);
            if (ps.executeUpdate() == 0) {
                throw new DbException("AddDishToCart failed, no rows attached");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot addDishToCart", e);
        }
    }

    @Override
    public Map<Dish, Integer> getCart(long usersId) throws DbException {
        Map<Dish, Integer> cart = new HashMap<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_CART)) {
            ps.setLong(1, usersId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Dish dishes = MySqlDishDao.mapDish(rs);
                    int count = rs.getInt("count");
                    cart.put(dishes, count);
                }
            }
            return cart;
        } catch (SQLException e) {
            throw new DbException("Cannot getCart", e);
        }
    }

    @Override
    public void removeDishFromCart(long useresId, long dishesId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.REMOVE_DISH_FROM_CART)) {
            int k = 0;
            ps.setLong(++k, useresId);
            ps.setLong(++k, dishesId);
            ps.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot removeDishFromCart", e);
        }
    }

    @Override
    public void makeAnOrder(long usersId, Map<Dish, Integer> cart) throws DbException {
        Connection c = null;
        Savepoint savepoint = null;
        try {
            c = ConnectionPool.getInstance().getConnection();
            savepoint = c.setSavepoint();
            long receiptsId = addReceipt(c, usersId);
            for (Map.Entry<Dish, Integer> entry : cart.entrySet()) {
                addReceiptHasDish(c, receiptsId, entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            if (c != null) SqlUtils.rollback(c, savepoint);
            throw new DbException("Cannot makeAnOrder", e);
        } finally {
            SqlUtils.close(c);
        }
    }

    private void addReceiptHasDish(Connection c, long receiptsId, Dish dishes, int count) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(SqlUtils.ADD_RECEIPT_HAS_DISH)) {
            int k = 0;
            ps.setLong(++k, receiptsId);
            ps.setLong(++k, dishes.getId());
            ps.setLong(++k, count);
            ps.setLong(++k, dishes.getPrice());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Adding receipt_has_dish failed, no rows were attached");
            }
            c.commit();
        }
    }

    private long addReceipt(Connection c, long usersId) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(SqlUtils.ADD_RECEIPT, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            ps.setLong(++k, usersId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Adding receipt failed, no rows were attached");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.commit();
                    return rs.getLong(1);
                }
            }
        }
        return -1;
    }

    @Override
    public void cleanCart(long usersId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.CLEAN_USER_CART)) {
            ps.setLong(1, usersId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Cleaning cart failed, no rows were deleted");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot cleanCart", e);
        }
    }

	
}
