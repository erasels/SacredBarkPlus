package SacredBarkPlus.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble;

public class BlackNeowPatch {
    @SpirePatch(clz = NeowEvent.class, method = "render")
    public static class ProbsWrongName {
        public static final Color col = Color.WHITE.cpy();

        @SpirePrefixPatch
        public static void patch1(NeowEvent __instance, SpriteBatch sb) {
            Color.WHITE.set(Color.BLACK);
        }

        @SpirePostfixPatch
        public static void patch2(NeowEvent __instance, SpriteBatch sb) {
            Color.WHITE.set(col);
        }
    }

    @SpirePatch(clz = NeowEvent.class, method = "talk")
    public static class DiffText {
        @SpirePrefixPatch
        public static SpireReturn<Void> patch(NeowEvent __instance, String msg) {
            String[] tmp = {"~You~ ~are~ ~the~ ~Extinction~ ~Entity...~", "I'm ~Stranded~ at your ~Beach~ ...", "I'm the ~Beached~ whale...", "You son of a @Beach@ ...", "~The~ ~first~ @Strand-type@ ~Spire...~", "~And~ ~I~ ~Would~ ~Walk~ ~500~ ~Miles~", "I'm @Fragile@ but I'm not ~that~ @fragile...@", "You're my Princess @Beach@ ...", "@YOU'RE@ @DAMAGED@ @GOODS!@"};
            AbstractDungeon.effectList.add(new InfiniteSpeechBubble((float)ReflectionHacks.getPrivateStatic(NeowEvent.class, "DIALOG_X"), (float)ReflectionHacks.getPrivateStatic(NeowEvent.class, "DIALOG_Y"), tmp[MathUtils.random(0, tmp.length - 1)]));
            return SpireReturn.Return(null);
        }
    }
}

