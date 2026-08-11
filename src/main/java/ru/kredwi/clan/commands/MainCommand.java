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
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.service.RequestService;
import ru.kredwi.clan.shop.ClanShop;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MainCommand implements CommandExecutor {

    private final Map<String, SubCommand> subCommands;

    public MainCommand(ClanService clanService,
                       RequestService requestService,
                       ClanShop clanShop,
                       ConfigProvider config) {
        this.subCommands = new HashMap<>();
        subCommands.put("help", new Help());
        subCommands.put("info", new Info(clanService));
        subCommands.put("create", new Create(config, clanService));
        subCommands.put("members", new Members(clanService));
        subCommands.put("kick", new Kick(clanService));
        subCommands.put("leave", new Leave(clanService));
        subCommands.put("list", new List(clanService));
        subCommands.put("stats", new Stats(clanService));

        subCommands.put("disband", new Disband(clanService));
        subCommands.put("sethome", new SetHome(clanService));
        subCommands.put("delhome", new DelHome(clanService));
        subCommands.put("home", new ClanHome(clanService));
        subCommands.put("roles", new RolesCMD(clanService));
        subCommands.put("role", new RoleCMD(clanService));
        subCommands.put("addrole", new AddRole(clanService));

        subCommands.put("setexp", new SetExp(clanService));
        subCommands.put("setbalance", new SetBalance(clanService));
        subCommands.put("chat", new Chat(clanService));
        subCommands.put("demote", new Demote(clanService));
        subCommands.put("remote", new Remote(clanService));
        subCommands.put("market", new Market(clanShop, clanService));
        subCommands.put("pvp", new PVP(clanService));

        RequestEvent crh = new RequestEvent(clanService, requestService);

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

        Optional.ofNullable(subCommands.get(strings[0]))
                .ifPresentOrElse(e -> {
                    if (commandSender.hasPermission(e.getPermission()))
                        e.onCommand(commandSender, list);
                    else
                        commandSender.sendMessage("You cannot be has permissions");
                }, () -> commandSender.sendMessage("Sub command not found use /help"));

        return true;
    }
}
