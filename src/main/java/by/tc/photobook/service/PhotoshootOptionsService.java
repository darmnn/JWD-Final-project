package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.PhotoshootOption;

public interface PhotoshootOptionsService 
{
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws ServiceException;
	public boolean addPhotoshootOption(int photographer, int photoshootType, double price) throws ServiceException;
	public boolean deletePhotoshootOption(int photoshootOption) throws ServiceException;
	public boolean editPhotoshootOption(int photoshootOption, int photoshootType, double price) throws ServiceException;
}
