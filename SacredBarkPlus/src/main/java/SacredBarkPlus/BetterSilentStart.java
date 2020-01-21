package SacredBarkPlus;

import SacredBarkPlus.actions.DelayACtionAction;
import SacredBarkPlus.actions.ReplaceCardAction;
import SacredBarkPlus.util.ProxyFactoryHelper;
import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpireInitializer
public class BetterSilentStart implements
        PostInitializeSubscriber,
        OnCardUseSubscriber {
    public static void initialize() {
        BaseMod.subscribe(new BetterSilentStart());
    }

    @Override
    public void receivePostInitialize() {

    }
    
    private static boolean trig = false;

    @Override
    public void receiveCardUsed(AbstractCard card) {
        AbstractCard newCard = ProxyFactoryHelper.getAlwaysPlayableProxy(card);

        if(!trig) {
            AbstractDungeon.actionManager.addToBottom(new DelayACtionAction(new ReplaceCardAction(newCard)));
            trig = true;
        }
    }
}