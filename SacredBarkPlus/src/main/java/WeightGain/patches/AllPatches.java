package WeightGain.patches;

import WeightGain.WeightGain;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.cards.purple.Fasting;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import org.apache.commons.lang3.math.NumberUtils;


public class AllPatches {
    public static int feeds = 0;
    public static int fasts = 0;
    private static final float WEIGHT_TO_WIDE = 0.05f;
    private static final float WEIGHT_TO_THINN = 0.1f;

    @SpirePatch(clz = FeedAction.class, method = "update")
    public static class FeedingBoi {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(FeedAction __instance) {
            Skeleton skel = ReflectionHacks.getPrivate(AbstractDungeon.player, AbstractCreature.class, "skeleton");
            if(skel != null) {
                feeds++;
                WeightGain.logger.info("Character has been fed.");
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "increaseMaxHp");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "renderPlayerImage")
    @SpirePatch(clz = Watcher.class, method = "renderPlayerImage")
    public static class FatBoi {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractPlayer __instance, SpriteBatch sb, Skeleton ___skeleton) {
            if(___skeleton != null) {
                Bone root = ___skeleton.getRootBone();
                root.setScaleX(root.getScaleX() + (feeds * WEIGHT_TO_WIDE));
                root.setScaleY(root.getScaleY() + (feeds * (WEIGHT_TO_WIDE/4.5f)));

                root.setScaleX(NumberUtils.max(root.getScaleX() - (fasts * WEIGHT_TO_THINN), 0.1f));
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Skeleton.class, "updateWorldTransform");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = Fasting.class, method = "use")
    public static class FastingMakesThin {
        @SpirePostfixPatch
        public static void patch(Fasting __instance, AbstractPlayer p, AbstractMonster m) {
            if(p != null) {
                Skeleton skel = ReflectionHacks.getPrivate(p, AbstractCreature.class, "skeleton");
                if(skel != null) {
                    fasts++;
                    WeightGain.logger.info("Character has been lost weight.");
                }
            }
        }
    }
}
