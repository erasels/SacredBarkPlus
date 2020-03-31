package SacredBarkPlus.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;

public class NothinessEvent extends AbstractImageEvent {


    public static final String ID = "STRANDTYPEIDTHINGYLOLDUPLICATESAREJOKES";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Dead Adventurer");

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = "manaResources/img/ui/mana_small.png";

    private int screenNum = 0;

    public NothinessEvent() {
        super("", "Nothing is around, you continue to walk.", IMG);

        imageEventText.setDialogOption("[Walk]");
    }

    @Override
    protected void buttonEffect(int i) {
        openMap();
    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
    }
}