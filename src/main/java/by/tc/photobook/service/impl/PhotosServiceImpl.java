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
	private static final String NO_PHOTOS_MESSAGE = "No photos uploaded";
	private static final String ERROR_WHILE_ADDING_PHOTO = "Error while loading new photo occured! The photo wasn't uploaded";
	private static final String ERROR_WHILE_RATING = "Error while updating photo rating! Your grade wasn't saved";
	
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
				throw new ServiceException(NO_PHOTOS_MESSAGE);
			}
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
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
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
		}
		
		return userPhotos;
	}
	
	@Override
	public boolean addNewPhoto(Photo newPhoto) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		boolean uploaded = false;
		try
		{
			uploaded = photosDAO.addNewPhoto(newPhoto);
			if(!uploaded)
			{
				throw new ServiceException(ERROR_WHILE_ADDING_PHOTO);
			}
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
		}
		return uploaded;
	}
	
	public boolean updatePhotoRating(int photoId, int newRating) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		boolean updated = false;
		try
		{
			updated = photosDAO.updatePhotoRating(photoId, newRating);
			if(!updated)
			{
				throw new ServiceException(ERROR_WHILE_RATING);
			}
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
		}
		
		return updated;
	}
}
