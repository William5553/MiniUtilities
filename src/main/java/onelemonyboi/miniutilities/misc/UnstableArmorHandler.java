package onelemonyboi.miniutilities.misc;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import onelemonyboi.miniutilities.items.MUArmorMaterial;

public class UnstableArmorHandler {
    public static void unstableArmor(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            long unstableCount = player.getInventory().armor.stream()
                    .filter(x -> x.getItem() instanceof ArmorItem)
                    .map(x -> (ArmorItem) x.getItem())
                    .filter(x -> x.getMaterial().equals(MUArmorMaterial.INFUSEDUNSTABLE))
                    .count();

            if (unstableCount >= 4) {
                player.getAbilities().mayfly = true;
                player.getAbilities().flying = true;
                player.getAbilities().setWalkingSpeed(0.2f);
                player.onUpdateAbilities();
            }
            else {
                player.getAbilities().mayfly = false;
                player.getAbilities().setWalkingSpeed(0.1f);
                player.onUpdateAbilities();
            }
        }
    }
}