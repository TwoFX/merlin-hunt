package de.markushimmel.merlinhunt.immortalgame.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.markushimmel.merlinhunt.immortalgame.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IVectorSpace;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.ModularArithmetic;
import de.markushimmel.merlinhunt.immortalgame.transformations.IStuffDoer;
import de.markushimmel.merlinhunt.immortalgame.transformations.RandomStuffDoer;
import de.markushimmel.merlinhunt.immortalgame.transformations.StuffDoerRoster;
import io.quarkus.logging.Log;

@ApplicationScoped
public class UndoingRestServiceBean {

    private static final List<ModularArithmetic> CORRECT_ANSWER = List.of(1l, 2l, 3l, 4l, 5l) //
            .stream() //
            .map(ModularArithmetic::of) //
            .toList();

    public boolean checkNumbers(List<Long> numbers) {
        List<ModularArithmetic> vector = numbers.stream() //
                .map(ModularArithmetic::of) //
                .toList();

        List<ModularArithmetic> output = transform(vector);
        Log.infof("Recieved attempt %s, resulting numbers %s", numbers, output);
        return CORRECT_ANSWER.equals(output);
    }

    private <T extends IAdditiveGroup<T> & IVectorSpace<ModularArithmetic, T>> List<T> transform(List<T> input) {
        IStuffDoer<ModularArithmetic, T> stuffdoer = new StuffDoerRoster<>(List.of(new RandomStuffDoer<T>(12)));
        return stuffdoer.doLinearStuff(input);
    }

}
