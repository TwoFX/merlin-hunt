package de.markushimmel.merlinhunt.immortalgame.arithmetic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ModularArithmeticTest {

    @Test
    void multiplictiveInverse_SHOULD_work() {
        assertEquals(1, ModularArithmetic.of(1).multiplicativeInverse());
        assertEquals(500000004, ModularArithmetic.of(2).multiplicativeInverse());
        assertEquals(333333336, ModularArithmetic.of(3).multiplicativeInverse());
        assertEquals(498525920, ModularArithmetic.of(43532134).multiplicativeInverse());
    }
}
