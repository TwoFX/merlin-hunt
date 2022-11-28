package de.markushimmel.merlinhunt.immortalgame.transformations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.markushimmel.merlinhunt.immortalgame.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IField;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IVectorSpace;

public class ShufflingStuffDoer<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>>
        implements IStuffDoer<TScalar, T> {

    private final long seed;

    public ShufflingStuffDoer(long seed) {
        this.seed = seed;
    }

    @Override
    public List<T> doLinearStuff(List<T> input, TScalar anyScalar) {
        List<T> result = new ArrayList<>(input);
        Collections.shuffle(result, new Random(seed));
        return result;
    }

}
