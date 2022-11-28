package de.markushimmel.merlinhunt.immortalgame.arithmetic;

import java.util.List;

public class Vector<T extends IAdditiveGroup<T> & IField<T>>
        implements IAdditiveGroup<Vector<T>>, IVectorSpace<T, Vector<T>> {
    private static final int SIZE = 5;

    private List<T> values;
}
