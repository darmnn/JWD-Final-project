package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ComplaintsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Command marks a report as viewed by administrator
 * 
 * @author Darya Minina
 * @see Command
 */
public class ViewComplaint implements Command
{
	private static final String LOAD_COMPLAINTS_PAGE = "Controller?command=loadcomplaintspage";
	private static final String LOAD_COMPLAINTS_PAGE_WITH_MESSAGE = "Controller?command=loadcomplaintspage&message=";
	private static final String COMPLAINT_ID_PARAM = "complaint_id";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	
	/**
	 * Executes the marking report as viewed command
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
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null 
				|| session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			int complaintId = Integer.parseInt(request.getParameter(COMPLAINT_ID_PARAM));
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			ComplaintsService complaintsService = serviceProvider.getComplaintsService();
			
			try
			{
				complaintsService.viewComplaint(complaintId);
				response.sendRedirect(LOAD_COMPLAINTS_PAGE);
			}
			catch(ServiceException e)
			{
				response.sendRedirect(LOAD_COMPLAINTS_PAGE_WITH_MESSAGE+e.getDescription());
			}
		}
	}
	
}
