/*
 *  Copyright (C) <2022> <XiaoMoMi>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.momirealms.customfishing.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.momirealms.customfishing.CustomFishing;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;

public class AdventureManager {

    public static void consoleMessage(String s) {
        Audience au = CustomFishing.adventure.sender(Bukkit.getConsoleSender());
        Component parsed = CustomFishing.miniMessage.deserialize(s);
        au.sendMessage(parsed);
    }

    public static void playerMessage(Player player, String s) {
        Audience au = CustomFishing.adventure.player(player);
        Component parsed = CustomFishing.miniMessage.deserialize(s);
        au.sendMessage(parsed);
    }

    public static void playerTitle(Player player, String s1, String s2, int in, int duration, int out) {
        Audience au = CustomFishing.adventure.player(player);
        Title.Times times = Title.Times.times(Duration.ofMillis(in), Duration.ofMillis(duration), Duration.ofMillis(out));
        Title title = Title.title(CustomFishing.miniMessage.deserialize(s1), CustomFishing.miniMessage.deserialize(s2), times);
        au.showTitle(title);
    }

    public static void playerActionbar(Player player, String s) {
        Audience au = CustomFishing.adventure.player(player);
        au.sendActionBar(CustomFishing.miniMessage.deserialize(s));
    }

    public static void playerSound(Player player, Sound.Source source, Key key) {
        Sound sound = Sound.sound(key, source, 1, 1);
        Audience au = CustomFishing.adventure.player(player);
        au.playSound(sound);
    }
}
