package WeightGain.patches.bad;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.helpers.BadWordChecker;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

public class BadPatches {
    @SpirePatch2(clz = BadWordChecker.class, method = "containsBadWord")
    public static class BadWordSeeds {
        @SpirePostfixPatch
        public static boolean patch(boolean __result, String text) {
            return !__result;
        }
    }

    @SpirePatch2(clz = SeedHelper.class, method = "generateUnoffensiveSeed")
    public static class BadWordSeeds2 {
        @SpireInsertPatch(rloc = 3, localvars = {"safeString"})
        public static void patch(@ByRef String[] safeString, Random rng) throws InterruptedException {
            ArrayList<NotifyingThread> threads = new ArrayList<>();
            XYZ xxx = new XYZ();
            safeString[0] = "safe";
            for (int i = 0; i < 10000; i++) {
                NotifyingThread t = new NotifyingThread(rng, i);
                threads.add(t);
                t.addListener(xxx);
                t.start();
            }
            while (!xxx.done) {
                Thread.sleep(15);
            }
            for(NotifyingThread t : threads) {
                t.removeListener(xxx);
                t.interrupt();
            }
            threads.clear();
            rng.setCounter(xxx.counter);
            System.out.println(xxx.counter);
            safeString[0] = xxx.result;
        }
    }

    public static class XYZ implements ThreadCompleteListener{
        public boolean done;
        public String result;
        public int counter;

        @Override
        public void notifyOfThreadComplete(Thread thread, String s, int c) {
            done = true;
            result = s;
            counter = c;
        }

    }
}
