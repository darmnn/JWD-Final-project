package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.PhotoshootOption;

/**
 * The interface for working with database table that stored all photoshoot options
 * 
 * @author Darya Minina
 */
public interface PhotoshootOptionsDAO 
{
	/**
	 * Gets all photoshoot options by one photographer
	 * 
	 * @param photographer the id of the photographer
	 * @return list of photoshoot option {@link PhotoshootOption}
	 * @throws DAOException
	 */
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws DAOException;
	
	/**
	 * Adds new photoshoot option created by a photographer
	 * 
	 * @param photographer the id of the photographer
	 * @param photoshootType the id of the photoshoot type
	 * @param price the price of photoshoot
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean addPhotoshootOption(int photographer, int photoshootType, double price) throws DAOException;
	
	/**
	 * Deletes one photoshoot option
	 * 
	 * @param photoshootOption the of the photoshoot option to delete
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean deletePhotoshootOption(int photoshootOption) throws DAOException;
	
	/**
	 * Edits one photoshoot option
	 * 
	 * @param photographer the id of the photographer
	 * @param photoshootType the id of the photoshoot type
	 * @param price the price of photoshoot
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean editPhotoshootOption(int photoshootOption, int photoshootType, double price) throws DAOException;
}
