package ru.kredwi.clan.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import com.google.common.collect.Lists;
import ru.kredwi.clan.api.command.SubCommand;
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
import ru.kredwi.clan.provider.ConfigProvider;
import ru.kredwi.clan.provider.economy.PluginEconomyProvider;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.MessagesService;
import ru.kredwi.clan.service.RequestService;
import ru.kredwi.clan.shop.ClanShop;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainCommand implements CommandExecutor {

    private final MessagesService messagesService;
    private final Map<String, SubCommand> subCommands;

    public MainCommand(
            PluginEconomyProvider economyProvider,
            MessagesService messagesService,
            ClanService clanService,
            RequestService requestService,
            ClanShop clanShop,
            ConfigProvider config) {
        this.messagesService = messagesService;
        this.subCommands = new HashMap<>();
        subCommands.put("help", new Help(messagesService));
        subCommands.put("info", new Info(messagesService, clanService));
        subCommands.put("create", new Create(economyProvider, messagesService, config, clanService));
        subCommands.put("members", new Members(messagesService, clanService));
        subCommands.put("kick", new Kick(messagesService, clanService));
        subCommands.put("leave", new Leave(messagesService, clanService));
        subCommands.put("list", new List(messagesService, clanService));
        subCommands.put("stats", new Stats(messagesService, clanService));
        subCommands.put("top", new Top(messagesService, clanService));

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
        subCommands.put("market", new Market(messagesService, clanService, clanShop));
        subCommands.put("pvp", new PVP(messagesService, clanService));

        RequestEvent crh = new RequestEvent(clanService, requestService, messagesService);

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
