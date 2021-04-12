package by.tc.photobook.service.impl;

import java.util.List;



import by.tc.photobook.bean.Photo;
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
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return allPhotos;
	}
	
	@Override
	public List<Photo> takeUserPhotos(String username) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		List<Photo> userPhotos = null;
		try
		{
			userPhotos = photosDAO.takeUserPhotos(username);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return userPhotos;
	}
	
	@Override
	public boolean addNewPhoto(Photo newPhoto) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		try
		{
			photosDAO.addNewPhoto(newPhoto);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		return true;
	}
	
	public boolean updatePhotoRating(int photoId, int newRating) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		try
		{
			photosDAO.updatePhotoRating(photoId, newRating);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	public boolean deletePhoto(int photoId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		try
		{
			photosDAO.deletePhoto(photoId);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	public boolean updateUserRating(String username) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		try
		{
			photosDAO.updateUserRating(username);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
}
