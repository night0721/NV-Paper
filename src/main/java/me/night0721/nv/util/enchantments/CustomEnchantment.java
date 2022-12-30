package me.night0721.nv.util.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class CustomEnchantment extends Enchantment {
    private final String name;
    private final int max;

    public CustomEnchantment(String namespace, String name, int lvl) {
        super(NamespacedKey.minecraft(namespace));
        this.name = name;
        this.max = lvl;
    }

  @Override
  public @NotNull String getName() {
    return name;
  }

  @Override
  public int getMaxLevel() {
    return max;
  }

  @Override
  public int getStartLevel() {
    return 1;
  }

  @Override
  public @NotNull EnchantmentTarget getItemTarget() {
    return EnchantmentTarget.WEAPON;
  }

  @Override
  public boolean isTreasure() {
    return false;
  }

  @Override
  public boolean isCursed() {
    return false;
  }

  @Override
  public boolean conflictsWith(@NotNull Enchantment other) {
    return false;
  }

  @Override
  public boolean canEnchantItem(@NotNull ItemStack item) {
    return false;
  }

  @Override
  public @NotNull Component displayName(int level) {
    return Component.text(name);
  }

  @Override
  public boolean isTradeable() {
    return false;
  }

  @Override
  public boolean isDiscoverable() {
    return false;
  }

  @Override
  public @NotNull EnchantmentRarity getRarity() {
    return EnchantmentRarity.VERY_RARE;
  }

  @Override
  public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
    return 0;
  }

  @Override
  public @NotNull Set<EquipmentSlot> getActiveSlots() {
    return Set.of(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
  }

  @Override
  public @NotNull String translationKey() {
    return "";
  }

  @Override
  public @NotNull Key key() {
    return super.key();
  }
}
