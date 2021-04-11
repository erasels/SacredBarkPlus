package WeightGain;

import WeightGain.patches.AllPatches;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PreStartGameSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class WeightGain implements
        PostInitializeSubscriber,
        PreStartGameSubscriber,
        OnStartBattleSubscriber {
    public static final Logger logger = LogManager.getLogger(WeightGain.class.getName());

    public static void initialize() {
        BaseMod.subscribe(new WeightGain());
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addSaveField("weightGain:Feeds", new CustomSavable<Integer>() {
            @Override
            public Integer onSave() {
                return AllPatches.feeds;
            }

            @Override
            public void onLoad(Integer i) {
                AllPatches.feeds = i;
            }
        });
    }

    @Override
    public void receivePreStartGame() {
        AllPatches.feeds = 0;
        AllPatches.fasts = 0;
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        AllPatches.fasts = 0;
    }
}