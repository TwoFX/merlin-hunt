package de.markushimmel.merlinhunt.greatundoing.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import de.markushimmel.merlinhunt.greatundoing.arithmetic.IAdditiveGroup;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IField;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.IVectorSpace;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.ModularArithmetic;
import de.markushimmel.merlinhunt.greatundoing.arithmetic.Vector;
import de.markushimmel.merlinhunt.greatundoing.transformations.IStuffDoer;
import de.markushimmel.merlinhunt.greatundoing.transformations.RandomStuffDoer;
import de.markushimmel.merlinhunt.greatundoing.transformations.RepeatedStuffDoer;
import de.markushimmel.merlinhunt.greatundoing.transformations.StaircaseStuffDoer;
import de.markushimmel.merlinhunt.greatundoing.transformations.StuffDoerRoster;
import de.markushimmel.merlinhunt.greatundoing.util.UndoingConstants;
import io.quarkus.logging.Log;

@ApplicationScoped
public class UndoingRestServiceBean {

    private static final long ANSWER_SEED = 45;

    public boolean checkNumbers(List<Long> numbers) {
        List<ModularArithmetic> vector = numbers.stream() //
                .map(ModularArithmetic::of) //
                .toList();

        List<ModularArithmetic> output = transform(vector, ModularArithmetic.of(0));
        List<ModularArithmetic> correctAnswer = generateCorrectAnswer();
        Log.infof("Recieved attempt %s, resulting numbers %s, correct answer is %s", numbers, output, correctAnswer);
        return correctAnswer.equals(output);
    }

    public String solve() {
        List<ModularArithmetic> correctAnswer = generateCorrectAnswer();
        List<Vector<ModularArithmetic, ModularArithmetic>> input = identityMatrix();
        List<Vector<ModularArithmetic, ModularArithmetic>> matrix = transform(input, ModularArithmetic.of(0));
        List<ModularArithmetic> rightHandSide = new ArrayList<>(correctAnswer);
        solve(matrix, rightHandSide);
        return rightHandSide.toString();
    }

    private void solve(
            List<Vector<ModularArithmetic, ModularArithmetic>> matrix, List<ModularArithmetic> rightHandSide) {
        for (int j = 0; j < UndoingConstants.SIZE; j++) {
            int i = findPivot(matrix, j);
            ModularArithmetic factor = matrix.get(i).get(j).multiplicativeInverse();
            scaleRow(matrix, i, factor);
            scaleRow(rightHandSide, i, factor);

            for (int ii = 0; ii < UndoingConstants.SIZE; ii++) {
                if (i == ii) {
                    continue;
                }
                ModularArithmetic innerFactor = matrix.get(ii).get(j).additiveInverse();
                rowOperation(matrix, i, ii, innerFactor);
                rowOperation(rightHandSide, i, ii, innerFactor);
            }

            swapRows(matrix, i, j);
            swapRows(rightHandSide, i, j);
        }
    }

    private int findPivot(List<Vector<ModularArithmetic, ModularArithmetic>> matrix, int column) {
        for (int i = column; i < UndoingConstants.SIZE; i++) {
            if (!matrix.get(i).get(column).equals(ModularArithmetic.of(0))) {
                return i;
            }
        }
        throw new IllegalStateException("Unable to find pivot");
    }

    private <TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>> void swapRows(
            List<T> matrix, int i, int j) {
        Collections.swap(matrix, i, j);
    }

    private <TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>> void scaleRow(
            List<T> matrix, int row,
            TScalar factor) {
        matrix.set(row, matrix.get(row).scalarMultiply(factor));
    }

    private <TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>> void rowOperation(
            List<T> matrix, int from, int to, TScalar factor) {
        matrix.set(to, matrix.get(to).add(matrix.get(from).scalarMultiply(factor)));
    }

    private List<Vector<ModularArithmetic, ModularArithmetic>> identityMatrix() {
        List<Vector<ModularArithmetic, ModularArithmetic>> result = new ArrayList<>(UndoingConstants.SIZE);
        for (int i = 0; i < UndoingConstants.SIZE; i++) {
            result.add(iota(i));
        }
        return result;
    }

    private Vector<ModularArithmetic, ModularArithmetic> iota(int index) {
        List<ModularArithmetic> values = new ArrayList<>(UndoingConstants.SIZE);
        for (int i = 0; i < UndoingConstants.SIZE; i++) {
            values.add(i == index ? ModularArithmetic.of(1) : ModularArithmetic.of(0));
        }
        return new Vector<>(values);
    }

    private List<ModularArithmetic> generateCorrectAnswer() {
        Random random = new Random(ANSWER_SEED);
        List<ModularArithmetic> result = new ArrayList<>(UndoingConstants.SIZE);

        for (int i = 0; i < UndoingConstants.SIZE; i++) {
            result.add(ModularArithmetic.random(random));
        }
        return result;
    }

    private <TScalar extends IAdditiveGroup<TScalar> & IField<TScalar>, T extends IAdditiveGroup<T> & IVectorSpace<TScalar, T>> List<T> transform(
            List<T> input, TScalar anyScalar) {
        IStuffDoer<TScalar, T> stuffdoer = new StuffDoerRoster<>( //
                new RandomStuffDoer<>(12), //
                new RepeatedStuffDoer<TScalar, T>(140, new RandomStuffDoer<>(5567)), //
                new StaircaseStuffDoer<>(),
                new RepeatedStuffDoer<TScalar, T>(31, new StuffDoerRoster<>( //
                        new RandomStuffDoer<TScalar, T>(31), //
                        new StaircaseStuffDoer<>(), //
                        new RepeatedStuffDoer<TScalar, T>(3,
                                new RandomStuffDoer<TScalar, T>(11)))));
        return stuffdoer.doLinearStuff(input, anyScalar);
    }

}
