package by.tc.photobook.controller.command.impl;

import java.io.IOException;



import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.parser.PhotoInfoParser;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

/**
 * Command gives a photo the grade from 1 to 5
 * 
 * @author Darya Minina
 * @see Command
 */
public class RatePhoto implements Command
{
	private static final String USER_PARAM = "user";
	private static final String PHOTO_PARAM = "photo";
	private static final String RATING_PARAM = "rating";
	private static final String LOAD_PHOTO_PAGE_WITH_MESSAGE = "Controller?command=loadphotopage&message=";
	private static final String LOAD_PHOTO_PAGE = "Controller?command=loadphotopage";
	private static final String EMPTY_RATING_ERROR = "message.empty_rating";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	
	/**
	 * Executes the rating photo command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String photoInfo = request.getParameter(PHOTO_PARAM);
		PhotoInfoParser photoInfoParser = PhotoInfoParser.getInstance();
		Photo photo = photoInfoParser.getPhotoFromString(photoInfo);
		
		if(session == null || session.getAttribute(USER_PARAM) == null)
		 {
			response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
		}
		else
		{
			if(!request.getParameter(RATING_PARAM).equals("Оценить фото") && 
					!request.getParameter(RATING_PARAM).equals("Rate photo"))
			{
				
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
					photosService.updateUserRating(photo.getPhotographer());
					response.sendRedirect(LOAD_PHOTO_PAGE+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
				}
				catch (ServiceException e) 
				{
					response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE + e.getDescription()+"&"+PHOTO_PARAM+"="+photo);
				}
			}
			else
			{
				response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE+EMPTY_RATING_ERROR+"&"+PHOTO_PARAM+"="+photo);
			}
		}
	}
}
