package by.tc.photobook.dao;

import java.time.LocalDate;
import java.util.List;

import by.tc.photobook.bean.Order;

public interface OrdersDAO 
{
	public boolean addOrder(int user, int photoshootOption, LocalDate date) throws DAOException;
	public List<Order> getOrdersByPhotographer(int photographerId) throws DAOException;
	public List<Order> getOrdersByClient(int clientId) throws DAOException;
	public boolean processOrder(int orderId, String action) throws DAOException;
	public boolean cancelOrder(int orderId) throws DAOException;
}