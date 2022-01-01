package WeightGain.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AllPatches {
    public static HashMap<String, String> uwuBank = new HashMap<>();
    public static HashMap<String, String> wordReplacements = new HashMap<String, String>() {{
        put("what", "wat");
        put("yes", "ye");
        put("have", "haz");
        put("has", "haz");
        put("please", "pls");
        put("thanks", "thx");
        put("because", "cuz");
        put("easy", "ez");
        put("good", "gud");
        put("this", "dis");
        put("that", "dat");
        put("them", "dem");
    }};

    public static HashMap<String, String> regexReplacements = new HashMap<String, String>() {{
        put("(%A)you", "%1u");
        put("^you", "u");
        put("([bcdfghjkmnpqrstvxz])l([^yl])", "%1w%2");
        put("ll", "wl");
        put("r(%a)", "w%1");
        put("th(%A)", "f%1");
        put("th$", "f");
        put("(%A)no(%A)", "%1nuu%2");
        put("^no(%A)", "nuu%1");
        put("^no$", "nuu");
        put("(%A)to(%A)", "%12%2");
        put("^to(%A)", "2%1");
        put("(%A)for(%A)", "%14%2");
        put("^for(%A)", "4%1");
        put("(%A)and(%A)", "%1nd%2");
        put("^and(%A)", "nd%1");
        put("'", "");
        put("([^.])%.$", "%1");
    }};

    public static ArrayList<String> prefixes = new ArrayList<>(Arrays.asList("uwaaa! ", "murr ", "rawr ","nyea "));
    public static ArrayList<String> postfixes = new ArrayList<>(Arrays.asList(" OwO", " UwU", " :3", " xD", " <3", " o.o", " >w<"," :v", " x3", " :o", " o3o", " o///o", " ^w^"));

    @SpirePatch2(clz = FontHelper.class, method = "renderTipLeft")
    @SpirePatch2(clz = FontHelper.class, method = "renderFont")
    @SpirePatch2(clz = FontHelper.class, method = "renderRotatedText")
    @SpirePatch2(clz = FontHelper.class, method = "renderWrappedText", paramtypez = {SpriteBatch.class, BitmapFont.class, String.class, float.class, float.class, float.class, Color.class, float.class})
    @SpirePatch2(clz = FontHelper.class, method = "renderFontRightToLeft")
    @SpirePatch2(clz = FontHelper.class, method = "renderSmartText", paramtypez = {SpriteBatch.class, BitmapFont.class, String.class, float.class, float.class, float.class, float.class, Color.class})
    @SpirePatch2(clz = FontHelper.class, method = "getSmartHeight")
    @SpirePatch2(clz = FontHelper.class, method = "getHeightForCharLineBreak")
    @SpirePatch2(clz = FontHelper.class, method = "getSmartWidth", paramtypez = {BitmapFont.class, String.class, float.class, float.class})
    @SpirePatch2(clz = FontHelper.class, method = "renderFontCenteredTopAligned")
    public static class UwUText {
        @SpirePrefixPatch
        public static void patch(@ByRef String[] msg) {
            if (!NumberUtils.isParsable(msg[0])) {
                msg[0] = uWillTakeIt(msg[0], true);
            }
        }
    }

    @SpirePatch2(clz = FontHelper.class, method = "getHeight", paramtypez = {BitmapFont.class, String.class, float.class})
    @SpirePatch2(clz = FontHelper.class, method = "getWidth")
    public static class UwUText2 {
        @SpirePrefixPatch
        public static void patch(@ByRef String[] text) {
            if (!NumberUtils.isParsable(text[0])) {
                text[0] = uWillTakeIt(text[0], true);
            }
        }
    }

    @SpirePatch2(clz= BitmapFont.class, method = "draw", paramtypez = {Batch.class, CharSequence.class, float.class, float.class})
    public static class UwUText3 {
        @SpirePrefixPatch
        public static void patch(@ByRef String[] str) {
            if (!NumberUtils.isParsable(str[0])) {
                str[0] = uWillTakeIt(str[0], false);
            }
        }
    }

    public static String uWillTakeIt(String s, boolean fixin) {
        if (uwuBank.containsKey(s)) {
            s = uwuBank.get(s);
        } else {
            String st = uwuify(s);
            if(fixin) {
                st = fixIt(st);
            }
            uwuBank.put(s, st);
            s = st;
        }

        return s;
    }

    public static String uwuify(String msg) {
        for(Map.Entry<String, String> ex : regexReplacements.entrySet()) {
            msg = msg.replaceAll(ex.getKey(), ex.getValue());
        }

        StringBuilder sb = new StringBuilder();
        for(String word : msg.split(" ")) {
            sb.append(wordReplacements.getOrDefault(word, word)).append(" ");
        }

        return sb.toString();
    }

    public static String fixIt(String msg) {
        if (MathUtils.randomBoolean(0.2f)) {
            msg = prefixes.get(MathUtils.random(prefixes.size() - 1)) + msg;
        }

        if (MathUtils.randomBoolean(0.2f)) {
            msg = msg + postfixes.get(MathUtils.random(postfixes.size() - 1));
        }

        return msg;
    }
}
