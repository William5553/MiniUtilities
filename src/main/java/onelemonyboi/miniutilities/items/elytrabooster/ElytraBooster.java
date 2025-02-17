package onelemonyboi.miniutilities.items.elytrabooster;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import onelemonyboi.miniutilities.items.MUArmorMaterial;

import net.minecraft.world.item.Item.Properties;

public class ElytraBooster extends ArmorItem {
    public ElytraBooster(Properties properties) {
        super(MUArmorMaterial.BOOSTER, Type.CHESTPLATE, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (player.isFallFlying()) {
            Vec3 vector3d = player.getLookAngle();
            double d0 = 1.5D;
            double d1 = 0.1D;
            Vec3 vector3d1 = player.getDeltaMovement();
            player.setDeltaMovement(vector3d1.add(vector3d.x * 0.1D + (vector3d.x * 1.5D - vector3d1.x) * 0.5D, vector3d.y * 0.1D + (vector3d.y * 1.5D - vector3d1.y) * 0.5D, vector3d.z * 0.1D + (vector3d.z * 1.5D - vector3d1.z) * 0.5D));
        }
    }

    // TODO: IMPLEMENT POWER N STUFF
}
