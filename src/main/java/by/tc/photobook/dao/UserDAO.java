package by.tc.photobook.dao;

import by.tc.photobook.bean.UserInfo;

public interface UserDAO 
{
	public boolean registration(UserInfo userInfo) throws DAOException;
	public UserInfo authorization(UserInfo userInfo) throws DAOException;
	public boolean updateProfileDesc(String username, String newProfileDesc) throws DAOException;
	public boolean updateProfilePic(String username, String newProfilePicPath) throws DAOException;
	public UserInfo getInfoByUsername(String username) throws DAOException;
}
