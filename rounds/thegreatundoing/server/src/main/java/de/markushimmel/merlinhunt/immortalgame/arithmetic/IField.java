package de.markushimmel.merlinhunt.immortalgame.arithmetic;

/**
 * Man, interfaces really are a terrible replacement for type classes!
 */
public interface IField<T extends IAdditiveGroup<T>> {

    T one();

    default T ofLong(long value) {
        T unit = value >= 0 ? one() : one().additiveInverse();
        T result = unit.zero();
        for (int i = 0; i < Math.abs(value); i++) {
            result = result.add(unit);
        }
        return result;
    }

    T multiply(T other);

    T multiplicateInverse();
}
