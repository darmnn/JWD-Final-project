package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;

import by.tc.photobook.bean.Complaint;
import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.parser.PhotoInfoParser;
import by.tc.photobook.service.ComplaintsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Command adds new report written by a user
 * 
 * @author Darya Minina
 * @see Command
 */
public class NewComplaint implements Command 
{
	private static final String COMMENT_ID_PARAM = "comment_id";
	private static final String PHOTO_PARAM = "photo";
	private static final String COMPLAINT_TEXT_PARAM = "complaint_text";
	private static final String USER_PARAM = "user";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_PHOTO_PAGE_WITH_MESSAGE = "Controller?command=loadphotopage&message=";
	private static final String LOAD_PHOTO_PAGE = "Controller?command=loadphotopage";
	
	/**
	 * Executes the adding new complaint command
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
			UserInfo user = (UserInfo) session.getAttribute(USER_PARAM);
			String commentId = request.getParameter(COMMENT_ID_PARAM);
			String complaintText = request.getParameter(COMPLAINT_TEXT_PARAM);
			String photoInfo = request.getParameter(PHOTO_PARAM);
			PhotoInfoParser photoInfoParser = PhotoInfoParser.getInstance();
			Photo photo = photoInfoParser.getPhotoFromString(photoInfo);
			Complaint newComplaint;
			if(commentId == null) 
			{
				newComplaint = new Complaint(complaintText, user.getUsername(), null, photo);
			}
			else
			{
				Integer commentid = Integer.parseInt(commentId);
				newComplaint = new Complaint(complaintText, user.getUsername(), commentid, photo);
			}
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			ComplaintsService complaintsService = serviceProvider.getComplaintsService();
			
			try 
			{
				complaintsService.addComplaint(newComplaint);
				response.sendRedirect(LOAD_PHOTO_PAGE+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
			} 
			catch (ServiceException e) 
			{
				response.sendRedirect(LOAD_PHOTO_PAGE_WITH_MESSAGE + e.getDescription()+"&"+PHOTO_PARAM+"="+URLEncoder.encode(photo.toString(), "UTF-8"));
			}
		}
	}
}
