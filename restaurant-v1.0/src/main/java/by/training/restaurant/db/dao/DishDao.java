package by.training.restaurant.db.dao;

import java.util.List;

import java.util.Map;

import by.training.restaurant.db.entity.Dish;
import by.training.restaurant.exceptions.DbException;
import com.mysql.cj.conf.ConnectionUrlParser.Pair;

public interface DishDao {
	
	Dish getDishById(long id) throws DbException;
	
	List<Dish> getAllDishes() throws DbException;
	
	List<Dish> getSortedDishesFromCategoryOnPage(int categoriesId, String sortBy, int dishesInPage, int pageNum) throws DbException;

	List<Dish> getSortedDishesOnPage(String sortBy, int dishesInPage, int pageNum) throws DbException;
	
	int getDishesNumber() throws DbException;
	
	int getDishesNumberInCategory(int categoriesId) throws DbException;
	
	Map<Integer, Pair<String, Integer>> getDishesOrderCount() throws DbException;
	
}
