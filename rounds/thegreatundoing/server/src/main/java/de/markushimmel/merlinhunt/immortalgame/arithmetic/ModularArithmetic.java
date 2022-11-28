package de.markushimmel.merlinhunt.immortalgame.arithmetic;

public class ModularArithmetic implements IAdditiveGroup<ModularArithmetic>, IField<ModularArithmetic>,
        IVectorSpace<ModularArithmetic, ModularArithmetic> {

    private static final long MODULUS = 1000000007;

    private final long value;

    private ModularArithmetic(long value) {
        this.value = ((value % MODULUS) + MODULUS) % MODULUS;
    }

    public static ModularArithmetic of(long value) {
        return new ModularArithmetic(value);
    }

    @Override
    public ModularArithmetic ofLong(long value) {
        return of(value);
    }

    @Override
    public ModularArithmetic scalarMultiply(ModularArithmetic scalar) {
        return of(scalar.value * value);
    }

    @Override
    public ModularArithmetic one() {
        return of(1);
    }

    @Override
    public ModularArithmetic multiply(ModularArithmetic other) {
        return scalarMultiply(other);
    }

    @Override
    public ModularArithmetic multiplicativeInverse() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ModularArithmetic zero() {
        return of(0);
    }

    @Override
    public ModularArithmetic add(ModularArithmetic other) {
        return of(value + other.value);
    }

    @Override
    public ModularArithmetic additiveInverse() {
        return of(-value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (value ^ (value >>> 32));
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
        ModularArithmetic other = (ModularArithmetic) obj;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return value + "m";
    }

}
