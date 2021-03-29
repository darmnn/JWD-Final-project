package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.PhotoshootOption;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.PhotoshootOptionsDAO;
import by.tc.photobook.service.PhotoshootOptionsService;
import by.tc.photobook.service.ServiceException;

public class PhotoshootOptionsServiceImpl implements PhotoshootOptionsService
{
	private static final String LOADING_OPTIONS_ERROR = "Server error while loading photoshoot options!";
	
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotoshootOptionsDAO phOptionsDAO = daoProvider.getPhotoshootOptionsDAO();
		
		List<PhotoshootOption> allOptions = null;
		
		try
		{
			allOptions = phOptionsDAO.getPhotoshootOptions(photographer);
			if(allOptions == null)
			{
				throw new ServiceException(LOADING_OPTIONS_ERROR);
			}
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
		}
		
		return allOptions;
	}
	
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
			throw new ServiceException(e);
		}
		
		return true;
	}
	
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
			throw new ServiceException(e);
		}
		
		return true;
	}
}
