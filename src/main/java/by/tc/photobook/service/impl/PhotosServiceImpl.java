package by.tc.photobook.service.impl;

import java.util.List;



import by.tc.photobook.bean.Photo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.PhotosDAO;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;

/**
 * The implementation of operations with photos
 * 
 * @author Darya Minina
 */
public class PhotosServiceImpl implements PhotosService
{
	/**
	 * Takes all photos from all photographers in the app
	 * 
	 * @return list of photos {@link Photo}
	 * @throws ServiceException
	 */
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
	
	/**
	 * Takes all photos from one user
	 * 
	 * @param username username of the author of photos
	 * @return list of photos {@link Photo}
	 * @throws ServiceException
	 */
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
	
	/**
	 * Loads new photo
	 * 
	 * @param newPhoto {@link Photo} the description of the new photo
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
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
	
	/**
	 * Updates photo's rating when user rates it
	 * 
	 * @param photoId the id of the photo to rate
	 * @param newRating the result rating of the photo
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
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
	
	/**
	 * Deletes a photo
	 * 
	 * @param photoId the id of the photo to delete
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
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
	
	/**
	 * Updates user's total rating according to the rates of all his photos
	 * 
	 * @param username the username of the user
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
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
