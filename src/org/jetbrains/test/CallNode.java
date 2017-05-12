package org.jetbrains.test;

/**
 * Created by Alex on 12.05.2017.
 */
import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class CallNode implements Serializable {
    private String arg;
    private String name;
    private List<CallNode> children = new LinkedList<>();

    public CallNode(String name, String arg) {
        this.arg = arg;
        this.name = name;
    }

    public void addMethodCall(CallNode methodNode) {
        children.add(methodNode);
    }

    public void printCallTree() {
        printCallTree(0);
    }

    private void printCallTree(int depth) {
        IntStream.range(0, depth).forEach(i -> System.out.print("\t|"));
        System.out.print("[" + depth + "]" + name + "(" + arg + ")\n");
        children.forEach(node -> {
            node.printCallTree(depth + 1);
        });
    }

    void saveAsJson(String filepath) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filepath))) {
            Gson gson = new Gson();
            String json = gson.toJson(this);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static CallNode readFromJson(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            Gson gson = new Gson();
            return gson.fromJson(reader.readLine(), CallNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
