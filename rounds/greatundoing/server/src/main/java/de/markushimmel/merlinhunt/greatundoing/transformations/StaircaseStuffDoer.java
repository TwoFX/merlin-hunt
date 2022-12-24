package de.markushimmel.merlinhunt.greatundoing.transformations;

import java.util.ArrayList;
import java.util.List;

import de.markushimmel.merlinhunt.greatundoing.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IField;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IVectorSpace;
import de.markushimmel.merlinhunt.greatundoing.util.UndoingConstants;

public class StaircaseStuffDoer<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>>
        implements IStuffDoer<TScalar, T> {

    @Override
    public List<T> doLinearStuff(List<T> input, TScalar anyScalar) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < UndoingConstants.SIZE; i++) {
            result.add(input.get(i).add(input.get((i + 1) % UndoingConstants.SIZE)));
        }
        return result;
    }

}
