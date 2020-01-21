package SacredBarkPlus;

import SacredBarkPlus.actions.DelayACtionAction;
import SacredBarkPlus.actions.ReplaceCardAction;
import basemod.BaseMod;
import basemod.interfaces.*;


import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;

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

    public static ProxyFactory proxf = new ProxyFactory();
    private static boolean trig = false;

    @Override
    public void receiveCardUsed(AbstractCard card) {
        proxf.setSuperclass(card.getClass());
        proxf.setFilter(m -> m.getName().equals("canUse") ||
                m.getName().equals("hasEnoughEnergy"));
        //Class c = f.createClass();
        MethodHandler mi = (self, m, proceed, args) -> {
            boolean tmp = (boolean) proceed.invoke(self, args);
            return tmp || true;  // execute the original method.
        };

        AbstractCard newCard = null;
        try {
            newCard = (AbstractCard) proxf.create(new Class[0], new Object[0], mi);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if(!trig) {
            AbstractDungeon.actionManager.addToBottom(new DelayACtionAction(new ReplaceCardAction(newCard)));
            trig = true;
        }
    }
}