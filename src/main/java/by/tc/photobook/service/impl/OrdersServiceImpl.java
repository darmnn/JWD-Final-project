package by.tc.photobook.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import by.tc.photobook.bean.Order;
import by.tc.photobook.bean.Timetable;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.OrdersDAO;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.ServiceException;

public class OrdersServiceImpl implements OrdersService
{
	private static final String APPROVED = "Одобрено";
	
	public boolean addOrder(int user, int photoshootOption, LocalDate date) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		OrdersDAO ordersDAO = daoProvider.getOrdersDAO();
		
		try
		{
			ordersDAO.addOrder(user, photoshootOption, date);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		return true;
	}
	
	public List<Order> getOrdersByPhotographer(int photographerId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		OrdersDAO ordersDAO = daoProvider.getOrdersDAO();
		List<Order> allOrders = null;
		
		try 
		{
			allOrders = ordersDAO.getOrdersByPhotographer(photographerId);
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return allOrders;
	}
	
	public boolean processOrder(int orderId, String action) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		OrdersDAO ordersDAO = daoProvider.getOrdersDAO();
		
		try
		{
			ordersDAO.processOrder(orderId, action);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	public List<Order> getOrdersByClient(int clientId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		OrdersDAO ordersDAO = daoProvider.getOrdersDAO();
		List<Order> allOrders = null;
		
		try 
		{
			allOrders = ordersDAO.getOrdersByClient(clientId);
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return allOrders;
	}
	
	public boolean cancelOrder(int orderId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		OrdersDAO ordersDAO = daoProvider.getOrdersDAO();
		
		try
		{
			ordersDAO.cancelOrder(orderId);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	public HashMap<Integer, Order> getBusyDaysOfMonth(int photographerId, LocalDate date) throws ServiceException
	{
		HashMap<Integer, Order> daysOfMonth = new HashMap<Integer, Order>();
		List<Order> allOrders = null;
		try 
		{
			allOrders = getOrdersByPhotographer(photographerId);
		} 
		catch (ServiceException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		for(int i = 1; i <= date.lengthOfMonth(); i++)
		{
			Order orderOnDay = null;
			for(Order order : allOrders)
			{
				if(order.getDate().getDayOfMonth() == i && order.getDate().getMonth().equals(date.getMonth()) 
						&& order.getDate().getYear() == date.getYear() && order.getStatus().equals(APPROVED))
				{
					orderOnDay = order;
				}
			}
			daysOfMonth.put(i, orderOnDay);
		}
		
		return daysOfMonth;
	}
	
	public Timetable[] getTimetable()
	{
		Timetable[] timetable = new Timetable[3];
		
		LocalDate today = LocalDate.now();
		LocalDate nextMonth = today.plusMonths(1);
		LocalDate monthAfter = today.plusMonths(2);
		timetable[0] = new Timetable(today, today.lengthOfMonth());
		timetable[1] = new Timetable(nextMonth, nextMonth.lengthOfMonth());
		timetable[2] = new Timetable(monthAfter, monthAfter.lengthOfMonth());
		
		return timetable;
	}
}
