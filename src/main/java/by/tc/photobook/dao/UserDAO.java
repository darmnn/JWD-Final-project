package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.service.ServiceException;

public interface UserDAO 
{
	public boolean registration(UserInfo userInfo) throws DAOException;
	public UserInfo authorization(UserInfo userInfo) throws DAOException;
	public boolean updateProfileDesc(String username, String newProfileDesc) throws DAOException;
	public boolean updateProfilePic(String username, String newProfilePicPath) throws DAOException;
	public UserInfo getInfoByUsername(String username) throws DAOException;
	public List<UserInfo> getAllUsers() throws DAOException;
	public boolean blockUnlock(int userId, int action) throws DAOException;
}
