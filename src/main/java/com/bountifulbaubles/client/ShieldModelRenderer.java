package com.bountifulbaubles.client;

import com.bountifulbaubles.BountifulBaubles;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.ItemStack;
import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;
import net.minecraft.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/** Draw the original shield cuboids and face UVs using the 1.6.4 renderer. */
public final class ShieldModelRenderer {
    private static final Map<String, JsonArray> MODELS = new HashMap<>();

    private ShieldModelRenderer() {}

    public static void render(ItemStack stack) {
        String path = stack.getItem().getUnlocalizedName().substring("item.bountifulbaubles.".length());
        JsonArray elements = MODELS.get(path);
        if (elements == null) {
            try (InputStreamReader reader = new InputStreamReader(
                    ShieldModelRenderer.class.getResourceAsStream("/assets/bountifulbaubles/models/item/" + path + ".json"),
                    StandardCharsets.UTF_8)) {
                elements = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonArray("elements");
                MODELS.put(path, elements);
            } catch (Exception failure) {
                throw new IllegalStateException("Cannot load shield model " + path, failure);
            }
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(
            BountifulBaubles.MOD_ID, "textures/models/" + path + ".png"));
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_CURRENT_BIT);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glColor4f(1, 1, 1, 1);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        for (JsonElement element : elements) {
            JsonObject cuboid = element.getAsJsonObject();
            JsonArray from = cuboid.getAsJsonArray("from"), to = cuboid.getAsJsonArray("to");
            double x0 = from.get(0).getAsDouble(), y0 = from.get(1).getAsDouble(), z0 = from.get(2).getAsDouble();
            double x1 = to.get(0).getAsDouble(), y1 = to.get(1).getAsDouble(), z1 = to.get(2).getAsDouble();
            JsonObject faces = cuboid.getAsJsonObject("faces");
            face(tessellator, faces, "south", new double[][] {{x0,y1,z1},{x0,y0,z1},{x1,y0,z1},{x1,y1,z1}}, 0,0,1);
            face(tessellator, faces, "north", new double[][] {{x1,y1,z0},{x1,y0,z0},{x0,y0,z0},{x0,y1,z0}}, 0,0,-1);
            face(tessellator, faces, "east", new double[][] {{x1,y1,z1},{x1,y0,z1},{x1,y0,z0},{x1,y1,z0}}, 1,0,0);
            face(tessellator, faces, "west", new double[][] {{x0,y1,z0},{x0,y0,z0},{x0,y0,z1},{x0,y1,z1}}, -1,0,0);
            face(tessellator, faces, "up", new double[][] {{x0,y1,z0},{x0,y1,z1},{x1,y1,z1},{x1,y1,z0}}, 0,1,0);
            face(tessellator, faces, "down", new double[][] {{x0,y0,z1},{x0,y0,z0},{x1,y0,z0},{x1,y0,z1}}, 0,-1,0);
        }
        tessellator.draw();
        GL11.glPopAttrib();
    }

    private static void face(Tessellator tessellator, JsonObject faces, String name,
                             double[][] vertices, float nx, float ny, float nz) {
        if (!faces.has(name)) return;
        JsonArray uv = faces.getAsJsonObject(name).getAsJsonArray("uv");
        double u0 = uv.get(0).getAsDouble() / 16, v0 = uv.get(1).getAsDouble() / 16;
        double u1 = uv.get(2).getAsDouble() / 16, v1 = uv.get(3).getAsDouble() / 16;
        double[][] tex = {{u0,v0},{u0,v1},{u1,v1},{u1,v0}};
        tessellator.setNormal(nx, ny, nz);
        for (int i = 0; i < 4; i++) {
            tessellator.addVertexWithUV(vertices[i][0], vertices[i][1], vertices[i][2], tex[i][0], tex[i][1]);
        }
    }
}
