package by.tc.photobook.dao;

import java.time.LocalDate;
import java.util.List;

import by.tc.photobook.bean.Order;

/**
 * The interface for working with database table that stored all orders
 * 
 * @author Darya Minina
 */
public interface OrdersDAO 
{
	/**
	 * Add one order to user's orders
	 * 
	 * @param user user who makes an order
	 * @param photoshootOption the id of the photoshoot option to order
	 * @param date {@link LocalDate} date of the order
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public boolean addOrder(int user, int photoshootOption, LocalDate date) throws DAOException;
	
	/**
	 * Takes all photographer's orders
	 * 
	 * @param photographerId the id of the photographer
	 * @return list of the orders {@link Order}
	 * @throws DAOException
	 */
	public List<Order> getOrdersByPhotographer(int photographerId) throws DAOException;
	
	/**
	 * Takes all client's orders
	 * 
	 * @param clientId the id of the client
	 * @return list of the orders {@link Order}
	 * @throws DAOException
	 */
	public List<Order> getOrdersByClient(int clientId) throws DAOException;
	
	/**
	 * Marks an order as declined or accepted depending on the action parameter
	 * 
	 * @param orderId the id of the order to process
	 * @param action decline or accept
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public boolean processOrder(int orderId, String action) throws DAOException;
	
	/**
	 * Cancels user's order
	 * 
	 * @param orderId the id of the order to cancel
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public boolean cancelOrder(int orderId) throws DAOException;
}