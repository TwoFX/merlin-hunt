package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import de.markushimmel.merlinhunt.buggyrelay.generator.IProgramGenerator;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

public class HaskellRSAProgramGenerator implements IProgramGenerator {

    private static final BigInteger P = new BigInteger("316912650057057350374175801279");
    private static final BigInteger Q = new BigInteger("316912650057057350374175801293");
    private static final BigInteger N = P.multiply(Q);
    private static final BigInteger E = BigInteger.valueOf((1 << 16) + 1);

    private static final BigInteger OFFSET = BigInteger.valueOf(1 << 8);

    private static final int GROUP_SIZE = 24;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance program(String standardOutputCipher, String standardErrorCipher,
                boolean withSyntaxError);
    }

    @Override
    public String generateProgram(String standardOutput, String standardError, boolean withSyntaxError) {
        return Templates.program(encryptString(standardOutput).toString(), encryptString(standardError).toString(),
                withSyntaxError).render();
    }

    private List<BigInteger> encryptString(String toEncrypt) {
        byte[] bytes = toEncrypt.getBytes(StandardCharsets.UTF_8);

        return Arrays.stream(group(bytes, GROUP_SIZE)) //
                .map(this::combine) //
                .map(this::encryptSingle) //
                .toList();
    }

    private byte[][] group(byte[] input, int groupSize) {
        int groups = (input.length + groupSize - 1) / groupSize;
        byte[][] result = new byte[groups][];

        for (int i = 0; i < groups; i++) {
            int from = i * groupSize;
            int to = Math.min(input.length, (i + 1) * groupSize);
            result[i] = Arrays.copyOfRange(input, from, to);
        }

        return result;
    }

    private BigInteger combine(byte[] bytes) {
        BigInteger result = BigInteger.ZERO;
        for (byte b : bytes) {
            result = result.multiply(OFFSET).add(BigInteger.valueOf(b));
        }
        return result;
    }

    private BigInteger encryptSingle(BigInteger toEncrypt) {
        return toEncrypt.modPow(E, N);
    }

}