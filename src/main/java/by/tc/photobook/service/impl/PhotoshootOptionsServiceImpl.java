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
