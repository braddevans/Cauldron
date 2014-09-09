package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.CraftShapedRecipe;
// CraftBukkit end

public class ShapedRecipes implements IRecipe
{
    public final int recipeWidth;
    public final int recipeHeight;
    public final ItemStack[] recipeItems;
    private ItemStack recipeOutput;
    private boolean field_92101_f;
    private static final String __OBFID = "CL_00000093";

    public ShapedRecipes(int p_i1917_1_, int p_i1917_2_, ItemStack[] p_i1917_3_, ItemStack p_i1917_4_)
    {
        this.recipeWidth = p_i1917_1_;
        this.recipeHeight = p_i1917_2_;
        this.recipeItems = p_i1917_3_;
        this.recipeOutput = p_i1917_4_;
    }

    // CraftBukkit start
    public org.bukkit.inventory.ShapedRecipe toBukkitRecipe()
    {
        CraftItemStack result = CraftItemStack.asCraftMirror(this.recipeOutput);
        CraftShapedRecipe recipe = new CraftShapedRecipe(result, this);

        switch (this.recipeHeight)
        {
            case 1:
                switch (this.recipeWidth)
                {
                    case 1:
                        recipe.shape("a");
                        break;
                    case 2:
                        recipe.shape("ab");
                        break;
                    case 3:
                        recipe.shape("abc");
                        break;
                }

                break;
            case 2:
                switch (this.recipeWidth)
                {
                    case 1:
                        recipe.shape("a", "b");
                        break;
                    case 2:
                        recipe.shape("ab", "cd");
                        break;
                    case 3:
                        recipe.shape("abc", "def");
                        break;
                }

                break;
            case 3:
                switch (this.recipeWidth)
                {
                    case 1:
                        recipe.shape("a", "b", "c");
                        break;
                    case 2:
                        recipe.shape("ab", "cd", "ef");
                        break;
                    case 3:
                        recipe.shape("abc", "def", "ghi");
                        break;
                }

                break;
        }

        char c = 'a';

        for (ItemStack stack : this.recipeItems)
        {
            if (stack != null)
            {
                recipe.setIngredient(c, org.bukkit.craftbukkit.util.CraftMagicNumbers.getMaterial(stack.getItem()), stack.getItemDamage());
            }

            c++;
        }

        return recipe;
    }
    // CraftBukkit end

    public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }

    public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_)
    {
        for (int i = 0; i <= 3 - this.recipeWidth; ++i)
        {
            for (int j = 0; j <= 3 - this.recipeHeight; ++j)
            {
                if (this.checkMatch(p_77569_1_, i, j, true))
                {
                    return true;
                }

                if (this.checkMatch(p_77569_1_, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_)
    {
        for (int k = 0; k < 3; ++k)
        {
            for (int l = 0; l < 3; ++l)
            {
                int i1 = k - p_77573_2_;
                int j1 = l - p_77573_3_;
                ItemStack itemstack = null;

                if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight)
                {
                    if (p_77573_4_)
                    {
                        itemstack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
                    }
                    else
                    {
                        itemstack = this.recipeItems[i1 + j1 * this.recipeWidth];
                    }
                }

                ItemStack itemstack1 = p_77573_1_.getStackInRowAndColumn(k, l);

                if (itemstack1 != null || itemstack != null)
                {
                    if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null)
                    {
                        return false;
                    }

                    if (itemstack.getItem() != itemstack1.getItem())
                    {
                        return false;
                    }

                    if (itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack1.getItemDamage())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ItemStack getCraftingResult(InventoryCrafting p_77572_1_)
    {
        ItemStack itemstack = this.getRecipeOutput().copy();

        if (this.field_92101_f)
        {
            for (int i = 0; i < p_77572_1_.getSizeInventory(); ++i)
            {
                ItemStack itemstack1 = p_77572_1_.getStackInSlot(i);

                if (itemstack1 != null && itemstack1.hasTagCompound())
                {
                    itemstack.setTagCompound((NBTTagCompound)itemstack1.stackTagCompound.copy());
                }
            }
        }

        return itemstack;
    }

    public int getRecipeSize()
    {
        return this.recipeWidth * this.recipeHeight;
    }

    public ShapedRecipes func_92100_c()
    {
        this.field_92101_f = true;
        return this;
    }
}