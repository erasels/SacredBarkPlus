package SacredBarkPlus.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

public class ProxiedCardPatches {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class CardFields {
        public static SpireField<Boolean> limitBroken = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, Color.class})
    public static class CardGlowPatch {
        @SpirePostfixPatch
        public static void patch(CardGlowBorder __instance, AbstractCard c, Color col, @ByRef Color[] ___color) {
            if(CardFields.limitBroken.get(c)) {
                ___color[0] =  Color.FIREBRICK.cpy();
            }
        }
    }
}
