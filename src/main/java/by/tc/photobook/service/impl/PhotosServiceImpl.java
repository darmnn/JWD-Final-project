package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.PhotosDAO;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;

public class PhotosServiceImpl implements PhotosService
{
	@Override
	public List<Photo> takeALl() throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		PhotosDAO photosDAO = daoProvider.getPhotosDAO();
		
		return photosDAO.takeAll();
	}
}
