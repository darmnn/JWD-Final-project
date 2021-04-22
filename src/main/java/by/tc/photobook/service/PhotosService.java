package by.tc.photobook.service;

import java.util.List;


import by.tc.photobook.bean.Photo;

/**
 * The interface to operate with photos
 * 
 * @author Darya Minina
 */
public interface PhotosService 
{
	/**
	 * Takes all photos from all photographers in the app
	 * 
	 * @return list of photos {@link Photo}
	 * @throws ServiceException
	 */
	public List<Photo> takeALl() throws ServiceException;
	
	/**
	 * Takes all photos from one user
	 * 
	 * @param username username of the author of photos
	 * @return list of photos {@link Photo}
	 * @throws ServiceException
	 */
	public List<Photo> takeUserPhotos(String username) throws ServiceException;
	
	/**
	 * Loads new photo
	 * 
	 * @param newPhoto {@link Photo} the description of the new photo
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean addNewPhoto(Photo newPhoto) throws ServiceException;
	
	/**
	 * Updates photo's rating when user rates it
	 * 
	 * @param photoId the id of the photo to rate
	 * @param newRating the result rating of the photo
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean updatePhotoRating(int photoId, int newRating) throws ServiceException;
	
	/**
	 * Deletes a photo
	 * 
	 * @param photoId the id of the photo to delete
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean deletePhoto(int photoId) throws ServiceException;
	
	/**
	 * Updates user's total rating according to the rates of all his photos
	 * 
	 * @param username the username of the user
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean updateUserRating(String username) throws ServiceException;
}
