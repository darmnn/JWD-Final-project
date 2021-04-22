package by.tc.photobook.service;

import java.util.List;


import by.tc.photobook.bean.PhotoshootType;

/**
 * The interface to operate with photoshoot types
 * 
 * @author Darya Minina
 */
public interface PhotoshootTypesService 
{
	/**
	 * Gets list of all types that exist in the system
	 * 
	 * @return list of photoshoot types {@link PhotoshootType}
	 * @throws ServiceException
	 */
	public List<PhotoshootType> takeAll() throws ServiceException;
	
	/**
	 * Adds a new photoshoot type
	 * 
	 * @param type the name of a new type
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean addNewType(String type) throws ServiceException;
}
