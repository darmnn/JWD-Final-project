package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.PhotoshootType;

/**
 * The interface for working with database table that stored all photoshoot types
 * 
 * @author Darya Minina
 */
public interface PhotoshootTypesDAO 
{
	/**
	 * Gets list of all types that exist in the system
	 * 
	 * @return list of photoshoot types {@link PhotoshootType}
	 * @throws DAOException
	 */
	public List<PhotoshootType> takeAll() throws DAOException;
	
	/**
	 * Adds a new photoshoot type
	 * 
	 * @param type the name of a new type
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean addNewType(String type) throws DAOException;
}
