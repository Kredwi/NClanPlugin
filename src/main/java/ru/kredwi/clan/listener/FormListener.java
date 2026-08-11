package ru.kredwi.clan.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.item.Item;
import cn.nukkit.permission.Permission;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.gui.MarketForm;
import ru.kredwi.clan.gui.RoleCreateForm;
import ru.kredwi.clan.gui.RoleManagerForm;
import ru.kredwi.clan.permission.ClanPermissions;
import ru.kredwi.clan.role.Role;
import ru.kredwi.clan.service.ClanService;
import ru.kredwi.clan.shop.ClanShop;
import ru.kredwi.clan.shop.ShopItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record FormListener(ClanService clanService, ClanShop clanShop) implements Listener {

    public static final int OFFSET_TO_ROLES = 6;
    public static final String ALLOW_SYMBOLS = "^[a-zA-Z0-9а-яА-ЯёЁ§]+$";

    @EventHandler
    public void onForm(PlayerFormRespondedEvent e) {
        Player player = e.getPlayer();
        FormWindow formWindow = e.getWindow();

        if (formWindow.wasClosed())
            return;

        if (formWindow instanceof MarketForm marketForm) {
            onClanShop(player, marketForm);
            return;
        }

        if (formWindow instanceof RoleManagerForm managerForm) {
            onRoleManager(player, managerForm);
            return;
        }

        if (formWindow instanceof RoleCreateForm createForm) {
            onRoleCreate(player, createForm);
            return;
        }
    }

    private boolean isNameInvalid(@NonNull String roleName) {
        return !roleName.matches(ALLOW_SYMBOLS);
    }

    private void onRoleCreate(Player player, RoleCreateForm managerForm) {
        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            player.sendMessage("You does not has clan");
            return;
        }

        var response = managerForm.getResponse();
        if (response == null)
            return;
        var responses = response.getResponses();
        if (responses.isEmpty())
            return;

        String name = (String) response.getResponse(0); // name input
        String priorityText = (String) response.getResponse(1); // priority input
        List<Permission> perms = new ArrayList<>();

        if (isNameInvalid(name)) {
            player.sendMessage("Name of role " + name + " cannot access");
            return;
        }

        int priority;
        try {
            priority = Integer.parseInt(priorityText);
        } catch (NumberFormatException e) {
            player.sendMessage("Provide correct number");
            return;
        }

        for (int i = 4; i < response.getResponses().size(); i++) {
            if ((Boolean) response.getResponse(i))
                perms.add(ClanPermissions.values()[i - 4].getPermission());
        }
        var roles = new ArrayList<>(clan.get().getRoles());
        roles.add(new Role(name, priority, perms));
        clan.get().setRoles(roles);
    }

    private void onRoleManager(Player player, RoleManagerForm managerRoleForm) {
        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            player.sendMessage("You does not has clan");
            return;
        }

        var response = managerRoleForm.getResponse();
        if (response == null)
            return;
        var responses = response.getResponses();
        if (responses.isEmpty())
            return;

        var player1 = clan.get().getMembers().get(player.getUniqueId());
        if (player1 == null || !player1.getRole().getPermissions().contains(ClanPermissions.PERMISSION_CLAN_CHANGE_ROLE.getPermission())) {
            player.sendMessage("You dont has permissions");
            return;
        }

        List<Integer> sortedKeys = new ArrayList<>(responses.keySet());
        Collections.sort(sortedKeys);

        List<Role> roles = managerRoleForm.getRoles();
        int permCount = ClanPermissions.values().length;

        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            int baseInt = i * (OFFSET_TO_ROLES + permCount);

            String newName = (String) responses.get(sortedKeys.get(baseInt + 1));
            if (isNameInvalid(newName)) {
                player.sendMessage("Name of role " + newName + " cannot access");
                continue;
            }

            String priorityStr = (String) responses.get(sortedKeys.get(baseInt + 2));

            int newPriority = -1;
            try {
                newPriority = Integer.parseInt(priorityStr);
                if (newPriority < 0)
                    throw new NumberFormatException("Number cannot be negative");
            } catch (NumberFormatException numberException) {
                player.sendMessage("Please provide correct priority for role " + newName + ". Error " + numberException.getMessage());
                continue;
            }

            role.setName(newName);
            role.setPriority(newPriority);

            List<Permission> permissions = new ArrayList<>();
            for (int j = 0; j < permCount; j++) {
                int toggleKey = sortedKeys.get(baseInt + 4 + j);
                boolean hasPerm = (boolean) responses.get(toggleKey);
                ClanPermissions perm = ClanPermissions.values()[j];

                if (hasPerm)
                    permissions.add(perm.getPermission());
            }
            role.setPermissions(permissions);

        }
    }

    private void onClanShop(Player player, MarketForm marketForm) {
        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            player.sendMessage("You does not has clan");
            return;
        }

        int clickedButton = marketForm.getResponse().getClickedButtonId();
        if (clickedButton > clanShop.getItems().size())
            return;
        Optional<ShopItem> shopItem = clanShop.getItem(clickedButton);
        if (shopItem.isEmpty()) {
            player.sendMessage("Item not found");
            return;
        }
        if (shopItem.get().getPrise() > clan.get().getStats().getBalance()) {
            player.sendMessage("You cannot has moneys for the buy");
            return;
        }
        Item buyedItem = new Item(shopItem.get().getItemId());
        buyedItem.setCustomName(shopItem.get().getName());
        buyedItem.setLore(shopItem.get().getLore().toArray(new String[0]));
        buyedItem.setCount(shopItem.get().getCount());
        player.getInventory().addItem(buyedItem);
        clan.get().getStats().setBalance(clan.get().getStats().getBalance() - shopItem.get().getPrise());
        player.sendMessage("You successfully buying item");
    }

}
