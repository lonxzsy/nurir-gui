package ru.karatel.desing.ClickGui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.MathUtil;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;
import ru.karatel.system.font.Fonts;
import ru.karatel.system.render.ColorUtils;
import ru.karatel.system.render.RenderUtils;

import java.awt.*;

/**
 * @author Ieo117
 * @created 16.06.2024
 */

@Setter
@Getter
public class SearchField {
    private int x, y, width, height;
    private String text;
    private boolean isFocused;
    private boolean typing;
    private final String placeholder;

    public SearchField(int x, int y, int width, int height, String placeholder) {
        this.x = x;
        this.y = y - 35;
        this.width = width;
        this.height = height + 7;
        this.placeholder = placeholder;
        this.text = "";
        this.isFocused = false;
        this.typing = false;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawOutlinedRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, 5,1,new Color(17,11,22, 255).getRGB(),new Color(17,11,22, 1).getRGB());
        RenderUtils.drawRoundedRect(x, y, width, height,5,  new Color(17,11,22, 255).getRGB());
        String cursor = typing && System.currentTimeMillis() % 1000 > 500 ? "_" : "";
        if (text.isEmpty() && !typing) {
            Fonts.pro[18].drawString(matrixStack, placeholder, x + height / 3, y + (height - 8) / 2 + 2, new Color(180,178,182).getRGB());
        } else {
            Fonts.pro[18].drawString(matrixStack, text + cursor, x + height / 3, y + (height - 8) / 2 + 2, new Color(255,255,255).getRGB());
        }
    }

    public boolean charTyped(char codePoint, int modifiers) {
        if (isFocused) {
            text += codePoint;
            return true;
        }
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (isFocused && keyCode == GLFW.GLFW_KEY_BACKSPACE && !text.isEmpty()) {
            text = text.substring(0, text.length() - 1);
            return true;
        }
        if(keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_ESCAPE){
            typing = false;
        }
        return false;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(!MathUtil.isHovered((float) mouseX, (float) mouseY, x, y, width, height)){
            isFocused = false;
        }
        isFocused = MathUtil.isHovered((float) mouseX, (float) mouseY, x, y, width, height);
        typing = isFocused;
        return isFocused;
    }

    public boolean isEmpty() {
        return text.isEmpty();
    }
    public void setFocused(boolean focused) { isFocused = focused; }
}
