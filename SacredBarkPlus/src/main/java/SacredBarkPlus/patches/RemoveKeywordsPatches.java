package SacredBarkPlus.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.TipHelper;

import java.util.ArrayList;

public class RemoveKeywordsPatches {
    @SpirePatch(clz = TipHelper.class, method = "renderKeywords")
    public static class RemoveBaseKeywords {
        @SpirePrefixPatch
        public static SpireReturn<Void> patch(float x, float y, SpriteBatch sb, @ByRef ArrayList<String>[] keywords) {
            return SpireReturn.Return(null);
        }
    }
}
