package dev.blackclient.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class BlackTitleScreen extends Screen {
    private long openedAt;
    private static final int BLACK = 0xFF050505;
    private static final int WHITE = 0xFFE2E2E2;
    private static final int GRAY = 0xFF777777;

    public BlackTitleScreen() { super(Text.literal("Black Client")); }

    @Override
    protected void init() {
        openedAt = System.currentTimeMillis();
        int bw = Math.min(250, width / 3);
        int x = Math.max(24, width / 16); // buttons aligned left
        int y = Math.max(110, height / 2 - 20);
        int gap = 32;
        addDrawableChild(ButtonWidget.builder(Text.literal("Играть"), b -> client.setScreen(new SelectWorldScreen(this)))
                .dimensions(x, y, bw, 25).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Мультиплеер"), b -> client.setScreen(new MultiplayerScreen(this)))
                .dimensions(x, y + gap, bw, 25).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Настройки"), b -> client.setScreen(new OptionsScreen(this, client.options)))
                .dimensions(x, y + gap * 2, bw, 25).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Внешний вид"), b -> client.setScreen(new AppearanceScreen(this)))
                .dimensions(x, y + gap * 3, bw, 25).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Выход"), b -> client.scheduleStop())
                .dimensions(x, y + gap * 4, bw, 25).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        double t = (System.currentTimeMillis() - openedAt) / 1000.0;
        ctx.fill(0, 0, width, height, BLACK);
        // Monochrome night sky and distant blocky silhouettes.
        ctx.fill(0, height / 2, width, height, 0xFF101010);
        int moonX = (int)(width * .78), moonY = (int)(height * .22);
        ctx.fill(moonX, moonY, moonX + 34, moonY + 34, 0xFFBEBEBE);
        ctx.fill(moonX + 5, moonY + 4, moonX + 12, moonY + 11, 0xFF999999);
        ctx.fill(moonX + 21, moonY + 17, moonX + 29, moonY + 26, 0xFF969696);
        for (int i = 0; i < 18; i++) {
            int x = (i * 113) % Math.max(1, width);
            int y = (i * 47) % Math.max(1, height / 2);
            ctx.fill(x, y, x + 1, y + 1, 0xFF666666);
        }
        // Low-contrast voxel hills and trees; no bright accent colors.
        for (int i = 0; i < 24; i++) {
            int x = i * Math.max(1, width / 23) - 12;
            int h = height / 8 + (i * 37 % Math.max(1, height / 5));
            int top = height - h;
            int shade = 12 + (i % 4) * 3;
            int color = 0xFF000000 | shade << 16 | shade << 8 | shade;
            ctx.fill(x, top, x + Math.max(12, width / 18), height, color);
            if (i % 3 == 0) {
                ctx.fill(x + 5, top - 24, x + 29, top + 3, 0xFF080808);
                ctx.fill(x + 10, top - 39, x + 25, top - 18, 0xFF0B0B0B);
            }
        }
        // A restrained shimmer on the water line.
        int waterY = (int)(height * .82);
        for (int i = 0; i < 16; i++) {
            int x = (int)((i * 89 + t * (3 + i % 3)) % Math.max(1, width));
            ctx.fill(x, waterY + i % 5 * 3, x + 18 + i % 12, waterY + i % 5 * 3 + 1, 0xFF292929);
        }
        int left = Math.max(24, width / 16);
        ctx.drawTextWithShadow(textRenderer, Text.literal("BLACK"), left, height / 4, WHITE);
        ctx.drawText(textRenderer, Text.literal("C L I E N T"), left + 1, height / 4 + 19, GRAY, false);
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawText(textRenderer, Text.literal("Black Client v1.0.0"), 12, 12, GRAY, false);
        ctx.drawText(textRenderer, Text.literal("Minecraft 1.21.4"), 12, height - 18, GRAY, false);
    }

    @Override
    public void close() { if (client != null) client.setScreen(new TitleScreen()); }
}
