package by.tc.photobook.service;

import java.util.List;


import by.tc.photobook.bean.PhotoshootOption;

/**
 * The interface to operate with photoshoot options
 * 
 * @author Darya Minina
 */
public interface PhotoshootOptionsService 
{
	/**
	 * Gets all photoshoot options by one photographer
	 * 
	 * @param photographer the id of the photographer
	 * @return list of photoshoot option {@link PhotoshootOption}
	 * @throws ServiceException
	 */
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws ServiceException;
	
	/**
	 * Adds new photoshoot option created by a photographer
	 * 
	 * @param photographer the id of the photographer
	 * @param photoshootType the id of the photoshoot type
	 * @param price the price of photoshoot
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean addPhotoshootOption(int photographer, int photoshootType, double price) throws ServiceException;
	
	/**
	 * Deletes one photoshoot option
	 * 
	 * @param photoshootOption the of the photoshoot option to delete
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean deletePhotoshootOption(int photoshootOption) throws ServiceException;
	
	/**
	 * Edits one photoshoot option
	 * 
	 * @param photographer the id of the photographer
	 * @param photoshootType the id of the photoshoot type
	 * @param price the price of photoshoot
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean editPhotoshootOption(int photoshootOption, int photoshootType, double price) throws ServiceException;
}
