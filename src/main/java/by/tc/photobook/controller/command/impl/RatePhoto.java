package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.http.*;
import jakarta.servlet.*;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.parser.PhotoInfoParser;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

public class RatePhoto implements Command
{
	private static final String ERROR_MESSAGE = "Your rate wasn't submitted, log in to rate photos!";
	private static final String USER_PARAM = "user";
	private static final String PHOTO_PARAM = "photo";
	private static final String RATING_PARAM = "rating";
	private static final String LOAD_PHOTO_PAGE_WITH_MESSAGE = "Controller?command=loadphotopage&message=";
	private static final String LOAD_PHOTO_PAGE = "Controller?command=loadphotopage";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(USER_PARAM) == null)
		{
			response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE+ERROR_MESSAGE);
		}
		
		String photoInfo = request.getParameter(PHOTO_PARAM);
		PhotoInfoParser photoInfoParser = PhotoInfoParser.getInstance();
		Photo photo = photoInfoParser.getPhotoFromString(photoInfo);
		int photoId = photo.getId();
		
		int rating = Integer.parseInt(request.getParameter(RATING_PARAM));
		if(photo.getRating() != 0)
		{
			rating = (rating+photo.getRating())/2;
		}
		photo.setRating(rating);
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
    	PhotosService photosService = serviceProvider.getPhotosService();
		
		try
		{
			photosService.updatePhotoRating(photoId, rating);
			response.sendRedirect(LOAD_PHOTO_PAGE+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
		}
		catch (ServiceException e) 
		{
			response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE + e.getMessage()+"&"+PHOTO_PARAM+"="+photo);
		}
	}
}
