package onelemonyboi.miniutilities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import onelemonyboi.lemonlib.identifiers.RenderInfoIdentifier;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MachineRenderer {
    public static void blockRenderInfo(RenderGuiOverlayEvent event) {
        GuiGraphics ms = event.getGuiGraphics();

        HitResult mouseOver = Minecraft.getInstance().hitResult;
        if (!(mouseOver instanceof BlockHitResult)) {
            return;
        }

        BlockHitResult result = (BlockHitResult) mouseOver;
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        BlockPos pos = result.getBlockPos();
        BlockEntity te = world.getBlockEntity(pos);

        if (!(te instanceof RenderInfoIdentifier)) {
            return;
        }

        ms.pose().pushPose();
        List<MutableComponent> tooltip = ((RenderInfoIdentifier) te).getInfo();

        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();
        float posX = width / 2F ;
        float posY = height / 2F + 10;

        posX = Math.min(posX, width - 20);
        posY = Math.min(posY, height - 20);

        int count = 0;
        int maxLen = 0;
        for (MutableComponent component : tooltip) {
            int len = mc.font.width(component.getString());
            ms.drawString(mc.font, component.getVisualOrderText(), posX - (len / 2F), posY + count, 16777215, true);
            if (len > maxLen) {
                maxLen = mc.font.width(component.getString());
            }
            count += 12;
        }
        ms.pose().popPose();
    }
}
