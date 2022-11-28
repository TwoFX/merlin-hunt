package de.markushimmel.merlinhunt.immortalgame.arithmetic;

import java.util.ArrayList;
import java.util.List;

import de.markushimmel.merlinhunt.immortalgame.util.UndoingConstants;

public class Vector<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>>
        implements IAdditiveGroup<Vector<TScalar, T>>, IVectorSpace<TScalar, Vector<TScalar, T>> {
    private final List<T> values;

    public Vector(List<T> values) {
        if (values == null || values.size() != UndoingConstants.SIZE) {
            throw new IllegalArgumentException(
                    String.format("values must contain exactly %d values.", UndoingConstants.SIZE));
        }

        this.values = values;
    }

    @Override
    public Vector<TScalar, T> scalarMultiply(TScalar scalar) {
        return new Vector<>(values.stream().map(x -> x.scalarMultiply(scalar)).toList());
    }

    @Override
    public Vector<TScalar, T> zero() {
        List<T> result = new ArrayList<>(UndoingConstants.SIZE);
        for (int i = 0; i < UndoingConstants.SIZE; i++) {
            result.add(values.get(0).zero());
        }
        return new Vector<>(result);
    }

    @Override
    public Vector<TScalar, T> add(Vector<TScalar, T> other) {
        List<T> result = new ArrayList<>(UndoingConstants.SIZE);
        for (int i = 0; i < UndoingConstants.SIZE; i++) {
            result.add(values.get(i).add(other.values.get(i)));
        }
        return new Vector<>(result);
    }

    @Override
    public Vector<TScalar, T> additiveInverse() {
        return new Vector<>(values.stream().map(IAdditiveGroup::additiveInverse).toList());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((values == null) ? 0 : values.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Vector<TScalar, T> other = (Vector<TScalar, T>) obj;
        if (values == null) {
            if (other.values != null)
                return false;
        } else if (!values.equals(other.values))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return values + "v";
    }

}
