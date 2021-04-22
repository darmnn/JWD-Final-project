package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.Photo;

/**
 * The interface for working with database table that stored all photos
 * 
 * @author Darya Minina
 */
public interface PhotosDAO 
{
	/**
	 * Takes all photos from all photographers in the app
	 * 
	 * @return list of photos {@link Photo}
	 * @throws DAOException
	 */
	public List<Photo> takeAll() throws DAOException;
	
	/**
	 * Takes all photos from one user
	 * 
	 * @param username username of the author of photos
	 * @return list of photos {@link Photo}
	 * @throws DAOException
	 */
	public List<Photo> takeUserPhotos(String username) throws DAOException;
	
	/**
	 * Loads new photo
	 * 
	 * @param newPhoto {@link Photo} the description of the new photo
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean addNewPhoto(Photo newPhoto) throws DAOException;
	
	/**
	 * Updates photo's rating when user rates it
	 * 
	 * @param photoId the id of the photo to rate
	 * @param newRating the result rating of the photo
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean updatePhotoRating(int photoId, int newRating) throws DAOException;
	
	/**
	 * Deletes a photo
	 * 
	 * @param photoId the id of the photo to delete
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean deletePhoto(int photoId) throws DAOException;
	
	/**
	 * Updates user's total rating according to the rates of all his photos
	 * 
	 * @param username the username of the user
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean updateUserRating(String username) throws DAOException;
}
