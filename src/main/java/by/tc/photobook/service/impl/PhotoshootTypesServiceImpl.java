package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.PhotoshootType;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.PhotoshootTypesDAO;
import by.tc.photobook.service.PhotoshootTypesService;
import by.tc.photobook.service.ServiceException;

public class PhotoshootTypesServiceImpl implements PhotoshootTypesService
{
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
			throw new ServiceException(e);
		}
		
		return allTypes;
	}
}
