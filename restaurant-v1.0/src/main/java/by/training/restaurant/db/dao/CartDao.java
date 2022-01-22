package by.training.restaurant.db.dao;

import java.util.Map;

import by.training.restaurant.db.entity.Dish;
import by.training.restaurant.exceptions.*;

public interface CartDao {

	void addDishToCart(long usersId, long dishesId, int count) throws DbException;

	Map<Dish, Integer> getCart(long usersId) throws DbException;

	void makeAnOrder(long usersId, Map<Dish, Integer> cart) throws DbException;

	void removeDishFromCart(long usersId, long dishesId) throws DbException;

	void cleanCart(long usersId) throws DbException;

}
