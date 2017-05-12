package org.jetbrains.test;

import java.io.*;

/**
 * Created by Alex on 12.05.2017.
 */
public class ReadCallTreeFromFile {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            CallNode.readFromJson("callTree_" + i++ + ".json").printCallTree();
        }
    }
}
