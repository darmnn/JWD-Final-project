package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import java.util.List;


import by.tc.photobook.bean.Photo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.EmailService;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class LoadMainPage implements Command
{
	private static final String MAIN_PAGE_PATH = "/WEB-INF/jsp/main_page.jsp";
	private static final String PHOTOS_ATTRIBUTE = "photos";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String MESSAGE_PARAM = "message";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage";
	private static final String URL_ATTRIBUTE = "url";
	
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	HttpSession session = request.getSession(true);
    	
    	if(request.getParameter(MESSAGE_PARAM) != null)
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_MAIN_PAGE_WITH_MESSAGE+request.getParameter(MESSAGE_PARAM));
    	}
    	else
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_MAIN_PAGE);
    	}
    	session.setAttribute(PARAM_ATTRIBUTE, null);
    	
    	ServiceProvider serviceProvider = ServiceProvider.getInstance();
    	PhotosService photosService = serviceProvider.getPhotosService();
    	
    	try
    	{
    		List<Photo> allPhotos = photosService.takeALl();
    		
    		request.setAttribute(PHOTOS_ATTRIBUTE, allPhotos);
    		request.setAttribute(MESSAGE_PARAM, request.getParameter(MESSAGE_PARAM));
    	}
    	catch(ServiceException e)
    	{
    		String errorMessage = e.getDescription();
    		request.setAttribute(MESSAGE_PARAM, errorMessage);
    	}
    	finally
    	{
    		RequestDispatcher requestDispatcher = request.getRequestDispatcher(MAIN_PAGE_PATH);
            requestDispatcher.forward(request, response);
    	}
    }
}