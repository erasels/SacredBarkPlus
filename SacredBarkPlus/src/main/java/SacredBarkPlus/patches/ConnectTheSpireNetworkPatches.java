package SacredBarkPlus.patches;

import SacredBarkPlus.util.TextureLoader;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import javassist.CtBehavior;

import static com.megacrit.cardcrawl.shop.Merchant.DRAW_X;
import static com.megacrit.cardcrawl.shop.Merchant.DRAW_Y;

public class ConnectTheSpireNetworkPatches {
    public static Texture tex = TextureLoader.getTexture("manaResources/img/ui/monster.png");
    @SpirePatch(clz = Merchant.class, method = "update")
    public static class NoShopping {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> patch(Merchant __instance) {
            __instance.hb.hovered = false;
            AbstractDungeon.effectList.add(new SpeechBubble(__instance.hb.cX - 50.0F * Settings.scale, __instance.hb.cY + 70.0F * Settings.scale, 3.0F, "You need to connect the Spiral Network!", false));
            return SpireReturn.Return(null);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ProceedButton.class, "setLabel");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = Merchant.class, method = "render")
    public static class MonsterCans {
        @SpirePostfixPatch
        public static void patch(Merchant __instance, SpriteBatch sb) {
            sb.setColor(Color.WHITE);
            sb.draw(tex,
                    DRAW_X + (float)ReflectionHacks.getPrivate(__instance, Merchant.class, "modX") + (270f * Settings.scale),
                    DRAW_Y + (float)ReflectionHacks.getPrivate(__instance, Merchant.class, "modY"),
                    0,
                    0,
                    tex.getWidth() * Settings.scale * 0.5f,
                    tex.getHeight() * Settings.scale * 0.5f,
                    1f,
                    1f,
                    -6f,
                    0,
                    0,
                    tex.getWidth(),
                    tex.getHeight(),
                    false,
                    false
            );

            if (__instance.hb.hovered) {
                sb.setBlendFunction(770, 1);
                sb.setColor(Settings.HALF_TRANSPARENT_WHITE_COLOR);
                sb.draw(tex,
                        DRAW_X + (float)ReflectionHacks.getPrivate(__instance, Merchant.class, "modX") + (270f * Settings.scale),
                        DRAW_Y + (float)ReflectionHacks.getPrivate(__instance, Merchant.class, "modY"),
                        0,
                        0,
                        tex.getWidth() * Settings.scale * 0.5f,
                        tex.getHeight() * Settings.scale * 0.5f,
                        1f,
                        1f,
                        -6f,
                        0,
                        0,
                        tex.getWidth(),
                        tex.getHeight(),
                        false,
                        false
                );
                sb.setBlendFunction(770, 771);
                sb.setColor(Color.WHITE);
            }
        }
    }
}
