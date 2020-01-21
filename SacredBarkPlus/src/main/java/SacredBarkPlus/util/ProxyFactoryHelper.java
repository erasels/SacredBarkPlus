package SacredBarkPlus.util;

import SacredBarkPlus.patches.ProxiedCardPatches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;

public class ProxyFactoryHelper {
    public static ProxyFactory pF = new ProxyFactory();
    private static ProxyFactory internalPF = new ProxyFactory();

    public static AbstractCard getAlwaysPlayableProxy(AbstractCard card) {
        ProxyFactoryHelper.internalPF.setSuperclass(card.getClass());
        MethodFilter mF = m -> m.getName().equals("canUse") ||
                m.getName().equals("hasEnoughEnergy") ||
                m.getName().equals("makeCopy");
        ProxyFactoryHelper.internalPF.setFilter(mF);
        //Class c = f.createClass();
        MethodHandler mi = (self, m, proceed, args) -> {
            if(!m.getName().equals("makeCopy")) {
                boolean tmp = (boolean) proceed.invoke(self, args);
                return tmp || ProxiedCardPatches.CardFields.limitBroken.get(self);  // execute the original method.
            } else {
                return getAlwaysPlayableProxy((AbstractCard)proceed.invoke(self, args));
            }
        };

        try {
            AbstractCard tmp = (AbstractCard) ProxyFactoryHelper.internalPF.create(new Class[0], new Object[0], mi);
            ProxiedCardPatches.CardFields.limitBroken.set(tmp, true);
            return tmp;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
