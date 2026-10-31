package dev.blackclient.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class AppearanceScreen extends Screen {
    private final Screen parent;
    private int theme = 0;
    private boolean particles = true;
    private boolean reducedMotion = false;

    public AppearanceScreen(Screen parent) {
        super(Text.literal("Black Client — Внешний вид"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = width / 2 - 125;
        int y = height / 2 - 55;
        addDrawableChild(ButtonWidget.builder(themeLabel(), b -> {
            theme = (theme + 1) % 4;
            b.setMessage(themeLabel());
        }).dimensions(x, y, 250, 24).build());
        addDrawableChild(ButtonWidget.builder(particlesLabel(), b -> {
            particles = !particles;
            b.setMessage(particlesLabel());
        }).dimensions(x, y + 31, 250, 24).build());
        addDrawableChild(ButtonWidget.builder(motionLabel(), b -> {
            reducedMotion = !reducedMotion;
            b.setMessage(motionLabel());
        }).dimensions(x, y + 62, 250, 24).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Назад"), b -> client.setScreen(parent))
                .dimensions(x, y + 100, 250, 24).build());
    }

    private Text themeLabel() {
        String[] names = {"Фиолетовая", "Красная", "Синяя", "Зелёная"};
        return Text.literal("Тема: " + names[theme] + "  ›");
    }
    private Text particlesLabel() {
        return Text.literal("Частицы: " + (particles ? "ВКЛ" : "ВЫКЛ"));
    }
    private Text motionLabel() {
        return Text.literal("Упрощённая анимация: " + (reducedMotion ? "ВКЛ" : "ВЫКЛ"));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, width, height, 0xF2050505);
        ctx.drawCenteredTextWithShadow(textRenderer, title, width / 2, height / 2 - 92, 0xFFCCCCCC);
        ctx.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Выбери оформление BLACK CLIENT"), width / 2, height / 2 - 75, 0xFFF5EEFF);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }
}
