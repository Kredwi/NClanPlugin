package ru.kredwi.clan.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import com.google.common.collect.Lists;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.commands.sub.*;
import ru.kredwi.clan.commands.sub.request.Accept;
import ru.kredwi.clan.commands.sub.request.CommandRequestHandler;
import ru.kredwi.clan.commands.sub.request.Deny;
import ru.kredwi.clan.commands.sub.request.Invite;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.RequestService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainCommand implements CommandExecutor {

    private Map<String, SubCommand> subCommands;

    public MainCommand(ClanService clanService, RequestService requestService) {
        this.subCommands = new HashMap<>();
        subCommands.put("help", new Help());
        subCommands.put("create", new Create(clanService));
        subCommands.put("remove", new Remove(clanService));
        subCommands.put("members", new Members(clanService));
        subCommands.put("kick", new Kick(clanService));

        CommandRequestHandler crh = new CommandRequestHandler(clanService, requestService);

        subCommands.put("accept", new Accept(crh));
        subCommands.put("deny", new Deny(crh));
        subCommands.put("invite", new Invite(crh));

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("Command required arguments. /clan help");
            return true;
        }

        var list = Lists.newArrayList(strings);

        list.remove(0); // remove first argument

        Optional.of(subCommands.get(strings[0]))
                .ifPresent(e -> {
                    if (commandSender.hasPermission(e.getPermission()))
                        e.onCommand(commandSender, list);
                    else
                        commandSender.sendMessage("You cannot be has permissions");
                });

        return true;
    }
}
