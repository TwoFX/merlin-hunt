package de.markushimmel.merlinhunt.immortalgame.transformations;

import java.util.List;

import de.markushimmel.merlinhunt.immortalgame.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IField;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IVectorSpace;

public class RepeatedStuffDoer<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>>
        implements IStuffDoer<TScalar, T> {

    private final IStuffDoer<TScalar, T> inner;
    private final int repetitions;

    public RepeatedStuffDoer(IStuffDoer<TScalar, T> inner, int repetitions) {
        this.inner = inner;
        this.repetitions = repetitions;
    }

    @Override
    public List<T> doLinearStuff(List<T> input) {
        List<T> result = input;
        for (int i = 0; i < repetitions; i++) {
            result = inner.doLinearStuff(result);
        }
        return result;
    }

}
