package net.dopedealers.plugins.mutechat.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder
{
    private final ItemStack item;

    public ItemBuilder(final Material material, final int data) {
        this.item = new ItemStack(material, 1, (short)(byte)data);
    }

    public ItemBuilder setAmount(final int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(final String name) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(Util.c(name));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        final ItemMeta meta = this.item.getItemMeta();
        final List<String> lores = new ArrayList<String>();
        for (final String s : lore) {
            lores.add(Util.c(s));
        }
        meta.setLore((List)lores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(final Enchantment ench, final int level) {
        this.item.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder setColor(final Color color2) {
        if (this.item.equals((Object)Material.LEATHER_BOOTS) || this.item.equals((Object)Material.LEATHER_LEGGINGS) || this.item.equals((Object)Material.LEATHER_CHESTPLATE) || this.item.equals((Object)Material.LEATHER_HELMET)) {
            final LeatherArmorMeta meta = (LeatherArmorMeta)this.item.getItemMeta();
            meta.setColor(color2);
            this.item.setItemMeta((ItemMeta)meta);
            return this;
        }
        throw new IllegalArgumentException("setColor can only be used on leather armour!");
    }

    public ItemBuilder setDurability(final int durability) {
        if (durability >= -32768 && durability <= 32767) {
            this.item.setDurability((short)durability);
            return this;
        }
        throw new IllegalArgumentException("The durability must be a short!");
    }

    public ItemBuilder addFlag(final ItemFlag flag) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(new ItemFlag[] { flag });
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setData(final MaterialData data) {
        final ItemMeta meta = this.item.getItemMeta();
        this.item.setData(data);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack getStack() {
        return this.item;
    }
}
