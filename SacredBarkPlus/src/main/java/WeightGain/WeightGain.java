package WeightGain;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class WeightGain implements
        PostInitializeSubscriber{
    public static final Logger logger = LogManager.getLogger(WeightGain.class.getName());

    public static void initialize() {
        BaseMod.subscribe(new WeightGain());
    }

    @Override
    public void receivePostInitialize() {

    }
}