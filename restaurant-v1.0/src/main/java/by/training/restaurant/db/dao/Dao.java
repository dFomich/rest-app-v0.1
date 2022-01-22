package by.training.restaurant.db.dao;


import by.training.restaurant.db.mysql.MySqlDao;

public abstract class Dao {

    public static Dao getDao() {
       
        return new MySqlDao();
    }

    public abstract UserDao getUserDao();
    public abstract DishDao getDishDao();
    public abstract CategoryDao getCategoryDao();
    public abstract CartDao getCartDao();
    public abstract ReceiptDao getReceiptDao();
}
