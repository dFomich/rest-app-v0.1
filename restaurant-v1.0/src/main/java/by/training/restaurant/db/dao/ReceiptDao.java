package by.training.restaurant.db.dao;

import java.util.List;



import by.training.restaurant.db.entity.Receipt;
import by.training.restaurant.exceptions.DbException;

public interface ReceiptDao {
	
	 List<Receipt> getUserReceipts(long usersId) throws DbException;
	 
	 List<Receipt> getAllReceipts() throws DbException;
	 
	 List<Receipt> getAllReceiptsAcceptedBy(long managerId) throws DbException;
	 
	 List<Receipt> getNotApproved() throws DbException;
	 
	 void changeStatus(long receiptsId, long statusId, long managerId) throws DbException;
	 
	 

}
