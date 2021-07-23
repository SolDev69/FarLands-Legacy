package com.example.examplemod.coremod;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class FarTransformer implements IClassTransformer
{
    private final Config config = Config.instance;

    @Override
    public byte[] transform(String name, String transformedName, byte[] classBytes)
    {
        boolean isObfuscated = !name.equals(transformedName);
        if(transformedName.equals("net.minecraft.world.gen.NoiseGeneratorOctaves") && config.isFarLands)
            return patchClassASMOcto(name, classBytes, isObfuscated);
        return classBytes;
    }

    public byte[] patchClassASMOcto(String name, byte[] classBytes, boolean obfuscated)
    {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(classBytes);
        classReader.accept(classNode, 0);

        boolean pass = false;
        for(MethodNode method : classNode.methods)
            if(method.desc.equals("([DIIIIIIDDD)[D"))
                for(AbstractInsnNode ain : method.instructions.toArray())
                    if(ain.getOpcode() == Opcodes.LDC
                            && ((LdcInsnNode)ain).cst instanceof Long && (Long)((LdcInsnNode)ain).cst == 16777216L)
                    {
                        LogManager.getLogger().info("test");
                        ((LdcInsnNode)ain).cst = Long.MAX_VALUE;
                        pass = true;
                    }
        if(pass)
            LogManager.getLogger().info("[FarLands] Noise generator patched successfully!");

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
