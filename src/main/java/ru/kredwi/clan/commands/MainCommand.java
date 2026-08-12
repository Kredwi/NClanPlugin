package ru.kredwi.clan.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import com.google.common.collect.Lists;
import ru.kredwi.clan.api.command.SubCommand;
import ru.kredwi.clan.commands.sub.PluginReload;
import ru.kredwi.clan.commands.sub.admin.SetBalance;
import ru.kredwi.clan.commands.sub.admin.SetExp;
import ru.kredwi.clan.commands.sub.control.*;
import ru.kredwi.clan.commands.sub.info.*;
import ru.kredwi.clan.commands.sub.members.*;
import ru.kredwi.clan.commands.sub.request.Accept;
import ru.kredwi.clan.commands.sub.request.Deny;
import ru.kredwi.clan.commands.sub.request.Invite;
import ru.kredwi.clan.commands.sub.role.AddRole;
import ru.kredwi.clan.commands.sub.role.Demote;
import ru.kredwi.clan.commands.sub.role.Remote;
import ru.kredwi.clan.commands.sub.role.RoleCMD;
import ru.kredwi.clan.events.RequestEvent;
import ru.kredwi.clan.model.Services;
import ru.kredwi.clan.service.MessagesService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainCommand implements CommandExecutor {

    private final MessagesService messagesService;
    private final Map<String, SubCommand> subCommands;

    public MainCommand(Services services) {
        this.messagesService = services.messagesService();
        this.subCommands = new HashMap<>();
        var clanService = services.clanService();
        subCommands.put("help", new Help(messagesService));
        subCommands.put("info", new Info(messagesService, services.clanService()));
        subCommands.put("create", new Create(services.economyAPI(), messagesService, services.configProvider(), services.clanService()));
        subCommands.put("members", new Members(messagesService, services.clanService()));
        subCommands.put("kick", new Kick(messagesService, services.clanService()));
        subCommands.put("leave", new Leave(messagesService, services.clanService()));
        subCommands.put("list", new List(messagesService, services.clanService()));
        subCommands.put("stats", new Stats(messagesService, services.clanService()));
        subCommands.put("top", new Top(messagesService, services.clanService()));
        subCommands.put("reload", new PluginReload(services));

        subCommands.put("disband", new Disband(messagesService, clanService));
        subCommands.put("sethome", new SetHome(messagesService, clanService));
        subCommands.put("delhome", new DelHome(messagesService, clanService));
        subCommands.put("home", new ClanHome(messagesService, clanService));
        subCommands.put("roles", new RolesCMD(messagesService, clanService));
        subCommands.put("role", new RoleCMD(messagesService, clanService));
        subCommands.put("addrole", new AddRole(messagesService, clanService));

        subCommands.put("setexp", new SetExp(messagesService, clanService));
        subCommands.put("setbalance", new SetBalance(messagesService, clanService));
        subCommands.put("chat", new Chat(messagesService, clanService));
        subCommands.put("demote", new Demote(messagesService, clanService));
        subCommands.put("remote", new Remote(messagesService, clanService));
        subCommands.put("market", new Market(messagesService, clanService, services.clanShop()));
        subCommands.put("pvp", new PVP(messagesService, clanService));

        RequestEvent crh = new RequestEvent(clanService, services.requestService(), messagesService);

        subCommands.put("accept", new Accept(crh));
        subCommands.put("deny", new Deny(crh));
        subCommands.put("invite", new Invite(messagesService, crh));

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            messagesService
                    .sendMessage(commandSender, "clan.command.help.required_arguments");
            return true;
        }

        var subCmdArgs = Lists.newArrayList(strings);

        subCmdArgs.remove(0); // remove first argument

        Optional.ofNullable(subCommands.get(strings[0]))
                .ifPresentOrElse(e -> {
                    if (commandSender.hasPermission(e.getPermission()))
                        e.onCommand(commandSender, subCmdArgs);
                    else
                        messagesService.sendMessage(commandSender, "clan.error.no_permission");
                }, () ->
                        messagesService.sendMessage(commandSender, "clan.command.help.unknown"));

        return true;
    }
}
