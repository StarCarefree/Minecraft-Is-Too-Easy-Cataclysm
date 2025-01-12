package com.starkettle.mite_ctm.keymappings;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class ModKeyMappings {
    public static final KeyMapping ZOOM_KEY_MAPPING=new KeyMapping(
            "key.mite_ctm.zoom",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "mite_ctm.mod_name"
    );
}
