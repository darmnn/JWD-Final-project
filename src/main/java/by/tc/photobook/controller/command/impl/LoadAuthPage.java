package by.tc.photobook.controller.command.impl;

import java.io.IOException;




import by.tc.photobook.controller.command.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * Command displays an authorization page
 * 
 * @author Darya Minina
 * @see Command
 */
public class LoadAuthPage implements Command
{
	private static final String AUTH_PAGE_PATH = "/WEB-INF/jsp/authorization.jsp";
	private static final String MESSAGE_PARAM = "message";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String LOAD_AUTH_PAGE_WITH_MESSAGE = "Controller?command=loadauthpage&message=";
	private static final String LOAD_AUTH_PAGE = "Controller?command=loadauthpage";
	
	/**
	 * Executes the loading authorization page command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		HttpSession session = request.getSession(true);
		String message = request.getParameter(MESSAGE_PARAM);
		
		if(message != null)
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_AUTH_PAGE_WITH_MESSAGE+message);
    	}
    	else
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_AUTH_PAGE);
    	}
		session.setAttribute(PARAM_ATTRIBUTE, null);
		
		if(message != null)
		{
			request.setAttribute(MESSAGE_PARAM, message);
		}
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(AUTH_PAGE_PATH);
        requestDispatcher.forward(request, response);
    }
}
