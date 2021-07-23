package com.example.examplemod.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraft.init.Blocks;

import java.util.Map;

public class CoreMod implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{FarTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}