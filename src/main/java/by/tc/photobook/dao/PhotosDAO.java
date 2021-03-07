package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.Photo;

public interface PhotosDAO 
{
	public List<Photo> takeAll() throws DAOException;
}
