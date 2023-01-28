package de.markushimmel.merlinhunt.buggyrelay.generator.util;

import java.util.random.RandomGenerator;

import javax.enterprise.context.ApplicationScoped;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

@ApplicationScoped
public class EmojiProvider {

    private static final Emoji[] emojis = EmojiManager.getAll().toArray(Emoji[]::new);

    public String getRandomEmoji(RandomGenerator random) {
        return emojis[random.nextInt(emojis.length)].getUnicode();
    }

}
