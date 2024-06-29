import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrainfuckInterpreter {
    private String code;
    private int pointer = 0;
    private int codePointer = 0;
    private int[] stack = new int[30000];
    private final Map<Character, Runnable> operationMap = new HashMap<>();
    private ArrayList<Integer> loopStack = new ArrayList<>();

    public BrainfuckInterpreter() {
        operationMap.put('+', () -> changeStack(1));
        operationMap.put('-', () -> changeStack(-1));
        operationMap.put('>', () -> changePos(1));
        operationMap.put('<', () -> changePos(-1));
        operationMap.put('.', () -> System.out.print((char) stack[pointer]));
        operationMap.put('[', this::startLoop);
        operationMap.put(']', this::endLoop);
    }

    public void interpret(String s) {
        code = s;
        init();
        while (codePointer < code.length()) {
            System.out.println(code.charAt(codePointer) + " " + pointer + " " + codePointer + " " + stack[pointer]);
            execute();
            codePointer++;
        }
    }

    private void init() {
        pointer = 0;
        codePointer = 0;
        stack = new int[30000];
    }

    private void changeStack(int delta) {
        stack[pointer] += delta;
    }

    private void changePos(int delta) {
        pointer += delta;
    }

    private void startLoop() {
        if (stack[pointer] != 0) {
            loopStack.add(codePointer);
        } else {
            skipLoop();
        }
    }

    private void skipLoop() {
        while (code.charAt(codePointer) != ']') {
            codePointer++;
        }
    }

    private void endLoop() {
        if (stack[pointer] != 0) {
            codePointer = loopStack.get(loopStack.size() - 1);
        } else {
            loopStack.remove(loopStack.size() - 1);
        }
    }

    private void execute() {
        operationMap.getOrDefault(code.charAt(codePointer), () -> changePos(0)).run();
    }
}
