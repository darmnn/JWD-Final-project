package by.tc.photobook.controller.command.impl;

import java.io.IOException;




import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import by.tc.photobook.controller.command.Command;

/**
 * Command ends current user's session
 * 
 * @author Darya Minina
 * @see Command
 */
public class Logout implements Command 
{
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage";
	private static final String AUTH_ATTRIBUTE = "auth";
	
	/**
	 * Executes the logout command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session != null)
		{
			session.removeAttribute(AUTH_ATTRIBUTE);
		}
		
		response.sendRedirect(LOAD_MAIN_PAGE);
	}
}
