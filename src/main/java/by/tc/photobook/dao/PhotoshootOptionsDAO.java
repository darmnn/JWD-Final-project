package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.PhotoshootOption;

public interface PhotoshootOptionsDAO 
{
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws DAOException;
}
