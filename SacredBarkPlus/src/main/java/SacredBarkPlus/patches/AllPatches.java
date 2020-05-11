package SacredBarkPlus.patches;

import SacredBarkPlus.BetterSilentStart;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.*;

public class AllPatches {
    @SpirePatch(clz = MapRoomNode.class, method = SpirePatch.CLASS)
    public static class CDField {
        public static SpireField<Float> cooldown = new SpireField<>(() -> 0f);
    }

    @SpirePatch(clz = MapRoomNode.class, method = "playNodeHoveredSound")
    public static class PlaySounds {
        @SpirePrefixPatch
        public static SpireReturn<Void> patch(MapRoomNode __instance) {
            if(CDField.cooldown.get(__instance) > 0) {
                return SpireReturn.Continue();
            }

            if(__instance.room instanceof MonsterRoomElite) {
                CardCrawlGame.sound.play(BetterSilentStart.ELITE);
            } else if(__instance.room instanceof MonsterRoom) {
                CardCrawlGame.sound.play(BetterSilentStart.ENEMY);
            } else if (__instance.room instanceof ShopRoom) {
                CardCrawlGame.sound.play(BetterSilentStart.MERCHANT);
            } else if (__instance.room instanceof RestRoom) {
                CardCrawlGame.sound.play(BetterSilentStart.REST);
            } else if (__instance.room instanceof TreasureRoom) {
                CardCrawlGame.sound.play(BetterSilentStart.TREASURE);
            } else {
                CardCrawlGame.sound.play(BetterSilentStart.UNKNOWN);
            }
            CDField.cooldown.set(__instance, 0.5f);

            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(clz = MapRoomNode.class, method = "update")
    public static class ReduceCD {
        @SpirePostfixPatch
        public static void patch(MapRoomNode __instance) {
            CDField.cooldown.set(__instance, CDField.cooldown.get(__instance) - Gdx.graphics.getRawDeltaTime());
        }
    }

}
