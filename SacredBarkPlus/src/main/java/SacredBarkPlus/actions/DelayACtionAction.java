package SacredBarkPlus.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DelayACtionAction extends AbstractGameAction {
    AbstractGameAction act;

    public DelayACtionAction(AbstractGameAction act) {
        this.act = act;
    }

    @Override
    public void update() {
        addToBot(act);
        isDone = true;
    }
}
