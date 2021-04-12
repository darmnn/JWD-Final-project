package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SearchUser implements Command
{
	private static final String USER_TO_SEARCH_PARAM = "user_to_search";
	private static final String MESSAGE_PARAM = "message";
	private static final String URL_ATTRIBUTE = "url";
	private static final String NO_USER_MESSAGE = "message.no_such_user";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String LOAD_USER_PAGE = "Controller?command=loaduserpage&user=";
	
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
