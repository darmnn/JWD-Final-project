package by.tc.photobook.controller.command;

import java.util.HashMap;









import by.tc.photobook.controller.command.impl.AddPhotoshootType;
import by.tc.photobook.controller.command.impl.Authorization;
import by.tc.photobook.controller.command.impl.BlockUnlock;
import by.tc.photobook.controller.command.impl.CancelOrder;
import by.tc.photobook.controller.command.impl.ChangeLocale;
import by.tc.photobook.controller.command.impl.DeleteComment;
import by.tc.photobook.controller.command.impl.DeletePhoto;
import by.tc.photobook.controller.command.impl.DeletePhotoshoot;
import by.tc.photobook.controller.command.impl.LoadAllUsers;
import by.tc.photobook.controller.command.impl.LoadAuthPage;
import by.tc.photobook.controller.command.impl.LoadComplaintsPage;
import by.tc.photobook.controller.command.impl.LoadMainPage;
import by.tc.photobook.controller.command.impl.LoadOrdersPage;
import by.tc.photobook.controller.command.impl.LoadPhotoPage;
import by.tc.photobook.controller.command.impl.LoadPhotoshoots;
import by.tc.photobook.controller.command.impl.LoadPhotoshootsPage;
import by.tc.photobook.controller.command.impl.LoadProfilePage;
import by.tc.photobook.controller.command.impl.LoadRegPage;
import by.tc.photobook.controller.command.impl.LoadReportPage;
import by.tc.photobook.controller.command.impl.LoadUserPage;
import by.tc.photobook.controller.command.impl.Logout;
import by.tc.photobook.controller.command.impl.NewComment;
import by.tc.photobook.controller.command.impl.NewComplaint;
import by.tc.photobook.controller.command.impl.OrderPhotoshoot;
import by.tc.photobook.controller.command.impl.ProcessOrder;
import by.tc.photobook.controller.command.impl.RatePhoto;
import by.tc.photobook.controller.command.impl.Registration;
import by.tc.photobook.controller.command.impl.SaveChanges;
import by.tc.photobook.controller.command.impl.SavePhotoshoot;
import by.tc.photobook.controller.command.impl.SavePhotoshootEdit;
import by.tc.photobook.controller.command.impl.SearchUser;
import by.tc.photobook.controller.command.impl.ViewComplaint;

/** 
 * Class that maps names of the commands to their implementation
 * @author Darya Minina
*/
public class CommandProvider
{
	/** 
	 * A map that contains all names of commands as a key and their implementation as a value
	*/
    private HashMap<CommandName, Command> commands = new HashMap<>();

    /** 
     * Fills the map with keys and values
    */
    public CommandProvider()
    {
        commands.put(CommandName.LOADMAINPAGE, new LoadMainPage());
        commands.put(CommandName.LOADREGPAGE, new LoadRegPage());
        commands.put(CommandName.REGISTRATION, new Registration());
        commands.put(CommandName.LOADAUTHPAGE, new LoadAuthPage());
        commands.put(CommandName.AUTHORIZATION, new Authorization());
        commands.put(CommandName.LOGOUT, new Logout());
        commands.put(CommandName.LOADPROFILEPAGE, new LoadProfilePage());
        commands.put(CommandName.SAVECHANGES, new SaveChanges());
        commands.put(CommandName.EN, new ChangeLocale());
        commands.put(CommandName.RU, new ChangeLocale());
        commands.put(CommandName.LOADPHOTOPAGE, new LoadPhotoPage());
        commands.put(CommandName.NEWCOMMENT, new NewComment());
        commands.put(CommandName.RATEPHOTO, new RatePhoto());
        commands.put(CommandName.DELETEPHOTO, new DeletePhoto());
        commands.put(CommandName.LOADUSERPAGE, new LoadUserPage());
        commands.put(CommandName.LOADPHOTOSHOOTPAGE, new LoadPhotoshootsPage());
        commands.put(CommandName.SAVEPHOTOSHOOT, new SavePhotoshoot());
        commands.put(CommandName.DELETEPHOTOSHOOT, new DeletePhotoshoot());
        commands.put(CommandName.SAVEPHOTOSHOOTEDIT, new SavePhotoshootEdit());
        commands.put(CommandName.LOADPHOTOSHOOTS, new LoadPhotoshoots());
        commands.put(CommandName.ORDERPHOTOSHOOT, new OrderPhotoshoot());
        commands.put(CommandName.PROCESSORDER, new ProcessOrder());
        commands.put(CommandName.LOADORDERSPAGE, new LoadOrdersPage());
        commands.put(CommandName.CANCELORDER, new CancelOrder());
        commands.put(CommandName.ADDPHOTOSHOOTTYPE, new AddPhotoshootType());
        commands.put(CommandName.SEARCHUSER, new SearchUser());
        commands.put(CommandName.LOADALLUSERSPAGE, new LoadAllUsers());
        commands.put(CommandName.BLOCKUNLOCK, new BlockUnlock());
        commands.put(CommandName.DELETECOMMENT, new DeleteComment());
        commands.put(CommandName.NEWCOMPLAINT, new NewComplaint());
        commands.put(CommandName.LOADREPORTPAGE, new LoadReportPage());
        commands.put(CommandName.LOADCOMPLAINTSPAGE, new LoadComplaintsPage());
        commands.put(CommandName.VIEWCOMPLAINT, new ViewComplaint());
    }

    /**
	 * Takes command by its name
	 * 
	 * @param name {@link String} command name
	 * @return {@link Command}
	 */
    public Command takeCommand(String name)
    {
        CommandName commandName;

        commandName = CommandName.valueOf(name.toUpperCase());

        return commands.get(commandName);
    }
}