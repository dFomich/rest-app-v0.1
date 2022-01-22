package by.training.restaurant.db.dao;

import java.util.List;


import by.training.restaurant.db.entity.*;
import by.training.restaurant.exceptions.*;

public interface CategoryDao {
	 List<Category> getAllCategories() throws DbException;

}
