package de.markushimmel.merlinhunt.immortalgame.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.markushimmel.merlinhunt.immortalgame.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IVectorSpace;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.ModularArithmetic;

public class RandomStuffDoer<T extends IAdditiveGroup<T> & IVectorSpace<ModularArithmetic, T>>
        implements IStuffDoer<ModularArithmetic, T> {

    private final long seed;

    public RandomStuffDoer(long seed) {
        this.seed = seed;
    }

    @Override
    public List<T> doLinearStuff(List<T> input) {
        Random rng = new Random(seed);
        int n = input.size();
        List<T> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            T entry = input.get(0).zero();
            for (int j = 0; j < n; j++) {
                entry = entry.add(input.get(j).scalarMultiply(ModularArithmetic.of(rng.nextLong())));
            }
            result.add(entry);
        }

        return result;
    }

}
