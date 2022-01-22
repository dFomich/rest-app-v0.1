package by.training.restaurant.db.mysql;

import by.training.restaurant.db.dao.*;


public class MySqlDao extends Dao {

    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao();
    }

    @Override
    public DishDao getDishDao() {
        return new MySqlDishDao();
    }

    @Override
    public CategoryDao getCategoryDao() {
        return new MySqlCategoryDao();
    }

    @Override
    public CartDao getCartDao() {
        return new MySqlCartDao();
    }

    @Override
    public ReceiptDao getReceiptDao() {
        return new MySqlReceiptDao();
    }
}