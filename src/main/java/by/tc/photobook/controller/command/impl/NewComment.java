package by.tc.photobook.controller.command.impl;

import java.io.IOException;




import java.net.URLEncoder;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.parser.PhotoInfoParser;
import by.tc.photobook.service.CommentsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

/**
 * Command adds new comment written by a user
 * 
 * @author Darya Minina
 * @see Command
 */
public class NewComment implements Command
{
	private static final String COMMENT_ERROR_MESSAGE = "message.comment_error";
	private static final String USER_PARAM = "user";
	private static final String PHOTO_PARAM = "photo";
	private static final String COMMENT_PARAM = "comment_text";
	private static final String LOAD_PHOTO_PAGE_WITH_MESSAGE = "Controller?command=loadphotopage&message=";
	private static final String LOAD_PHOTO_PAGE = "Controller?command=loadphotopage";
	private static final String EMPTY_COMMENT_ERROR = "message.empty_comment";
	
	/**
	 * Executes the adding new comment command
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
			response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE+COMMENT_ERROR_MESSAGE+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
		}
		else
		{
			String commentText = request.getParameter(COMMENT_PARAM);
			if(!commentText.isEmpty())
			{
				UserInfo user = (UserInfo) session.getAttribute(USER_PARAM);
				int userId = user.getId();
				int photoId = photo.getId();
				
				LocalDate date = LocalDate.now();
				String authorPic = user.getProfilePicPath();
				
				Comment newComment = new Comment(commentText, user.getUsername(), date, authorPic);
				
				ServiceProvider serviceProvider = ServiceProvider.getInstance();
		        CommentsService commentsService = serviceProvider.getCommentsService();
				
				try 
				{
					commentsService.loadNewComment(newComment, photoId, userId);
					response.sendRedirect(LOAD_PHOTO_PAGE+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
				} 
				catch (ServiceException e) 
				{
					response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE + e.getDescription()+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
				}
			}
			else
			{
				response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE+EMPTY_COMMENT_ERROR+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
			}
		}
	}
}
