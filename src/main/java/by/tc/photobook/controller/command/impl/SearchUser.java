package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Command searches user by his username from the http request parameter and redirects to his page
 * 
 * @author Darya Minina
 * @see Command
 */
public class SearchUser implements Command
{
	private static final String USER_TO_SEARCH_PARAM = "user_to_search";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String LOAD_USER_PAGE = "Controller?command=loaduserpage&user=";
	
	/**
	 * Executes the searching user command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String userToSearch = request.getParameter(USER_TO_SEARCH_PARAM);
		if(userToSearch == null || userToSearch.isEmpty())
		{
			HttpSession session = request.getSession(false);
			String url = (String) session.getAttribute(URL_ATTRIBUTE);
			String parameter = (String) session.getAttribute(PARAM_ATTRIBUTE);
			
			if(parameter != null)
			{
				response.sendRedirect(url + URLEncoder.encode(parameter, "UTF-8"));
			}
			else
			{
				response.sendRedirect(url);
			}
		}
		else
		{
			response.sendRedirect(LOAD_USER_PAGE+URLEncoder.encode(userToSearch, "UTF-8"));
		}
	}
}
