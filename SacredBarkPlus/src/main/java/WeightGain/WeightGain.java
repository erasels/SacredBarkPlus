package WeightGain;

import basemod.BaseMod;
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
    }

    @Override
    public void receivePreStartGame() {
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        
    }
}