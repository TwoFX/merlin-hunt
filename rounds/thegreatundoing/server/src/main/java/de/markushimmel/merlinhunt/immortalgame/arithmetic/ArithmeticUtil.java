package de.markushimmel.merlinhunt.immortalgame.arithmetic;

public interface ArithmeticUtil {

    static long powMod(long base, long exponent, long modulus) {
        base = ((base % modulus) + modulus) % modulus;
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (base * result) % modulus;
            }
            base = (base * base) % modulus;
            exponent /= 2;
        }
        return result;
    }

}
