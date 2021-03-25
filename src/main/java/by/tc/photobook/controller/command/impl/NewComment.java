package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.parser.PhotoInfoParser;
import by.tc.photobook.service.CommentsService;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

public class NewComment implements Command
{
	private static final String COMMENT_ERROR_MESSAGE = "Your comment wasn't submitted, log in to leave comments!";
	private static final String USER_PARAM = "user";
	private static final String PHOTO_PARAM = "photo";
	private static final String COMMENT_PARAM = "comment_text";
	private static final String LOAD_PHOTO_PAGE_WITH_MESSAGE = "Controller?command=loadphotopage&message=";
	private static final String LOAD_PHOTO_PAGE = "Controller?command=loadphotopage";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(USER_PARAM) == null)
		{
			response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE+COMMENT_ERROR_MESSAGE);
		}
		
		UserInfo user = (UserInfo) session.getAttribute(USER_PARAM);
		int userId = user.getId();
		
		String photoInfo = request.getParameter(PHOTO_PARAM);
		PhotoInfoParser photoInfoParser = PhotoInfoParser.getInstance();
		Photo photo = photoInfoParser.getPhotoFromString(photoInfo);
		int photoId = photo.getId();
		
		String commentText = request.getParameter(COMMENT_PARAM);
		LocalDate date = LocalDate.now();
		String authorPic = user.getProfilePicPath();
		
		Comment newComment = new Comment(commentText, user.getUsername(), date, authorPic);
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CommentsService commentsService = serviceProvider.getCommentsService();
		boolean commentAdded = false;
		
		try 
		{
			commentAdded = commentsService.loadNewComment(newComment, photoId, userId);
			response.sendRedirect(LOAD_PHOTO_PAGE+"&"+PHOTO_PARAM+"="+photo);
		} 
		catch (ServiceException e) 
		{
			response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE + e.getMessage()+"&"+PHOTO_PARAM+"="+photo);
		}
	}
}
