package me.night0721.nv.entities.items

import me.night0721.nv.NullValkyrie
import me.night0721.nv.database.CustomWeaponsDataManager
import me.night0721.nv.util.Rarity
import me.night0721.nv.util.Util.capitalize
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.*
import org.bukkit.persistence.PersistentDataType
import java.util.*

object CustomItemManager {

    @Suppress("unchecked_cast")
    fun produceItem(itemName: String?): ItemStack {
        val weapon = CustomWeaponsDataManager().getWeapon(itemName)
        val item = ItemStack((weapon["Material"] as Material?)!!)
        val propertiesList: MutableList<Component> = ArrayList()
        val itemAbility: MutableList<Component> = ArrayList()
        val enchants = weapon["Enchants"] as HashMap<String, Any>?
        val attributes = weapon["Attributes"] as HashMap<String, Any>?
        for (enchant in enchants!!.keys) Objects.requireNonNull(
            Enchantment.getByKey(
                NamespacedKey.minecraft(enchant)
            )
        )?.let {
            item.addUnsafeEnchantment(
                it, (enchants[enchant] as Int?)!!
            )
        }
        val lore = weapon["Lore"] as HashMap<String, Any>?
        val ability = lore!!["Ability"] as HashMap<String, Any?>?
        val properties = lore["Properties"] as HashMap<String, Any>?
        for (p in properties!!.keys) if (properties[p] as Int > 0) propertiesList.add(
            Component.text().content(capitalize(p) + ": ").color(NamedTextColor.GRAY)
                .append(Component.text().content("+" + properties[p]).color(NamedTextColor.RED).build()).build()
        )
        if (ability!!["Name"] != null) {
            itemAbility.add(Component.text().content("Item Ability: " + ability["Name"]).color(NamedTextColor.GOLD).build())
            for (line in (ability["Details"] as List<String>?)!!) itemAbility.add(Component.text().content(line).color(NamedTextColor.GRAY).build())
        }
        val itemMeta = item.itemMeta ?: return item
        itemMeta.displayName(
            Component.text().content(weapon["Name"] as String).color(Rarity.getRarity(weapon["Rarity"] as String).color)
                .build()
        )
        itemMeta.isUnbreakable = true
        val loreList = ArrayList(propertiesList)
        loreList.add(Component.text().content(" ").build())
        val enchantmentList = ArrayList<String>()
        for (enchantment in item.enchantments.keys) {
            val split = listOf(
                *listOf(*enchantment.key.toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray())[1].split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            )
            val builder = StringBuilder()
            for (strings in split) {
                val formatted = capitalize(strings)
                if (split.size > 1) {
                    if (strings == split[split.size - 1]) builder.append(formatted) else {
                        builder.append(formatted)
                        builder.append(" ")
                    }
                } else builder.append(formatted)
            }
            enchantmentList.add(builder.toString() + " " + item.getEnchantmentLevel(enchantment))
        }
        loreList.add(Component.text().content(java.lang.String.join(", ", enchantmentList)).color(NamedTextColor.BLUE).build())
        loreList.add(Component.text().content(" ").build())
        loreList.addAll(itemAbility)
        loreList.add(Component.text().content(" ").build())
        loreList.add(Rarity.getRarity(weapon["Rarity"] as String?).display)
        itemMeta.lore(loreList)
        for (attribute in attributes!!.keys) {
            if (attribute == "damage") {
                val p = AttributeModifier(
                    UUID.randomUUID(),
                    "generic.attackDamage",
                    (attributes[attribute] as Double?)!!,
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlot.HAND
                )
                itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, p)
            } else if (attribute == "moveSpeed") {
                val s = AttributeModifier(
                    UUID.randomUUID(),
                    "generic.movementSpeed",
                    (attributes[attribute] as Double?)!!,
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlot.HAND
                )
                itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, s)
            }
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS)
        val pdcdata = weapon["PDC"] as HashMap<String, Any>?
        for (key in pdcdata!!.keys) {
            val container = itemMeta.persistentDataContainer
            val key1 = NamespacedKey(NullValkyrie.getPlugin(), key)
            container.set(key1, PersistentDataType.INTEGER, pdcdata[key] as Int)
        }
        item.itemMeta = itemMeta
        val recipes = weapon["Recipes"] as HashMap<String, Any?>?
        if (recipes!!["Shape"] != null) {
            val shapes = recipes["Shape"] as List<String>?
            val ind = recipes["Ingredients"] as HashMap<String, String>?
            val indgredients = HashMap<Char, Material?>()
            for (i in ind!!.keys) indgredients[i[0]] = Material.matchMaterial(ind[i]!!)
            setItemRecipe(weapon["Name"] as String?, item, shapes, indgredients, recipes["Amount"] as Int)
        }
        return item
    }

    private fun setItemRecipe(
        key: String?, i: ItemStack?, shapes: List<String>?, ingredients: HashMap<Char, Material?>, amount: Int
    ) {
        val nsk = NamespacedKey(NullValkyrie.getPlugin(), key!!.replace("\\s".toRegex(), ""))
        val recipe = ShapedRecipe(nsk, i!!)
        recipe.shape(shapes!![0], shapes[1], shapes[2])
        val abcs = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I')
        for (ei in 0 until amount) recipe.setIngredient(abcs[ei], ingredients[abcs[ei]]!!)
        if (Bukkit.getRecipe(nsk) != null) Bukkit.removeRecipe(nsk)
        Bukkit.addRecipe(recipe)
    }
}