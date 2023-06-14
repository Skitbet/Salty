package com.skitbet.salty.bungee.listeners;

import com.skitbet.salty.bungee.SaltyBungee;
import com.skitbet.salty.shared.Salty;
import com.skitbet.salty.shared.profile.Profile;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        Profile profile = Salty.INSTANCE.getProfileManager().getOrCreateProfile(event.getPlayer().getName(), event.getPlayer().getUniqueId());
    }

}
