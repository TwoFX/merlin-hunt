package de.markushimmel.merlinhunt.immortalgame.arithmetic;

public interface IAdditiveGroup<T> {

    T zero();

    T add(T other);

    T additiveInverse();

}
