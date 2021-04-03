package by.tc.photobook.service.impl;

import java.time.LocalDate;
import java.util.List;

import by.tc.photobook.bean.Order;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.OrdersDAO;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.ServiceException;

public class OrdersServiceImpl implements OrdersService
{
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
}
