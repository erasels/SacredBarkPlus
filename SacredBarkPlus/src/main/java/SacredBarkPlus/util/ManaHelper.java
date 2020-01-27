package SacredBarkPlus.util;

import SacredBarkPlus.patches.ManaPatches;
import SacredBarkPlus.vfx.general.IncreaseManaPanelEffect;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

public class ManaHelper {
    public static final Texture MANA_ICON = TextureLoader.getTexture("manaResources/img/ui/mana_small.png");

    public static int getMP() {
        return ManaPatches.ManaField.mp.get(AbstractDungeon.player);
    }

    public static void setMP(int i) {
        int maxmp = getMaxMP();
        if (i > maxmp) {
            i = maxmp;
        } else if (i < 0) {
            i = 0;
        }
        ManaPatches.ManaField.mp.set(AbstractDungeon.player, i);
    }

    public static void addMP(int i) {
        setMP(getMP() + i);
        AbstractDungeon.topLevelEffectsQueue.add(new IncreaseManaPanelEffect((Float) ReflectionHacks.getPrivate(AbstractDungeon.topPanel, TopPanel.class, "hpIconX")));
    }

    public static void loseMP(int i) {
        if (i < 0) {
            i *= -1;
        }
        setMP(getMP() - i);
    }

    public static int getMaxMP() {
        return ManaPatches.ManaField.maxMP.get(AbstractDungeon.player);
    }

    public static boolean hasMana() {
        return getMaxMP() > -1;
    }
}
