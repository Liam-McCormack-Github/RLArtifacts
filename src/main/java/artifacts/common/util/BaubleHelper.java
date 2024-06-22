package artifacts.common.util;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

import java.util.List;

public abstract class BaubleHelper {

    public static int getAmountBaubleEquipped(EntityPlayer player, Item bauble) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        int amount = 0;

        for (int a = 0; a < handler.getSlots(); ++a) {
            if (!handler.getStackInSlot(a).isEmpty() && handler.getStackInSlot(a).getItem() == bauble) {
                amount++;
            }
        }

        return amount;
    }

    public static void negatePotion(Entity entity, List<String> potions) {
        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        for (String potionName : potions) {
            Potion potion = Potion.getPotionFromResourceLocation(potionName);
            if (potion != null && player.isPotionActive(potion)) {
                player.removePotionEffect(potion);
            }
        }
    }

}