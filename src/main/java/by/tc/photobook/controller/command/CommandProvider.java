package by.tc.photobook.controller.command;

import java.util.HashMap;

import by.tc.photobook.controller.command.impl.Authorization;
import by.tc.photobook.controller.command.impl.ChangeLocale;
import by.tc.photobook.controller.command.impl.EditProfile;
import by.tc.photobook.controller.command.impl.LoadAuthPage;
import by.tc.photobook.controller.command.impl.LoadMainPage;
import by.tc.photobook.controller.command.impl.LoadProfilePage;
import by.tc.photobook.controller.command.impl.LoadRegPage;
import by.tc.photobook.controller.command.impl.Logout;
import by.tc.photobook.controller.command.impl.Registration;
import by.tc.photobook.controller.command.impl.SaveChanges;

public class CommandProvider
{
    private HashMap<CommandName, Command> commands = new HashMap<>();

    public CommandProvider()
    {
        commands.put(CommandName.LOADMAINPAGE, new LoadMainPage());
        commands.put(CommandName.LOADREGPAGE, new LoadRegPage());
        commands.put(CommandName.REGISTRATION, new Registration());
        commands.put(CommandName.LOADAUTHPAGE, new LoadAuthPage());
        commands.put(CommandName.AUTHORIZATION, new Authorization());
        commands.put(CommandName.LOGOUT, new Logout());
        commands.put(CommandName.LOADPROFILEPAGE, new LoadProfilePage());
        commands.put(CommandName.EDITPROFILE, new EditProfile());
        commands.put(CommandName.SAVECHANGES, new SaveChanges());
        commands.put(CommandName.EN, new ChangeLocale());
        commands.put(CommandName.RU, new ChangeLocale());
    }

    public Command takeCommand(String name)
    {
        CommandName commandName;

        commandName = CommandName.valueOf(name.toUpperCase());

        return commands.get(commandName);
    }
}