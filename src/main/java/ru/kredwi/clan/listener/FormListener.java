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
import ru.kredwi.clan.service.MessagesService;
import ru.kredwi.clan.shop.ClanShop;
import ru.kredwi.clan.shop.ShopItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record FormListener(MessagesService messagesService, ClanService clanService,
                           ClanShop clanShop) implements Listener {

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
            messagesService.sendMessage(player, "clan.error.no_clan");
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
            messagesService.sendMessage(player, "clan.error.role_name_invalid", name);
            return;
        }

        int priority;
        try {
            priority = Integer.parseInt(priorityText);
        } catch (NumberFormatException e) {
            messagesService.sendMessage(player, "clan.error.provide_correct_number");
            return;
        }

        for (int i = 4; i < response.getResponses().size(); i++) {
            if ((Boolean) response.getResponse(i))
                perms.add(ClanPermissions.values()[i - 4].getPermission());
        }
        var roles = new ArrayList<>(clan.get().getRoles());
        roles.add(new Role(name, priority, perms));
        clan.get().setRoles(roles);
        messagesService.sendMessage(player, "clan.success.role_created", name);
    }

    private void onRoleManager(Player player, RoleManagerForm managerRoleForm) {
        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            messagesService.sendMessage(player, "clan.error.no_clan");
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
            messagesService.sendMessage(player, "clan.error.no_permission_role");
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
                messagesService.sendMessage(player, "clan.error.role_name_invalid", newName);
                continue;
            }

            String priorityStr = (String) responses.get(sortedKeys.get(baseInt + 2));

            int newPriority = -1;
            try {
                newPriority = Integer.parseInt(priorityStr);
                if (newPriority < 0)
                    throw new NumberFormatException("Number cannot be negative");
            } catch (NumberFormatException numberException) {
                messagesService.sendMessage(player, "clan.error.priority_invalid", newName, numberException.getMessage());

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
        messagesService.sendMessage(player, "clan.success.roles_updated");
    }

    private void onClanShop(Player player, MarketForm marketForm) {
        var clan = clanService.getClanWithUUID(player.getUniqueId());
        if (clan.isEmpty()) {
            messagesService.sendMessage(player, "clan.error.no_clan");
            return;
        }

        int clickedButton = marketForm.getResponse().getClickedButtonId();
        if (clickedButton > clanShop.getItems().size())
            return;

        Optional<ShopItem> shopItem = clanShop.getItem(clickedButton);
        if (shopItem.isEmpty()) {
            messagesService.sendMessage(player, "clan.error.item_not_found");
            return;
        }

        if (shopItem.get().getPrise() > clan.get().getStats().getBalance()) {
            messagesService.sendMessage(player, "clan.error.insufficient_balance");
            return;
        }

        Item buyedItem = new Item(shopItem.get().getItemId());
        buyedItem.setCustomName(shopItem.get().getName());
        buyedItem.setLore(shopItem.get().getLore().toArray(new String[0]));
        buyedItem.setCount(shopItem.get().getCount());
        if (!player.getInventory().canAddItem(buyedItem)) {
            messagesService.sendMessage(player, "clan.error.inventory_full");
            return;
        }
        player.getInventory().addItem(buyedItem);
        clan.get().getStats().setBalance(clan.get().getStats().getBalance() - shopItem.get().getPrise());
        messagesService.sendMessage(player, "clan.success.item_bought");
    }

}
