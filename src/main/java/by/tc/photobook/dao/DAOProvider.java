package by.tc.photobook.dao;

import by.tc.photobook.dao.impl.SQLPhotosDAO;
import by.tc.photobook.dao.impl.SQLUserDAO;

public class DAOProvider 
{
	private static final DAOProvider instance = new DAOProvider();	
	
	private final UserDAO userdao = new SQLUserDAO();
	private final PhotosDAO photosDAO = new SQLPhotosDAO();
	
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
}
