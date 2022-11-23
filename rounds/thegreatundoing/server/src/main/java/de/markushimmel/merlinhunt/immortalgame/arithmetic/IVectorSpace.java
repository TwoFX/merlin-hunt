package de.markushimmel.merlinhunt.immortalgame.arithmetic;

public interface IVectorSpace<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T>> {

    T scalarMultiply(TScalar scalar);
}
