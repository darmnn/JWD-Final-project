package by.tc.photobook.controller.command;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


public interface Command
{
    public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException;
}