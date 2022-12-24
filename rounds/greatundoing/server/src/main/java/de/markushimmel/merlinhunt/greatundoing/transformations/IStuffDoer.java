package de.markushimmel.merlinhunt.greatundoing.transformations;

import java.util.List;

import de.markushimmel.merlinhunt.greatundoing.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IField;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IVectorSpace;

public interface IStuffDoer<TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>> {
    List<T> doLinearStuff(List<T> input, TScalar anyScalar);
}
