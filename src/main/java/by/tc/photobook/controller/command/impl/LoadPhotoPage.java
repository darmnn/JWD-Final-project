package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.bean.Photo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.parser.PhotoInfoParser;
import by.tc.photobook.service.CommentsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

public class LoadPhotoPage implements Command
{
	private static final String PHOTO_PAGE_PATH = "/WEB-INF/jsp/photo_page.jsp";
	private static final String URL_ATTRIBUTE = "url";
	private static final String LOAD_PHOTO_PAGE = "Controller?command=loadphotopage";
	private static final String PHOTO_PARAM = "photo";
	private static final String MESSAGE_PARAM = "message";
	private static final String NO_COMMENTS_MESSAGE = "No commemts yet";
	private static final String COMMENTS_ATTR = "comments";
	
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		String photoInfo = request.getParameter(PHOTO_PARAM);
		PhotoInfoParser photoInfoParser = PhotoInfoParser.getInstance();
		Photo photo = photoInfoParser.getPhotoFromString(photoInfo);
		
		HttpSession session = request.getSession(true);
		session.setAttribute(URL_ATTRIBUTE, LOAD_PHOTO_PAGE+"&"+PHOTO_PARAM+"="+photoInfo);
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		CommentsService commentsService = serviceProvider.getCommentsService();
		List<Comment> comments = null;
		
		try 
		{
			comments = commentsService.takePhotoComments(photo.getId());
		} 
		catch (ServiceException e) 
		{
			request.setAttribute(MESSAGE_PARAM, NO_COMMENTS_MESSAGE);
			
		}
		finally
		{
			request.setAttribute(PHOTO_PARAM, photo);
			request.setAttribute(COMMENTS_ATTR, comments);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PHOTO_PAGE_PATH);
			requestDispatcher.forward(request, response);
		}
		
	}
}
