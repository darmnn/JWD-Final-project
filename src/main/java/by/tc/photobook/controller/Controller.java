package by.tc.photobook.controller;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.command.CommandProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private static final String COMMAND_PARAM = "command";

    private final CommandProvider provider = new CommandProvider();

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        String name;
        Command command;

        name = request.getParameter(COMMAND_PARAM);
        command = provider.takeCommand(name);

        command.execute(request, response);
        
    }
}