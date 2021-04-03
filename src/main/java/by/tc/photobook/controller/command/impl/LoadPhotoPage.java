package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.util.List;


import by.tc.photobook.bean.Comment;
import by.tc.photobook.bean.Photo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.parser.PhotoInfoParser;
import by.tc.photobook.service.CommentsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoadPhotoPage implements Command
{
	private static final String PHOTO_PAGE_PATH = "/WEB-INF/jsp/photo_page.jsp";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String LOAD_PHOTO_PAGE = "Controller?command=loadphotopage&photo=";
	private static final String LOAD_PHOTO_PAGE_WITH_MESSAGE = "Controller?command=loadphotopage&message=";
	private static final String PHOTO_PARAM = "photo";
	private static final String PHOTO_ATTRIBUTE = "&photo=";
	private static final String MESSAGE_PARAM = "message";
	private static final String COMMENTS_ATTR = "comments";
	
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		String photoInfo = request.getParameter(PHOTO_PARAM);
		PhotoInfoParser photoInfoParser = PhotoInfoParser.getInstance();
		Photo photo = photoInfoParser.getPhotoFromString(photoInfo);
		String message = request.getParameter(MESSAGE_PARAM);
		
		HttpSession session = request.getSession(true);
		if(message != null)
		{
			session.setAttribute(URL_ATTRIBUTE, LOAD_PHOTO_PAGE_WITH_MESSAGE+message+PHOTO_ATTRIBUTE);
		}
		else
		{
			session.setAttribute(URL_ATTRIBUTE, LOAD_PHOTO_PAGE);
		}
		session.setAttribute(PARAM_ATTRIBUTE, photoInfo);
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		CommentsService commentsService = serviceProvider.getCommentsService();
		List<Comment> comments = null;
		
		try 
		{
			comments = commentsService.takePhotoComments(photo.getId());
		} 
		catch (ServiceException e) 
		{
			request.setAttribute(MESSAGE_PARAM, e.getDescription());
			
		}
		finally
		{
			request.setAttribute(PHOTO_PARAM, photo);
			request.setAttribute(COMMENTS_ATTR, comments);
			if(message != null) 
			{
				request.setAttribute(MESSAGE_PARAM, message);
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PHOTO_PAGE_PATH);
			requestDispatcher.forward(request, response);
		}
		
	}
}
