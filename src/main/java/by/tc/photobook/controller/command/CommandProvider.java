package by.tc.photobook.controller.command;

import java.util.HashMap;

import by.tc.photobook.controller.command.impl.AddPhotoshoot;
import by.tc.photobook.controller.command.impl.Authorization;
import by.tc.photobook.controller.command.impl.ChangeLocale;
import by.tc.photobook.controller.command.impl.DeletePhoto;
import by.tc.photobook.controller.command.impl.DeletePhotoshoot;
import by.tc.photobook.controller.command.impl.EditPhotoshoot;
import by.tc.photobook.controller.command.impl.EditProfile;
import by.tc.photobook.controller.command.impl.LoadAuthPage;
import by.tc.photobook.controller.command.impl.LoadMainPage;
import by.tc.photobook.controller.command.impl.LoadPhoto;
import by.tc.photobook.controller.command.impl.LoadPhotoPage;
import by.tc.photobook.controller.command.impl.LoadPhotoshootsPage;
import by.tc.photobook.controller.command.impl.LoadProfilePage;
import by.tc.photobook.controller.command.impl.LoadRegPage;
import by.tc.photobook.controller.command.impl.LoadUserPage;
import by.tc.photobook.controller.command.impl.Logout;
import by.tc.photobook.controller.command.impl.NewComment;
import by.tc.photobook.controller.command.impl.RatePhoto;
import by.tc.photobook.controller.command.impl.Registration;
import by.tc.photobook.controller.command.impl.SaveChanges;
import by.tc.photobook.controller.command.impl.SaveNewPhoto;
import by.tc.photobook.controller.command.impl.SavePhotoChanges;
import by.tc.photobook.controller.command.impl.SavePhotoshoot;

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
        commands.put(CommandName.SAVEPHOTOCHANGES, new SavePhotoChanges());
        commands.put(CommandName.EN, new ChangeLocale());
        commands.put(CommandName.RU, new ChangeLocale());
        commands.put(CommandName.ADDPHOTO, new LoadPhoto());
        commands.put(CommandName.SAVENEWPHOTO, new SaveNewPhoto());
        commands.put(CommandName.LOADPHOTOPAGE, new LoadPhotoPage());
        commands.put(CommandName.NEWCOMMENT, new NewComment());
        commands.put(CommandName.RATEPHOTO, new RatePhoto());
        commands.put(CommandName.DELETEPHOTO, new DeletePhoto());
        commands.put(CommandName.LOADUSERPAGE, new LoadUserPage());
        commands.put(CommandName.LOADPHOTOSHOOTPAGE, new LoadPhotoshootsPage());
        commands.put(CommandName.ADDPHOTOSHOOT, new AddPhotoshoot());
        commands.put(CommandName.SAVEPHOTOSHOOT, new SavePhotoshoot());
        commands.put(CommandName.DELETEPHOTOSHOOT, new DeletePhotoshoot());
        commands.put(CommandName.EDITPHOTOSHOOT, new EditPhotoshoot());
    }

    public Command takeCommand(String name)
    {
        CommandName commandName;

        commandName = CommandName.valueOf(name.toUpperCase());

        return commands.get(commandName);
    }
}