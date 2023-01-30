package artifacts.common.util;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public abstract class BaubleHelper {

    public static int getAmountBaubleEquipped(EntityPlayer player, Item bauble) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        int amount = 0;

        for(int a = 0; a < handler.getSlots(); ++a) {
            if(!handler.getStackInSlot(a).isEmpty() && handler.getStackInSlot(a).getItem() == bauble) {
                amount++;
            }
        }

        return amount;
    }
}