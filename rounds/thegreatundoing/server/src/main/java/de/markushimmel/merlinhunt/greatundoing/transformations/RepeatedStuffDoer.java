package de.markushimmel.merlinhunt.greatundoing.transformations;

import java.util.List;

import de.markushimmel.merlinhunt.greatundoing.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IField;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IVectorSpace;

public class RepeatedStuffDoer<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>>
        implements IStuffDoer<TScalar, T> {

    private final IStuffDoer<TScalar, T> inner;
    private final int repetitions;

    public RepeatedStuffDoer(int repetitions, IStuffDoer<TScalar, T> inner) {
        this.inner = inner;
        this.repetitions = repetitions;
    }

    @Override
    public List<T> doLinearStuff(List<T> input, TScalar anyScalar) {
        List<T> result = input;
        for (int i = 0; i < repetitions; i++) {
            result = inner.doLinearStuff(result, anyScalar);
        }
        return result;
    }

}
