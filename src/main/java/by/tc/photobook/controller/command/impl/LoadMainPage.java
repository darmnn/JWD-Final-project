package by.tc.photobook.controller.command.impl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tc.photobook.controller.command.Command;

import java.io.IOException;

public class LoadMainPage implements Command
{
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main_page.jsp");
        requestDispatcher.forward(request, response);
    }
}