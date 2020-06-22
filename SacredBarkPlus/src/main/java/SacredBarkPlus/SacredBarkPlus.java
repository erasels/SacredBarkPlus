package SacredBarkPlus;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class SacredBarkPlus implements
        PostInitializeSubscriber, PostUpdateSubscriber {
    public static final Logger logger = LogManager.getLogger(SacredBarkPlus.class.getName());

    public static void initialize() {
        BaseMod.subscribe(new SacredBarkPlus());
    }

    @Override
    public void receivePostInitialize() {

    }

    @Override
    public void receivePostUpdate() {

    }
}