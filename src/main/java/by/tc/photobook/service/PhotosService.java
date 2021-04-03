package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.Photo;

public interface PhotosService 
{
	public List<Photo> takeALl() throws ServiceException;
	public List<Photo> takeUserPhotos(String username) throws ServiceException;
	public boolean addNewPhoto(Photo newPhoto) throws ServiceException;
	public boolean updatePhotoRating(int photoId, int newRating) throws ServiceException;
	public boolean deletePhoto(int photoId) throws ServiceException;
	public boolean updateUserRating(String username) throws ServiceException;
}
