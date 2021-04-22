package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.parser.PhotoInfoParser;
import by.tc.photobook.service.CommentsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Command deletes one specified by a http request parameter comment
 * 
 * @author Darya Minina
 * @see Command
 */
public class DeleteComment implements Command
{
	private static final String COMMENT_ID_PARAM = "comment_id";
	private static final String USER_PARAM = "user";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_PHOTO_PAGE = "Controller?command=loadphotopage&photo=";
	private static final String PHOTO_PARAM = "photo";
	
	/**
	 * Executes the deleting comment command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(USER_PARAM) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String photoInfo = request.getParameter(PHOTO_PARAM);
			PhotoInfoParser photoInfoParser = PhotoInfoParser.getInstance();
			Photo photo = photoInfoParser.getPhotoFromString(photoInfo);
			
			int commentId = Integer.parseInt(request.getParameter(COMMENT_ID_PARAM));
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			CommentsService commentsService = serviceProvider.getCommentsService();
			
			try 
			{
				commentsService.deleteComment(commentId);
				response.sendRedirect(LOAD_PHOTO_PAGE+photo);
				
			} 
			catch (ServiceException e) 
			{
				response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+e.getDescription());
			}
		}
		
	}
	
}
