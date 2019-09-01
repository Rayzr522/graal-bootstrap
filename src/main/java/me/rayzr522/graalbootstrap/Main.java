package me.rayzr522.graalbootstrap;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main {
    private static final int ITERATIONS = 10000;
    public static final String JS_TEST_CODE = "new Array(1000).fill(0).reduce((out, _, i) => out.concat(i < 2 ? 1 : out[i - 1]  + out[i - 2]), [])\n";
//    public static final String JS_TEST_CODE = "var arr = new Array(1000); for (var i = 0; i < arr.length; i++) { arr[i] = 0 }; arr.reduce(function (out, _, i) { return out.concat(i < 2 ? 1 : out[i - 1]  + out[i - 2]) }, [])";

    public static void main(String[] args) {
        Context context = Context.create();

        runBenchmark("Context Creation", ITERATIONS, Main::basicTest);

        runBenchmark("Single Context", ITERATIONS, () -> evaluateJs(context));

//        runBenchmark("ScriptEngine Test", ITERATIONS, Main::scriptingEngineTest);

        context.close();
    }

    public static void runBenchmark(String name, int iterations, Runnable test) {
        long total = 0;
        long lowest = Long.MAX_VALUE;
        long lowestIndex = -1;
        long highest = Long.MIN_VALUE;
        long highestIndex = -1;

        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            test.run();
            long end = System.nanoTime();

            long diff = end - start;

            if (diff > highest) {
                highest = diff;
                highestIndex = i;
            }

            if (diff < lowest) {
                lowest = diff;
                lowestIndex = i;
            }

            total += diff;
        }

        System.out.printf("----- %s -----\n", name);
        System.out.printf("%.2fs total, %.2fms avg, %.2fms low (%d), %.2fms high (%d)\n\n", total / 1000000000.0, total / iterations / 1000000.0, lowest / 1000000.0, lowestIndex, highest / 1000000.0, highestIndex);
    }

    private static void basicTest() {
        Context polyglot = Context.create();
        evaluateJs(polyglot);
        polyglot.close();
    }

    private static void evaluateJs(Context context) {
        Value value = context.eval("js", JS_TEST_CODE);
    }


    private static void scriptingEngineTest() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");

        try {
            Object output = engine.eval(JS_TEST_CODE);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
