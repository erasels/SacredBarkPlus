package SacredBarkPlus.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class NoFightingPatches {
    @SpirePatch(clz = MonsterRoom.class, method = "onPlayerEntry")
    public static class RemoveMonsters {
        @SpirePostfixPatch
        public static void patch(MonsterRoom __instance) {
            __instance.monsters.escape();
            __instance.monsters.monsters.forEach(m -> m.drawX = -300);
        }
    }

    @SpirePatch(clz = MonsterRoomElite.class, method = "onPlayerEntry")
    public static class RemoveElites {
        @SpirePostfixPatch
        public static void patch(MonsterRoomElite __instance) {
            __instance.monsters.escape();
            __instance.monsters.monsters.forEach(m -> m.drawX = Settings.WIDTH + 300);
        }
    }

    @SpirePatch(clz = MonsterRoomBoss.class, method = "onPlayerEntry")
    public static class RemoveBosses {
        @SpirePostfixPatch
        public static void patch(MonsterRoomBoss __instance) {
            __instance.monsters.escape();
            __instance.monsters.monsters.forEach(m -> m.drawX = Settings.WIDTH + 300);
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "escape")
    public static class FasterEscape {
        @SpirePostfixPatch
        public static void patch(AbstractMonster __instance) {
            __instance.escapeTimer = 0.5f;
        }
    }
}
