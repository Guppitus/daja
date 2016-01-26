package edu.vtc.daja;

import edu.vtc.daja.lev0.DajaLexer;
import edu.vtc.daja.lev0.DajaParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 * The main class of the Daja compiler.
 */
public class Main {

    /**
     * Specifies the different modes of operation that are supported.
     *
     * CHECK    : Syntax and semantic check only, no code generation or execution.
     * INTERPRET: Execute the program without generating code for it.
     * C        : Generate a Standard C program as output.
     * LLVM     : Generate LLVM assembly language as output.
     * JVM      : Generate JVM assembly language as output.
     */
    private enum Mode {
        CHECK,
        INTERPRET,
        C,
        LLVM,
        JVM
    }

    /**
     * Process input files in the Daja level 0 language. This method runs the compiler pipeline
     * assuming a level 0 compilation has been requested.
     *
     * @param input The input file to compile.
     * @param mode The desired target or mode of operation.
     */
    private static void processLevel0(ANTLRFileStream input, Mode mode) {
        // Parse the input file as Daja0.
        DajaLexer lexer  = new DajaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DajaParser parser = new DajaParser(tokens);
        DajaParser.ModuleContext tree = parser.module();

        // Now do a semantic analysis of the parse tree followed by code generation...
    }

    /**
     * The main program of the Daja educational compiler. This program accepts a file to compile
     * on the command line, along with some options about language level and desired target, and
     * produces error messages or a compiled output file. Daja programs can also be interpreted in
     * some cases.
     *
     * @param args The command line arguments.
     * @throws java.io.IOException If an I/O error occurs during File I/O.
     */
    public static void main(String[] args)
            throws java.io.IOException
    {
        System.out.println("Daja D Compiler (C) 2016 by Vermont Technical College");

        // Analyze the command line.
        if (args.length != 3) {
            System.out.println(
                    "Usage: java -jar Daja (0 | 1 | 2) (-k | -i | -c | -l | -j) source-file");
            System.exit(1);
        }

        int level = Integer.parseInt(args[0]);
        Mode mode;
        switch (args[1]) {
            case "-k":
                mode = Mode.CHECK;
                break;
            case "-i":
                mode = Mode.INTERPRET;
                break;
            case "-c":
                mode = Mode.C;
                break;
            case "-l":
                mode = Mode.LLVM;
                break;
            case "-j":
                mode = Mode.JVM;
                break;
            default:
                System.out.println("Error: Unknown mode, defaulting to CHECK!\n");
                mode = Mode.CHECK;
                break;
        }

        // Create a stream that reads from the specified file.
        ANTLRFileStream input = new ANTLRFileStream(args[2]);
        switch (level) {
            case 0:
                processLevel0(input, mode);
                break;
            default:
                System.out.println("Level not supported!\n");
                break;
        }
    }
}