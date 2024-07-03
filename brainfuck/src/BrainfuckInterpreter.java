import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrainfuckInterpreter {
    private String inputCode;
    private int stackPointer = 0;
    private int codePointer = 0;
    private int[] stack = new int[30000];
    private final Map<Character, Runnable> operationMap = new HashMap<>();
    private final ArrayList<Integer> loopStack = new ArrayList<>();

    public BrainfuckInterpreter() {
        operationMap.put('+', () -> changeStack(1));
        operationMap.put('-', () -> changeStack(-1));
        operationMap.put('>', () -> changePos(1));
        operationMap.put('<', () -> changePos(-1));
        operationMap.put('.', () -> System.out.print((char) stack[stackPointer]));
        operationMap.put('[', this::startLoop);
        operationMap.put(']', this::endLoop);
    }

    public void interpret(String s) {
        inputCode = s;
        init();
        while (codePointer < inputCode.length()) {
            execute();
            codePointer++;
        }
    }

    private void init() {
        stackPointer = 0;
        codePointer = 0;
        stack = new int[30000];
    }

    private void changeStack(int delta) {
        stack[stackPointer] += delta;
    }

    private void changePos(int delta) {
        stackPointer += delta;
    }

    private void startLoop() {
        if (stack[stackPointer] != 0) {
            loopStack.add(codePointer);
        } else {
            skipLoop();
        }
    }

    private void skipLoop() {
        while (inputCode.charAt(codePointer) != ']') {
            codePointer++;
        }
    }

    private void endLoop() {
        if (stack[stackPointer] != 0) {
            codePointer = loopStack.get(loopStack.size() - 1);
        } else {
            loopStack.remove(loopStack.size() - 1);
        }
    }

    private void execute() {
        operationMap.getOrDefault(inputCode.charAt(codePointer), () -> changePos(0)).run();
    }
}
