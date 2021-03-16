package by.tc.photobook.controller.command.impl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

import java.io.IOException;
import java.util.List;

public class LoadMainPage implements Command
{
	private static final String MAIN_PAGE_PATH = "/WEB-INF/jsp/main_page.jsp";
	private static final String PHOTOS_ATTR = "photos";
	private static final String MESSAGE_PARAM = "message";
	
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	ServiceProvider serviceProvider = ServiceProvider.getInstance();
    	PhotosService photosService = serviceProvider.getPhotosService();
    	
    	try
    	{
    		List<Photo> allPhotos = photosService.takeALl();
    		
    		request.setAttribute(PHOTOS_ATTR, allPhotos);
    		request.setAttribute(MESSAGE_PARAM, request.getParameter(MESSAGE_PARAM));

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(MAIN_PAGE_PATH);
            requestDispatcher.forward(request, response);
    	}
    	catch(ServiceException e)
    	{
    		String errorMessage = e.getMessage();
    		request.setAttribute(MESSAGE_PARAM, errorMessage);
    		RequestDispatcher requestDispatcher = request.getRequestDispatcher(MAIN_PAGE_PATH);
            requestDispatcher.forward(request, response);
    	}
    	
    }
}