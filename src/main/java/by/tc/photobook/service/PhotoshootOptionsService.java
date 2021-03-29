package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.PhotoshootOption;

public interface PhotoshootOptionsService 
{
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws ServiceException;
}
