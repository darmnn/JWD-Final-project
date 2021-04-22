package by.tc.photobook.service;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;

import by.tc.photobook.bean.Order;
import by.tc.photobook.bean.Timetable;

/**
 * The interface to operate with orders
 * 
 * @author Darya Minina
 */
public interface OrdersService 
{
	/**
	 * Add one order to user's orders
	 * 
	 * @param user user who makes an order
	 * @param photoshootOption the id of the photoshoot option to order
	 * @param date {@link LocalDate} date of the order
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public boolean addOrder(int user, int photoshootOption, LocalDate date) throws ServiceException;
	
	/**
	 * Takes all photographer's orders
	 * 
	 * @param photographerId the id of the photographer
	 * @return list of the orders {@link Order}
	 * @throws ServiceException
	 */
	public List<Order> getOrdersByPhotographer(int photographerId) throws ServiceException;
	
	/**
	 * Marks an order as declined or accepted depending on the action parameter
	 * 
	 * @param orderId the id of the order to process
	 * @param action decline or accept
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public boolean processOrder(int orderId, String action) throws ServiceException;
	
	/**
	 * Takes all client's orders
	 * 
	 * @param clientId the id of the client
	 * @return list of the orders {@link Order}
	 * @throws ServiceException
	 */
	public List<Order> getOrdersByClient(int clientId) throws ServiceException;
	
	/**
	 * Cancels user's order
	 * 
	 * @param orderId the id of the order to cancel
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public boolean cancelOrder(int orderId) throws ServiceException; 
	
	/**
	 * Gets all days of a month and all orders assigned to each day
	 * 
	 * @param photographerId the id of the photographer
	 * @param date the month that is checked
	 * @return a map that shows days of the month and orders
	 * @throws ServiceException
	 */
	public HashMap<Integer, Order> getBusyDaysOfMonth(int photographerId, LocalDate date) throws ServiceException;
	
	/**
	 * A helper method that gets three month date represented in {@link Timetable} objects
	 * 
	 * @return an array on {@link Timetable}
	 */
	public Timetable[] getTimetable();
}
