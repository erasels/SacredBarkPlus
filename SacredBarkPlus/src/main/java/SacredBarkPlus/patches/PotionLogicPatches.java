package SacredBarkPlus.patches;

import com.evacipated.cardcrawl.mod.hubris.relics.EmptyBottle;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.SacredBark;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;

import static SacredBarkPlus.SacredBarkPlus.affectedPotions;
import static SacredBarkPlus.SacredBarkPlus.hasHubris;

public class PotionLogicPatches {
    public static AbstractPotion potion;

    private static void Do(AbstractPotion pot) {
        if (!(hasHubris && AbstractDungeon.player.hasRelic(EmptyBottle.ID)) && AbstractDungeon.player.hasRelic(SacredBark.ID)) {
            if (affectedPotions.contains(pot.ID)) {
                int useCount = PotionUsePatches.PotionUseField.useCount.get(pot);
                PotionUsePatches.PotionUseField.useCount.set(pot, --useCount);

                if (useCount > 0) {
                    potion = pot;
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class FairyPotion {
        @SpireInsertPatch(locator = Locator.class, localvars = {"p"})
        public static void Insert(AbstractPlayer __instance, DamageInfo info, AbstractPotion potion) {
            Do(potion);
        }
    }

    @SpirePatch(clz = PotionPopUp.class, method = "updateInput")
    @SpirePatch(clz = PotionPopUp.class, method = "updateTargetMode")
    public static class NormalPotions {
        @SpireInsertPatch(locator = Locator.class, localvars = {"potion"})
        public static void Insert(PotionPopUp __instance, AbstractPotion potion) {
            Do(potion);
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(TopPanel.class, "destroyPotion");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
