package de.markushimmel.merlinhunt.greatundoing.transformations;

import java.util.List;

import de.markushimmel.merlinhunt.greatundoing.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IField;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IVectorSpace;

public class StuffDoerRoster<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>>
        implements IStuffDoer<TScalar, T> {

    private final List<IStuffDoer<TScalar, T>> transformations;

    @SafeVarargs
    public StuffDoerRoster(IStuffDoer<TScalar, T>... transformations) {
        this.transformations = List.of(transformations);
    }

    @Override
    public List<T> doLinearStuff(List<T> input, TScalar anyScalar) {
        List<T> result = input;
        for (IStuffDoer<TScalar, T> transformation : transformations) {
            result = transformation.doLinearStuff(result, anyScalar);
        }
        return result;
    }
}
