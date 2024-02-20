package me.fakepumpkin7.armouredelytras.handlers;

import com.google.common.collect.Multimap;
import me.fakepumpkin7.armouredelytras.ArmouredElytras;
import me.fakepumpkin7.armouredelytras.util.ArmouredElytraType;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class BaseArmouredElytra {

    private HashMap<UUID, ArmouredElytraType> playersToGiveEffect = new HashMap<>();
    ArmouredElytras plugin;

    public BaseArmouredElytra(ArmouredElytras plugin){
        this.plugin = plugin;

        //create recipies
        addRecipies();


    }

    private void addRecipies() {
        for(ArmouredElytraType type : ArmouredElytraType.values()){
            ShapedRecipe recipe = createRecipe(type);
            Bukkit.addRecipe(recipe);
        }

    }

    private ShapedRecipe createRecipe(ArmouredElytraType type) {

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "armouredelytra."+type.getName().toLowerCase()), buildArmouredElytra(type));
        recipe.shape(" I ", " E ", " C ");
        recipe.setIngredient('I', type.getMaterial());
        recipe.setIngredient('E', Material.ELYTRA);
        recipe.setIngredient('C', type.getChestplate());

        return recipe;
    }


    public ItemStack buildArmouredElytra(ArmouredElytraType type){
        int defencePoints;
        int toughnessPoints;
        switch(type){
            case IRON:
                defencePoints = 6;
                toughnessPoints = 0;
                break;
            case GOLD:
                defencePoints = 5;
                toughnessPoints = 0;
                break;
            case DIAMOND:
                defencePoints = 8;
                toughnessPoints = 2;
                break;
            case NETHERITE:
                defencePoints = 8;
                toughnessPoints = 3;
                break;
            default:
                defencePoints = 0;
                toughnessPoints = 0;
                break;
        }

        ItemStack itemStack = new ItemStack(Material.ELYTRA);
        ItemMeta meta = itemStack.getItemMeta();

        AttributeModifier defenceModifier = getDefenceModifier(defencePoints);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, defenceModifier);


        AttributeModifier toughnessModifier = getToughnessModifier(toughnessPoints);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);

        meta.setCustomModelData(type.getCustomModelData());

        meta.setDisplayName(""+ type.getColour() + type.getName() + " Plated Elytra");
        itemStack.setItemMeta(meta);

        return itemStack;
    }


    public AttributeModifier getToughnessModifier(double amount){
        return new AttributeModifier(UUID.randomUUID(), "armouredElytra.toughness", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
    }
    public AttributeModifier getDefenceModifier(double amount){
        return new AttributeModifier(UUID.randomUUID(), "armouredElytra.defence", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
    }


    public void onEquip(Player player, ArmouredElytraType equippedType){
        playersToGiveEffect.put(player.getUniqueId(),equippedType);
    }
    public void onUnequip(Player player){
        playersToGiveEffect.remove(player.getUniqueId());
    }

    public boolean isArmouredElytra(ItemStack itemStack){
        if(itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.hasAttributeModifiers()) {
                Multimap<Attribute, AttributeModifier> modifiers = meta.getAttributeModifiers();
                for (AttributeModifier modifier : modifiers.get(Attribute.GENERIC_ARMOR)) {
                    if (modifier.getName().equals("armouredElytra.defence")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public HashMap<UUID, ArmouredElytraType> getPlayersToGiveEffect() {
        return playersToGiveEffect;
    }
    public ArmouredElytraType getArmouredElytraType(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        double defence = 0.0;
        double toughness = 0.0;
        Multimap<Attribute, AttributeModifier> modifiers = meta.getAttributeModifiers();
        for (AttributeModifier modifier : modifiers.get(Attribute.GENERIC_ARMOR)) {
            if (modifier.getName().equals("armouredElytra.defence")) {
                defence = modifier.getAmount();
            }
        }
        for (AttributeModifier modifier : modifiers.get(Attribute.GENERIC_ARMOR_TOUGHNESS)) {
            if (modifier.getName().equals("armouredElytra.toughness")) {
                toughness = modifier.getAmount();
            }
        }
        switch((int) defence){
            case 5:
                return ArmouredElytraType.GOLD;
            case 6:
                return ArmouredElytraType.IRON;
            case 8:
                switch((int) toughness){
                    case 2:
                        return ArmouredElytraType.DIAMOND;
                    case 3:
                        return ArmouredElytraType.NETHERITE;
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
}
