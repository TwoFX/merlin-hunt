package de.markushimmel.merlinhunt.buggyrelay.generator.languages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BrainfuckProgramGeneratorTest {

    private BrainfuckProgramGenerator underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new BrainfuckProgramGenerator();
    }

    @ParameterizedTest
    @ValueSource(strings = { "aaaaa",
            "a\n",
            "The quick brown fox jumped over the lazy dog. Hurray! {}{}\n\n\n\n\\\\ I like $Salasdjfe*SDFedfeSjSJWEJs{hello}" })
    void testGenerateProgram(String outputToGenerate) {
        String program = underTest.generateProgram(outputToGenerate);
        String result = executeProgram(program);
        assertThat(result, is(outputToGenerate));
    }

    private String executeProgram(String program) {
        Map<Integer, Integer> matching = determineMatchingBrackets(program);
        ArrayList<Integer> buffer = new ArrayList<>();
        buffer.add(0);
        int position = 0;
        int instructionPointer = 0;
        StringBuilder result = new StringBuilder();

        while (instructionPointer < program.length()) {
            char instruction = program.charAt(instructionPointer);

            switch (instruction) {
                case '+':
                    buffer.set(position, buffer.get(position) + 1);
                    break;

                case '-':
                    buffer.set(position, buffer.get(position) - 1);
                    break;

                case '<':
                    position--;
                    assertThat(position, is(greaterThanOrEqualTo(0)));
                    break;

                case '>':
                    position++;
                    while (position >= buffer.size()) {
                        buffer.add(0);
                    }
                    break;

                case '[':
                    if (buffer.get(position) == 0) {
                        instructionPointer = matching.get(instructionPointer);
                    }
                    break;

                case ']':
                    instructionPointer = matching.get(instructionPointer) - 1;
                    break;

                case '.':
                    result.append((char) (int) buffer.get(position));
                    break;

                default:
                    fail("Unrecognized instruction");
                    break;
            }

            instructionPointer++;
        }

        return result.toString();
    }

    private Map<Integer, Integer> determineMatchingBrackets(String program) {
        Deque<Integer> stack = new ArrayDeque<>();
        Map<Integer, Integer> result = new HashMap<>();

        for (int i = 0; i < program.length(); i++) {
            char instruction = program.charAt(i);
            if (instruction == '[') {
                stack.push(i);
            } else if (instruction == ']') {
                assertThat(stack, is(not(empty())));
                int matchingPosition = stack.pop();
                result.put(i, matchingPosition);
                result.put(matchingPosition, i);
            }
        }

        assertThat(stack, is(empty()));
        return result;
    }
}
