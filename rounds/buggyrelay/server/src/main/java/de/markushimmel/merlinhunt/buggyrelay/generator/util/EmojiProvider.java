package de.markushimmel.merlinhunt.buggyrelay.generator.util;

import java.util.random.RandomGenerator;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmojiProvider {

    private static final String[] EMOJIS = { "😺", "😸", "😹", "😻", "😼", "😽", "🙀", "😿", "😾", "🙈", "🙉", "🙊",
            "🐵", "🐒", "🦍", "🐶", "🐕", "🐩", "🐺", "🦊", "🦝", "🐱", "🐈", "🦁", "🐯", "🐅", "🐆", "🐴", "🐎", "🦄",
            "🦓", "🦌", "🐮", "🐂", "🐃", "🐄", "🐷", "🐖", "🐗", "🐽", "🐏", "🐑", "🐐", "🐪", "🐫", "🦙", "🦒", "🐘",
            "🦏", "🦛", "🐭", "🐁", "🐀", "🐹", "🐰", "🐇", "🐿", "🦔", "🦇", "🐻", "🐨", "🐼", "🦘", "🦡", "🐾", "🦃",
            "🐔", "🐓", "🐣", "�", "�🐥", "🐦", "🐧", "🕊", "🦅", "🦆", "🦢", "🦉", "🦜", "🐸", "🐊", "🐢", "🦎", "🐍",
            "🐲", "🐉", "🦕", "🦖", "🐳", "🐋", "🐬", "🐟", "🐠", "🐡", "🦈", "🐙", "🦀", "🦞", "🦐", "🦑", "🐌", "🦋",
            "🐛", "🐜", "🐝", "🐞", "🦗", "🕷", "🕸", "🦂", "🦟", "🦠" };

    public String getRandomEmoji(RandomGenerator random) {
        return EMOJIS[random.nextInt(EMOJIS.length)];
    }

}
