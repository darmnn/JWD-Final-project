package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.PhotoshootType;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.PhotoshootTypesDAO;
import by.tc.photobook.service.PhotoshootTypesService;
import by.tc.photobook.service.ServiceException;

/**
 * The implementation of operations with photoshoot types
 * 
 * @author Darya Minina
 */
public class PhotoshootTypesServiceImpl implements PhotoshootTypesService
{
	/**
	 * Gets list of all types that exist in the system
	 * 
	 * @return list of photoshoot types {@link PhotoshootType}
	 * @throws ServiceException
	 */
	public List<PhotoshootType> takeAll() throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotoshootTypesDAO phTypesDAO = daoProvider.getPhotoshootTypesDAO();
		
		List<PhotoshootType> allTypes = null;
		
		try
		{
			allTypes = phTypesDAO.takeAll();
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return allTypes;
	}
	
	/**
	 * Adds a new photoshoot type
	 * 
	 * @param type the name of a new type
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean addNewType(String type) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotoshootTypesDAO phTypesDAO = daoProvider.getPhotoshootTypesDAO();
		
		try
		{
			phTypesDAO.addNewType(type);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
}
