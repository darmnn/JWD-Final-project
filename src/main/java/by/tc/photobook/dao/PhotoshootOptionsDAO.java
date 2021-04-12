package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.PhotoshootOption;

public interface PhotoshootOptionsDAO 
{
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws DAOException;
	public boolean addPhotoshootOption(int photographer, int photoshootType, double price) throws DAOException;
	public boolean deletePhotoshootOption(int photoshootOption) throws DAOException;
	public boolean editPhotoshootOption(int photoshootOption, int photoshootType, double price) throws DAOException;
}
