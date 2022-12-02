package de.markushimmel.merlinhunt.greatundoing.arithmetic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ArithmeticUtilTest {

    @Test
    void powMod_SHOULD_work() {
        assertEquals(47675, ArithmeticUtil.powMod(17, 34534525422l, 133333));
        assertEquals(93226, ArithmeticUtil.powMod(2343222, 34534525422l, 133333));
        assertEquals(834283130, ArithmeticUtil.powMod(23432229991l, 34343534525422l, 1333443523));
        assertEquals(1059592659, ArithmeticUtil.powMod(-234l, 34343534525422l, 1333443523));
    }

}
