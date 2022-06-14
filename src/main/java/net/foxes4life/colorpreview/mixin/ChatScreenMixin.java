package net.foxes4life.colorpreview.mixin;

import net.foxes4life.colorpreview.Parser;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {
    @Shadow protected TextFieldWidget chatField;
    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        assert this.client != null;

        int offset = 16;
        fill(matrices, 2, this.height - (14+offset), this.width - 2, this.height - (offset), this.client.options.getTextBackgroundColor(Integer.MIN_VALUE));
        drawTextWithShadow(matrices, textRenderer, Parser.parse(this.chatField.getText()), 4, this.height - (11+offset), 0xffffff);
    }
}
