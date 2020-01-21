package SacredBarkPlus.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ReplaceCardAction extends AbstractGameAction {
    private AbstractCard c;

    public ReplaceCardAction(AbstractCard c) {
        this.c = c;
    }

    @Override
    public void update() {
        Iterator<AbstractCard> iter = AbstractDungeon.player.discardPile.group.iterator();
        for(int i = 0; iter.hasNext(); i++) {
            AbstractCard card = iter.next();
            if(card.cardID.equals(c.cardID)) {
                AbstractDungeon.player.discardPile.group.set(i, c);
            }
        }
        isDone = true;
    }
}
