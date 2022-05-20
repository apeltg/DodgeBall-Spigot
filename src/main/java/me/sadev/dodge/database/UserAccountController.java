package me.sadev.dodge.database;

import com.google.common.collect.Maps;
import me.sadev.dodge.Dodge;
import me.sadev.dodge.database.models.UserAccount;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class UserAccountController {

    private final HashMap<UUID, UserAccount> userAccounts;

    public UserAccountController() {
        userAccounts = Maps.newHashMap();
    }

    public void loadUser(UserAccount userAccount) {
        userAccounts.put(userAccount.getUuid(), userAccount);
    }

    private boolean containsUserCache(UUID uuid) {
        return userAccounts.containsKey(uuid);
    }

    public boolean containsUser(UUID uuid) {
        if (!containsUserCache(uuid)) {
            //try { return Dodge.getInstance().getUserAccountRepository().loadUserAccount(uuid).whenCompleteAsync((aBoolean, throwable) -> {}).get();
            //} catch (InterruptedException | ExecutionException e) { Dodge.getInstance().getLog().error(e); }
        }
        return true;
    }
}
