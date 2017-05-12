package org.jetbrains.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Nikolay.Tropin
 * 18-Apr-17
 */
public class DummyApplication {
    private final List<String> args;
    private Random random = new Random(System.nanoTime());
    private LinkedList<CallNode> callStack = new LinkedList<>();
    private CallNode earliestCall = null;
    public CallNode getEarliestCall() {
        return earliestCall;
    }

    public DummyApplication(List<String> args) {
        this.args = args;
    }

    private boolean nextBoolean() {
        return random.nextBoolean();
    }

    private boolean stop() {
        return random.nextDouble() < 0.15;
    }

    private String nextArg() {
        int idx = random.nextInt(args.size());
        return args.get(idx);
    }

    private void sleep() {
        try {
            Thread.sleep(random.nextInt(20));
        } catch (InterruptedException ignored) {

        }
    }

    private void abc(String s) {
        //your code here
        updateCallTree("abc", s);
        sleep();
        if (stop()) {
            //do nothing
        }
        else if (nextBoolean()) {
            def(nextArg());
            xyz(nextArg());//added this call so that the tree won't be linear.
        }
        else {
            xyz(nextArg());
        }
        earliestCall = callStack.pop();
    }

    private void updateCallTree(String name, String arg) {
        CallNode currentCall = new CallNode(name, arg);
        CallNode parentCall = callStack.peek();
        if (parentCall != null) {
            parentCall.addMethodCall(currentCall);
        }
        callStack.push(currentCall);
    }

    private void def(String s) {
        updateCallTree("def", s);

        sleep();
        if (stop()) {
            //do nothing
        }
        else if (nextBoolean()) {
            abc(nextArg());
        }
        else {
            xyz(nextArg());
        }
        earliestCall = callStack.pop();
    }

    private void xyz(String s) {
        updateCallTree("xyz", s);

        sleep();
        if (stop()) {
            //do nothing
        }
        else if (nextBoolean()) {
            abc(nextArg());
        }
        else {
            def(nextArg());
        }
        earliestCall = callStack.pop();
    }

    public void start() {
        abc(nextArg());
    }
}
