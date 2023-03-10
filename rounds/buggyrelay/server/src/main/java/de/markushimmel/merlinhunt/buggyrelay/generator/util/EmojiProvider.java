package de.markushimmel.merlinhunt.buggyrelay.generator.util;

import java.util.random.RandomGenerator;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmojiProvider {

    private static final String[] EMOJIS = { "ðš", "ðļ", "ðđ", "ðŧ", "ðž", "ð―", "ð", "ðŋ", "ðū", "ð", "ð", "ð",
            "ðĩ", "ð", "ðĶ", "ðķ", "ð", "ðĐ", "ðš", "ðĶ", "ðĶ", "ðą", "ð", "ðĶ", "ðŊ", "ð", "ð", "ðī", "ð", "ðĶ",
            "ðĶ", "ðĶ", "ðŪ", "ð", "ð", "ð", "ð·", "ð", "ð", "ð―", "ð", "ð", "ð", "ðŠ", "ðŦ", "ðĶ", "ðĶ", "ð",
            "ðĶ", "ðĶ", "ð­", "ð", "ð", "ðđ", "ð°", "ð", "ðŋ", "ðĶ", "ðĶ", "ðŧ", "ðĻ", "ðž", "ðĶ", "ðĶĄ", "ðū", "ðĶ",
            "ð", "ð", "ðĢ", "ïŋ―", "ïŋ―ðĨ", "ðĶ", "ð§", "ð", "ðĶ", "ðĶ", "ðĶĒ", "ðĶ", "ðĶ", "ðļ", "ð", "ðĒ", "ðĶ", "ð",
            "ðē", "ð", "ðĶ", "ðĶ", "ðģ", "ð", "ðŽ", "ð", "ð ", "ðĄ", "ðĶ", "ð", "ðĶ", "ðĶ", "ðĶ", "ðĶ", "ð", "ðĶ",
            "ð", "ð", "ð", "ð", "ðĶ", "ð·", "ðļ", "ðĶ", "ðĶ", "ðĶ " };

    public String getRandomEmoji(RandomGenerator random) {
        return EMOJIS[random.nextInt(EMOJIS.length)];
    }

}
