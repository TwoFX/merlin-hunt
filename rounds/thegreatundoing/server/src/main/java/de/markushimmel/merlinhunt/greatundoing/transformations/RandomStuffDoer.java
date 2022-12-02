package de.markushimmel.merlinhunt.greatundoing.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.markushimmel.merlinhunt.greatundoing.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IField;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IVectorSpace;

public class RandomStuffDoer<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>>
        implements IStuffDoer<TScalar, T> {

    private final long seed;

    public RandomStuffDoer(long seed) {
        this.seed = seed;
    }

    @Override
    public List<T> doLinearStuff(List<T> input, TScalar anyScalar) {
        Random rng = new Random(seed);
        int n = input.size();
        List<T> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            T entry = input.get(0).zero();
            for (int j = 0; j < n; j++) {
                entry = entry.add(input.get(j).scalarMultiply(anyScalar.ofLong(rng.nextLong())));
            }
            result.add(entry);
        }

        return result;
    }

}
