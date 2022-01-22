package by.training.restaurant.db.dao;

import java.util.List;


import by.training.restaurant.db.entity.User;
import by.training.restaurant.exceptions.DbException;

public interface UserDao {
	
	User logIn(String login, char[] password) throws DbException;
	
	User signUp(String login, char[] password) throws DbException;
	
	boolean isLoginUnique(String login) throws DbException;
	
	User getUserByLogin(String login) throws DbException;
	
	void changePassword(long usersId, char[] newPass) throws DbException;
	
	List<User> getAllUsers() throws DbException;
	
	void changeRole(long usersId, int rolesId) throws DbException;
	 
	void deleteUser(long usersId) throws DbException;
	
	

}
