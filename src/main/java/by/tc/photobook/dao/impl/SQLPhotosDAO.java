package by.tc.photobook.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.dao.PhotosDAO;

public class SQLPhotosDAO implements PhotosDAO
{
	@Override
	public List<Photo> takeAll()
	{
		List<Photo> allPhotos = new ArrayList<Photo>();
		return allPhotos;
	}
}
