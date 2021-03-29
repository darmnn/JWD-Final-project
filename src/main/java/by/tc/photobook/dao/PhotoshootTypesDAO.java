package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.PhotoshootType;

public interface PhotoshootTypesDAO 
{
	public List<PhotoshootType> takeAll() throws DAOException;
}
