package WeightGain.patches.bad;

import com.megacrit.cardcrawl.helpers.BadWordChecker;
import com.megacrit.cardcrawl.random.Random;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.megacrit.cardcrawl.helpers.SeedHelper.getString;

public class NotifyingThread extends Thread {
    Random rng;
    int i, counter;

    public NotifyingThread(Random rng, int i) {
        this.rng = rng.copy();
        this.i = i;
    }

    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<>();
    public final void addListener(final ThreadCompleteListener listener) {
        listeners.add(listener);
    }
    public final void removeListener(final ThreadCompleteListener listener) {
        listeners.remove(listener);
    }
    private void notifyListeners(String s) {
        for (ThreadCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this, s, counter);
        }
    }
    @Override
    public final void run() {
        String s = "";
        try {
             s = doRun();
        } finally {
            notifyListeners(s);
        }
    }
    public String doRun() {
        String safeString = "safe";
        rng.setCounter(i);
        while (BadWordChecker.containsBadWord(safeString)) {
            long possible = rng.randomLong();
            rng.setCounter(rng.counter + (i - 1));
            safeString = getString(possible);
        }
        counter = rng.counter;
        return safeString;
    }
}
