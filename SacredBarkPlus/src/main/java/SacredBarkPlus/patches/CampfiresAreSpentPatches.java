package SacredBarkPlus.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Arrays;

public class CampfiresAreSpentPatches {
    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class RestBoyYouNeedIt {
        @SpirePostfixPatch
        public static void patch(CampfireUI __insatnce) {
            ReflectionHacks.setPrivate(__insatnce, CampfireUI.class, "buttons", new ArrayList<AbstractCampfireOption>(Arrays.asList(new RestOption(true))));
        }
    }

    @SpirePatch(clz = RestOption.class, method = SpirePatch.CONSTRUCTOR)
    public static class DisplayManaOnRest {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(RestOption __instance, boolean active, @ByRef String[] ___description) {
            ___description[0] = "You need to rest after all this walking.";
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(RestOption.class, "updateUsability");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
