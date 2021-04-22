package by.tc.photobook.controller;

import java.io.IOException;



import by.tc.photobook.controller.command.Command;
import by.tc.photobook.controller.command.CommandProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/** 
 * Controller that processes http requests
 * @see HttpServlet
 * @author Darya Minina
*/
public class Controller extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    private static final String COMMAND_PARAM = "command";

    private final CommandProvider provider = new CommandProvider();

    public Controller() {
        super();
    }

    /**
     * Receives get requests
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Receives posts requests
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        process(request, response);
    }

    /**
	 * Processes all of requests using the command pattern
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
    private void process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        String name;
        Command command;

        name = request.getParameter(COMMAND_PARAM);
        command = provider.takeCommand(name);

        command.execute(request, response);
        
    }
}