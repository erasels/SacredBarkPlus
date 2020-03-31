package SacredBarkPlus.patches;

import SacredBarkPlus.events.NothinessEvent;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.random.Random;

public class EventslessSpirePatchess {
    @SpirePatch(clz = AbstractDungeon.class, method = "generateEvent")
    public static class MyEventPls {
        @SpirePostfixPatch
        public static AbstractEvent patch(AbstractEvent __result, Random rng) {
            return new NothinessEvent();
        }
    }
}
