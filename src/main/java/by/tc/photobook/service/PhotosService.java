package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;

public interface PhotosService 
{
	public List<Photo> takeALl() throws ServiceException;
	public List<Photo> takeUserPhotos(UserInfo user) throws ServiceException;
	public boolean addNewPhoto(Photo newPhoto) throws ServiceException;
}
