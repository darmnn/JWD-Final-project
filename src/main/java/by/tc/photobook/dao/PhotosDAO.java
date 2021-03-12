package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;

public interface PhotosDAO 
{
	public List<Photo> takeAll() throws DAOException;
	public List<Photo> takeUserPhotos(UserInfo user) throws DAOException;
}
