package de.markushimmel.merlinhunt.greatundoing.arithmetic;

public interface IAdditiveGroup<T> {

    T zero();

    T add(T other);

    T additiveInverse();

}
