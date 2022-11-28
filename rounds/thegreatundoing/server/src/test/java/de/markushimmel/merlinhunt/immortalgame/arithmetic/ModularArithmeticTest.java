package de.markushimmel.merlinhunt.immortalgame.arithmetic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ModularArithmeticTest {

    @Disabled
    @Test
    void multiplictiveInverse_SHOULD_work() {
        assertEquals(ModularArithmetic.of(1), ModularArithmetic.of(1).multiplicativeInverse());
        assertEquals(ModularArithmetic.of(500000004), ModularArithmetic.of(2).multiplicativeInverse());
        assertEquals(ModularArithmetic.of(333333336), ModularArithmetic.of(3).multiplicativeInverse());
        assertEquals(ModularArithmetic.of(498525920), ModularArithmetic.of(43532134).multiplicativeInverse());
    }
}
