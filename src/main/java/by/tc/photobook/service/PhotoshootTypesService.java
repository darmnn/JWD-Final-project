package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.PhotoshootType;

public interface PhotoshootTypesService 
{
	public List<PhotoshootType> takeAll() throws ServiceException;
	public boolean addNewType(String type) throws ServiceException;
}
