package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.PhotosDAO;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;

public class PhotosServiceImpl implements PhotosService
{
	@Override
	public List<Photo> takeALl() throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		List<Photo> allPhotos = null;
		try
		{
			allPhotos = photosDAO.takeAll();
			if(allPhotos.isEmpty())
			{
				throw new ServiceException("No photos uploaded");
			}
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return allPhotos;
	}
	
	@Override
	public List<Photo> takeUserPhotos(UserInfo user) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		List<Photo> userPhotos = null;
		try
		{
			userPhotos = photosDAO.takeUserPhotos(user);
			if(userPhotos.isEmpty())
			{
				throw new ServiceException("No photos uploaded");
			}
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getMessage());
		}
		
		return userPhotos;
	}
}
