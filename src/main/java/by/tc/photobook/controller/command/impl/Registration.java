package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;

public class Registration implements Command
{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String checkValue = request.getParameter("checkbox");
        boolean isPhotographer = false;
        
        if(checkValue.equals("on")) 
        {
        	isPhotographer = true;
        }
        
        UserInfo userInfo = new UserInfo(username, email, password, isPhotographer);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        
        if(userService.registration(userInfo))
        {
        	response.sendRedirect("Controller?command=loadmainpage");
        }
    }
}
