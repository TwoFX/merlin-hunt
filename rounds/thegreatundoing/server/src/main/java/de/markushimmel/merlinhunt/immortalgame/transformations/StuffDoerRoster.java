package de.markushimmel.merlinhunt.immortalgame.transformations;

import java.util.List;

import de.markushimmel.merlinhunt.immortalgame.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IField;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IVectorSpace;

public class StuffDoerRoster<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>>
        implements IStuffDoer<TScalar, T> {

    private final List<IStuffDoer<TScalar, T>> transformations;

    public StuffDoerRoster(List<IStuffDoer<TScalar, T>> transformations) {
        this.transformations = transformations;
    }

    @Override
    public List<T> doLinearStuff(List<T> input) {
        List<T> result = input;
        for (IStuffDoer<TScalar, T> transformation : transformations) {
            result = transformation.doLinearStuff(result);
        }
        return result;
    }
}
