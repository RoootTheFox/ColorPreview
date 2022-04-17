package net.foxes4life.colorpreview;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern hexCode = Pattern.compile("&#([0-9a-f]){6}");
    private static final Pattern colorCode = Pattern.compile("&([0-9a-fklmnor])");

    public static Text parse(String input) {
        Matcher matcher = hexCode.matcher(input);
        ArrayList<Text> texts = new ArrayList<>();
        String[] split = input.split("&#([0-9a-f]){6}");

        try {
            for (int i = 0; i < split.length; i++) {
                if(matcher.find()) {
                    if(!(i+1 >= split.length)) {
                        String s = matcher.group().substring(2);
                        texts.add(new LiteralText(replaceColorCodes(split[i+1]).replace("§r", "§r§f")).setStyle(Style.EMPTY.withColor(Integer.parseInt(s, 16))));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LiteralText res = new LiteralText(replaceColorCodes(split.length > 0 ? split[0] : "").replace("§r", "§r§f"));

        for (Text text : texts) {
            res.append(text);
        }

        return res;
    }

    public static String replaceColorCodes(String input) {
        Matcher matcher = colorCode.matcher(input);
        while(matcher.find()) {
            input = input.replace(matcher.group(), matcher.group().replace("&", "§"));
        }

        return input;
    }
}
