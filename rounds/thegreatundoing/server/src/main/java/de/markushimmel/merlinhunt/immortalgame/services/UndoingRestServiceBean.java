package de.markushimmel.merlinhunt.immortalgame.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import de.markushimmel.merlinhunt.immortalgame.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IField;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IVectorSpace;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.ModularArithmetic;
import de.markushimmel.merlinhunt.immortalgame.transformations.IStuffDoer;
import de.markushimmel.merlinhunt.immortalgame.transformations.RandomStuffDoer;
import de.markushimmel.merlinhunt.immortalgame.transformations.RepeatedStuffDoer;
import de.markushimmel.merlinhunt.immortalgame.transformations.StaircaseStuffDoer;
import de.markushimmel.merlinhunt.immortalgame.transformations.StuffDoerRoster;
import de.markushimmel.merlinhunt.immortalgame.util.UndoingConstants;
import io.quarkus.logging.Log;

@ApplicationScoped
public class UndoingRestServiceBean {

    private static final long ANSWER_SEED = 45;

    public boolean checkNumbers(List<Long> numbers) {
        List<ModularArithmetic> vector = numbers.stream() //
                .map(ModularArithmetic::of) //
                .toList();

        List<ModularArithmetic> output = transform(vector, ModularArithmetic.of(0));
        List<ModularArithmetic> correctAnswer = generateCorrectAnswer();
        Log.infof("Recieved attempt %s, resulting numbers %s, correct answer is %s", numbers, output, correctAnswer);
        return correctAnswer.equals(output);
    }

    private List<ModularArithmetic> generateCorrectAnswer() {
        Random random = new Random(ANSWER_SEED);
        List<ModularArithmetic> result = new ArrayList<>(UndoingConstants.SIZE);

        for (int i = 0; i < UndoingConstants.SIZE; i++) {
            result.add(ModularArithmetic.random(random));
        }
        return result;
    }

    private <TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>> List<T> transform(
            List<T> input, TScalar anyScalar) {
        IStuffDoer<TScalar, T> stuffdoer = new StuffDoerRoster<>( //
                new RandomStuffDoer<>(12), //
                new RepeatedStuffDoer<TScalar, T>(140, new RandomStuffDoer<>(5567)), //
                new StaircaseStuffDoer<>(),
                new RepeatedStuffDoer<TScalar, T>(31, new StuffDoerRoster<>( //
                        new RandomStuffDoer<TScalar, T>(31), //
                        new StaircaseStuffDoer<>(), //
                        new RepeatedStuffDoer<TScalar, T>(3,
                                new RandomStuffDoer<TScalar, T>(11)))));
        return stuffdoer.doLinearStuff(input, anyScalar);
    }

}
