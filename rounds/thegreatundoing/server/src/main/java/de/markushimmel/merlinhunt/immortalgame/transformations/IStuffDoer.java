package de.markushimmel.merlinhunt.immortalgame.transformations;

import java.util.List;

import de.markushimmel.merlinhunt.immortalgame.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IField;
import de.markushimmel.merlinhunt.immortalgame.arithmetic.IVectorSpace;

public interface IStuffDoer<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>> {
    List<T> doLinearStuff(List<T> input);
}
