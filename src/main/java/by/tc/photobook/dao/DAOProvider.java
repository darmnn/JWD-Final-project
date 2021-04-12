package by.tc.photobook.dao;

import by.tc.photobook.dao.impl.SQLCommentsDAO;
import by.tc.photobook.dao.impl.SQLComplaintsDAO;
import by.tc.photobook.dao.impl.SQLOrdersDAO;
import by.tc.photobook.dao.impl.SQLPhotosDAO;
import by.tc.photobook.dao.impl.SQLPhotoshootOptionsDAO;
import by.tc.photobook.dao.impl.SQLPhotoshootTypesDAO;
import by.tc.photobook.dao.impl.SQLUserDAO;

public class DAOProvider 
{
	private static final DAOProvider instance = new DAOProvider();	
	
	private final UserDAO userdao = new SQLUserDAO();
	private final PhotosDAO photosDAO = new SQLPhotosDAO();
	private final CommentsDAO commentsDAO = new SQLCommentsDAO();
	private final PhotoshootOptionsDAO phOptionsDAO = new SQLPhotoshootOptionsDAO();
	private final PhotoshootTypesDAO phTypesDAO = new SQLPhotoshootTypesDAO();
	private final OrdersDAO ordersDAO = new SQLOrdersDAO();
	private final ComplaintsDAO complaintsDAO = new SQLComplaintsDAO();
	
	private DAOProvider() {}
	
	public static DAOProvider getInstance() {
		return instance;
	}

	public UserDAO getUserdao() {
		return userdao;
	}
	
	public PhotosDAO getPhotosDAO()
	{
		return photosDAO;
	}
	
	public CommentsDAO getCommentsDAO()
	{
		return commentsDAO;
	}
	
	public PhotoshootOptionsDAO getPhotoshootOptionsDAO()
	{
		return phOptionsDAO;
	}
	
	public PhotoshootTypesDAO getPhotoshootTypesDAO()
	{
		return phTypesDAO;
	}
	
	public OrdersDAO getOrdersDAO()
	{
		return ordersDAO;
	}
	
	public ComplaintsDAO getComplaintsDAO()
	{
		return complaintsDAO;
	}
}
