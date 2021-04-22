package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.PhotoshootOption;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.PhotoshootOptionsDAO;
import by.tc.photobook.service.PhotoshootOptionsService;
import by.tc.photobook.service.ServiceException;

/**
 * The implementation of operations with photoshoot options
 * 
 * @author Darya Minina
 */
public class PhotoshootOptionsServiceImpl implements PhotoshootOptionsService
{
	/**
	 * Gets all photoshoot options by one photographer
	 * 
	 * @param photographer the id of the photographer
	 * @return list of photoshoot option {@link PhotoshootOption}
	 * @throws ServiceException
	 */
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotoshootOptionsDAO phOptionsDAO = daoProvider.getPhotoshootOptionsDAO();
		
		List<PhotoshootOption> allOptions = null;
		
		try
		{
			allOptions = phOptionsDAO.getPhotoshootOptions(photographer);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return allOptions;
	}
	
	/**
	 * Adds new photoshoot option created by a photographer
	 * 
	 * @param photographer the id of the photographer
	 * @param photoshootType the id of the photoshoot type
	 * @param price the price of photoshoot
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean addPhotoshootOption(int photographer, int photoshootType, double price) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotoshootOptionsDAO phOptionsDAO = daoProvider.getPhotoshootOptionsDAO();
		
		try 
		{
			phOptionsDAO.addPhotoshootOption(photographer, photoshootType, price);
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	/**
	 * Deletes one photoshoot option
	 * 
	 * @param photoshootOption the of the photoshoot option to delete
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean deletePhotoshootOption(int photoshootOption) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotoshootOptionsDAO phOptionsDAO = daoProvider.getPhotoshootOptionsDAO();
		
		try
		{
			phOptionsDAO.deletePhotoshootOption(photoshootOption);
		}
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	/**
	 * Edits one photoshoot option
	 * 
	 * @param photographer the id of the photographer
	 * @param photoshootType the id of the photoshoot type
	 * @param price the price of photoshoot
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean editPhotoshootOption(int photoshootOption, int photoshootType, double price) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotoshootOptionsDAO phOptionsDAO = daoProvider.getPhotoshootOptionsDAO();
		
		try 
		{
			phOptionsDAO.editPhotoshootOption(photoshootOption, photoshootType, price);
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(),e);
		}
		
		return true;
	}
}
