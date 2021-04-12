package by.tc.photobook.service;

import java.time.LocalDate;
import java.util.List;

import by.tc.photobook.bean.Order;

public interface OrdersService 
{
	public boolean addOrder(int user, int photoshootOption, LocalDate date) throws ServiceException;
	public List<Order> getOrdersByPhotographer(int photographerId) throws ServiceException;
	public boolean processOrder(int orderId, String action) throws ServiceException;
	public List<Order> getOrdersByClient(int clientId) throws ServiceException;
	public boolean cancelOrder(int orderId) throws ServiceException; 
}