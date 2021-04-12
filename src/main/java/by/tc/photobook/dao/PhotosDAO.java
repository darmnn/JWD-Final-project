package by.tc.photobook.dao;

import java.util.List;


import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;

public interface PhotosDAO 
{
	public List<Photo> takeAll() throws DAOException;
	public List<Photo> takeUserPhotos(String username) throws DAOException;
	public boolean addNewPhoto(Photo newPhoto) throws DAOException;
	public boolean updatePhotoRating(int photoId, int newRating) throws DAOException;
	public boolean deletePhoto(int photoId) throws DAOException;
	public boolean updateUserRating(String username) throws DAOException;
}
