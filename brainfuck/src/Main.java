import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String code = ">+++++++++[<++++++++>-]<.>+++++++[<++++>-]<+.+++++++..+++.[-]\n" +
                ">++++++++[<++++>-] <.>+++++++++++[<++++++++>-]<-.--------.+++\n" +
                ".------.--------.[-]>++++++++[<++++>- ]<+.[-]++++++++++.";
        BrainfuckInterpreter brainfuck = new BrainfuckInterpreter();
        brainfuck.interpret(code);
    }
}