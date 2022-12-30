package me.night0721.nv.util;

import me.night0721.nv.NullValkyrie;
import org.bukkit.ChatColor;

import java.lang.reflect.Field;
import java.util.logging.LogRecord;

public class Util {
    public static String centerText(String text, int lineLength) {
        StringBuilder builder = new StringBuilder();
        char space = ' ';
        int distance = (lineLength - text.length()) / 2;
        String repeat = String.valueOf(space).repeat(Math.max(0, distance));
        builder.append(repeat);
        builder.append(text);
        builder.append(repeat);
        return builder.toString();
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Object getFieldValue(Object instance, String fieldName) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    public static void setFieldValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }
    public static void info(Object obj) {
      NullValkyrie.getPlugin(NullValkyrie.class).getLogger().info(color(obj.toString()));
    }
    public static void warn(String string) {
      NullValkyrie.getPlugin(NullValkyrie.class).getLogger().warning(color(string));
    }
}
