package by.training.restaurant.db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import by.training.restaurant.db.dao.ConnectionPool;
import by.training.restaurant.db.dao.DishDao;
import by.training.restaurant.db.entity.Dish;
import by.training.restaurant.exceptions.DbException;
import by.training.restaurant.util.SqlUtils;

public class MySqlDishDao implements DishDao {

    public static Dish mapDish(ResultSet rs) throws SQLException {
        return new Dish.Builder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setCategoriesId(rs.getLong("categories_id"))
                .setPrice(rs.getLong("price"))
                .setWeight(rs.getLong("weight"))
                .setDescription(rs.getString("description"))
                .getDishes();
    }

    @Override
    public Dish getDishById(long id) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISH_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDish(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DbException("Cannot getDishById", e);
        }
    }

    @Override
    public List<Dish> getAllDishes() throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_DISHES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dishes.add(mapDish(rs));
            }
            return dishes;
        } catch (SQLException e) {
            throw new DbException("Cannot getAllDishes", e);
        }
    }

    @Override
    public List<Dish> getSortedDishesFromCategoryOnPage(int categoriesId, String sortBy, int dishesInPage, int pageNum) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     SqlUtils.GET_SORTED_DISHES_FROM_CATEGORY + sortBy + " LIMIT " + pageNum * dishesInPage + ", " + dishesInPage)) {
            ps.setLong(1, categoriesId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException e) {
            throw new DbException("Cannot getSortedDishesFromCategory" + categoriesId, e);
        }
    }

    @Override
    public List<Dish> getSortedDishesOnPage(String sortBy, int dishesInPage, int pageNum) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     SqlUtils.GET_SORTED_DISHES + sortBy + " LIMIT " + pageNum * dishesInPage + ", " + dishesInPage)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException e) {
            throw new DbException("Cannot getSortedDishes", e);
        }
    }

    @Override
    public int getDishesNumber() throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISHES_COUNT);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DbException("Cannot getDishesNumber", e);
        }
    }

    @Override
    public int getDishesNumberInCategory(int categoriesId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISHES_COUNT_IN_CATEGORY)) {
            ps.setInt(1, categoriesId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return -1;
            }
        } catch (SQLException e) {
            throw new DbException("Cannot getDishesNumberInCategory", e);
        }
    }

    @Override
    public Map<Integer, Pair<String, Integer>> getDishesOrderCount() throws DbException {
        Map<Integer, Pair<String, Integer>> answ = new HashMap<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISH_ORDERS_COUNT);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int orders = rs.getInt("orders");
                Pair<String, Integer> pair = new Pair<>(name, orders);
                answ.put(id, pair);
            }
            return answ;
        } catch (SQLException e) {
            throw new DbException("Cannot getDishesOrderCount", e);
        }
    }
}