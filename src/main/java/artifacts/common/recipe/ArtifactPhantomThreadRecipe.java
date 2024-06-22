package artifacts.common.recipe;

import artifacts.common.item.BaubleBase;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import wiresegal.classyhats.ClassyHatsContent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ArtifactPhantomThreadRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return validInput(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        Integer[] slots = validInput(inv);
        if (slots == null) return ItemStack.EMPTY;

        ItemStack stack = inv.getStackInSlot(slots[0]).copy();
        if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setBoolean("phantom_thread_invisible", !stack.getTagCompound().getBoolean("phantom_thread_invisible"));

        return stack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Nullable
    private Integer[] validInput(InventoryCrafting inv) {
        int numStacks = 0;
        int artifactSlot = -1;
        int threadSlot = -1;
        List<Integer> occupiedSlots = new ArrayList<>();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                numStacks++;
                occupiedSlots.add(i);
            }
        }
        if (numStacks != 2) return null;

        for (int i : occupiedSlots) {
            ItemStack itemStack = inv.getStackInSlot(i);

            if (itemStack.getItem() instanceof BaubleBase) artifactSlot = i;
            else if (itemStack.getItem().equals(ClassyHatsContent.INSTANCE.getPHANTOM_THREAD())) threadSlot = i;
            else return null;
        }
        Integer[] slots = new Integer[2];
        slots[0] = artifactSlot;
        slots[1] = threadSlot;
        return (artifactSlot != -1 && threadSlot != -1) ? slots : null;
    }
}