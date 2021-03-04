package by.tc.photobook.dao;

import by.tc.photobook.dao.impl.SQLUserDAO;

public class DAOProvider 
{
	private static final DAOProvider instance = new DAOProvider();	
	
	private final UserDAO userdao = new SQLUserDAO();
	
	private DAOProvider() {}
	
	public static DAOProvider getInstance() {
		return instance;
	}

	public UserDAO getUserdao() {
		return userdao;
	}
}
