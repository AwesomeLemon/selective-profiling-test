package org.jetbrains.test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        List<Future<CallNode>> threadRootCalls = new LinkedList<>();

        for(int i = 0; i < 5; i++) {
            int start = 100 * i;
            List<String> arguments = IntStream.range(start, start + 10)
                    .mapToObj(Integer :: toString)
                    .collect(Collectors.toList());
            threadRootCalls.add(service.submit(() -> {
                DummyApplication dummyApplication = new DummyApplication(arguments);
                dummyApplication.start();
                return dummyApplication.getEarliestCall();
            }));
        }

        int count = 0;
        for (Future<CallNode> futureNode : threadRootCalls) {
            try {
                CallNode rootCall = futureNode.get(Long.MAX_VALUE, TimeUnit.DAYS);
                //printing to console:
                rootCall.printCallTree();
                System.out.println();

                //saving to file:
                rootCall.saveAsJson("callTree_" + count++ + ".json");
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }
}
