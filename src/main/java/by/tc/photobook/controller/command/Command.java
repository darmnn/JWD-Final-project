package by.tc.photobook.controller.command;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/** 
 * An interface that describes common behavior of all comands
 * @author Darya Minina
*/
public interface Command
{
    public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException;
}