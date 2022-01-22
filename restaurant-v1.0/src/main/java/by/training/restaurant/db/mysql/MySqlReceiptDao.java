package by.training.restaurant.db.mysql;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.training.restaurant.db.dao.*;
import by.training.restaurant.db.entity.Receipt;
import by.training.restaurant.exceptions.DbException;
import by.training.restaurant.util.SqlUtils;


public class MySqlReceiptDao implements ReceiptDao {

    private static Receipt mapReceipt(ResultSet rs) throws SQLException {
        return new Receipt.Builder()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("users_id"))
                .setStatusId(rs.getLong("status_id"))
                .setTotal(rs.getInt("total"))
                .setManagerId(rs.getLong("manager_id"))
                .setCreateDate(rs.getTimestamp("create_date"))
                .getReceipts();
    }

    @Override
    public List<Receipt> getUserReceipts(long usersId) throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_USER_RECEIPTS)) {
            ps.setLong(1, usersId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt receipt = mapReceipt(rs);
                    receipt.setDishes(getReceiptDishes(receipt.getId()));
                    receipts.add(receipt);
                }
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getUserReceipts", e);
        }
    }

    private List<Receipt.Dish> getReceiptDishes(long receiptsId) throws SQLException {
        List<Receipt.Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_RECEIPT_DISHES)) {
            ps.setLong(1, receiptsId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt.Dish dish = new Receipt.Dish.Builder()
                            .setId(rs.getLong("id"))
                            .setName(rs.getString("name"))
                            .setPrice(rs.getInt("price"))
                            .setCount(rs.getInt("count"))
                            .getDish();
                    dishes.add(dish);
                }
            }
        }
        return dishes;
    }

    @Override
    public List<Receipt> getAllReceipts() throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_RECEIPTS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Receipt receipt = mapReceipt(rs);
                receipt.setDishes(getReceiptDishes(receipt.getId()));
                receipts.add(receipt);
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getAllReceipts", e);
        }
    }

    @Override
    public List<Receipt> getAllReceiptsAcceptedBy(long managerId) throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_RECEIPTS_APPROVED_BY)) {
            ps.setLong(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt receipt = mapReceipt(rs);
                    receipt.setDishes(getReceiptDishes(receipt.getId()));
                    receipts.add(receipt);
                }
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getAllReceiptsAcceptedBy", e);
        }
    }

    @Override
    public List<Receipt> getNotApproved() throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_NOT_APPROVED);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Receipt receipt = mapReceipt(rs);
                receipt.setDishes(getReceiptDishes(receipt.getId()));
                receipts.add(receipt);
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getNotApproved", e);
        }
    }

    @Override
    public void changeStatus(long receiptsId, long statusId, long managerId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.CHANGE_RECEIPT_STATUS)) {
            int k = 0;
            ps.setLong(++k, statusId);
            ps.setLong(++k, managerId);
            ps.setLong(++k, receiptsId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Change status failed, no rows attached");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot changeStatus", e);
        }
    }
}
