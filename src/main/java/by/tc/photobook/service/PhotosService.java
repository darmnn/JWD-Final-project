package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.Photo;

public interface PhotosService 
{
	public List<Photo> takeALl() throws ServiceException;
}
