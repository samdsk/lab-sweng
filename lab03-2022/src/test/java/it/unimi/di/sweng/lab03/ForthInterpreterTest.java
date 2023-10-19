package it.unimi.di.sweng.lab03;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


@Timeout(2)
public class ForthInterpreterTest {
    private Interpreter interpreter;

    @BeforeEach
    public void setUp() throws Exception {
        interpreter = new ForthInterpreter();
    }

    @Test
    public void testEmptyProgram() {
        interpreter.input("");
        assertThat(interpreter.toString()).isEqualTo("<- Top");
    }

    @Test
    public void testPositiveIntegersProgram() {
        interpreter.input("1");
        assertThat(interpreter.toString()).isEqualTo("1 <- Top");
        interpreter.input("1 2");
        assertThat(interpreter.toString()).isEqualTo("1 2 <- Top");
    }

    @Test
    public void testSignedIntegersProgram() {
        interpreter.input("1 -2 3");
        assertThat(interpreter.toString()).isEqualTo("1 -2 3 <- Top");
        interpreter.input("1 +2 3");
        assertThat(interpreter.toString()).isEqualTo("1 2 3 <- Top");
    }

    @Test
    public void testSkipMultiSpaceAndNewLinesProgram() {
        interpreter.input("1 2");
        assertThat(interpreter.toString()).isEqualTo("1 2 <- Top");
        interpreter.input("1\n2");
        assertThat(interpreter.toString()).isEqualTo("1 2 <- Top");
        interpreter.input("1   2 \n3");
        assertThat(interpreter.toString()).isEqualTo("1 2 3 <- Top");
    }

    @Test
    public void testPlusProgram(){
        interpreter.input("1 2 +");
        assertThat(interpreter.toString()).isEqualTo("3 <- Top");
        interpreter.input("1 2 + 5 +");
        assertThat(interpreter.toString()).isEqualTo("8 <- Top");
    }

    @Test
    public void testIllegalTokenProgram(){
        assertThatThrownBy(() -> interpreter.input("1 2+"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Token error '2+'");

        assertThatThrownBy(() -> interpreter.input("1 2 ++5 +"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Token error '++5'");
    }

    @Test
    public void testStackUnderflowProgram(){
        assertThatThrownBy(()-> interpreter.input("1 +"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Stack Underflow");
    }

    @Test
    public void testMultiplicationProgram(){
        interpreter.input("1 2 *");
        assertThat(interpreter.toString()).isEqualTo("2 <- Top");
        interpreter.input("1 2 * 5 *");
        assertThat(interpreter.toString()).isEqualTo("10 <- Top");
    }

    @Test
    public void testDivSubProgram(){
        interpreter.input("1 2 -");
        assertThat(interpreter.toString()).isEqualTo("-1 <- Top");

        interpreter.input("1 2 /");
        assertThat(interpreter.toString()).isEqualTo("0 <- Top");
    }

    @Test
    public void testDupCommand(){
        interpreter.input("1 2 3 dup");
        assertThat(interpreter.toString()).isEqualTo("1 2 3 3 <- Top");
    }
    @Test
    public void testSwapCommand(){
        interpreter.input("1 2 3 swap");
        assertThat(interpreter.toString()).isEqualTo("1 3 2 <- Top");
    }

    @Test
    public void testDropCommand(){
        interpreter.input("1 2 3 drop");
        assertThat(interpreter.toString()).isEqualTo("1 2 <- Top");
    }

    @Test
    public void testTestAllCommandsSoFar(){
        interpreter.input("1 2 + 3 * 4 dup 5 + drop swap");
        assertThat(interpreter.toString()).isEqualTo("4 9 <- Top");

        assertThatThrownBy(() -> interpreter.input("1 2 + 3 * drop swap"))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void testCustomCommands(){
        interpreter.input(": raddoppia 2 * ; 5 raddoppia dup raddoppia");
        assertThat(interpreter.toString()).isEqualTo("10 20 <- Top");
    }

    @Test
    void testUndefinedWordException() {
        assertThatThrownBy(()->interpreter.input("pippo"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Undefined word 'pippo'");
        assertThatThrownBy(()->interpreter.input("1 2 pippo"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Undefined word 'pippo'");
        assertThatThrownBy(()->interpreter.input("1 : raddoppia 2 * ; raddoppi raddoppia"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Undefined word 'raddoppi'");
    }
}
