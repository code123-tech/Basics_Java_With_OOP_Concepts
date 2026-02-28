package org.example;

/**
 * Text Blocks are Java’s way to write multi-line Strings easily using {@code """}.
 * <p>
 * Introduced:
 * - Preview in Java 13/14
 * - Final (standard) in Java 15 (JEP 378)
 * <p>
 * Problem it solves:
 * - Before text blocks, writing JSON/SQL/HTML or any multi-line text required lots of {@code \n},
 *   escaping quotes, and string concatenation, which made code hard to read and maintain.
 * <p>
 * Syntax structure:
 * <pre>
 * String text = """
 *     line 1
 *     line 2
 *     """;
 * </pre>
 * Notes:
 * - The result is still a normal {@link String}.
 * - Java handles incidental indentation so you can keep your code nicely indented.
 */
public class UnderstandingTextBlocks {

    public static void doExercise(){

        System.out.println("Text Block");
        createBasicSyntaxStructure();
        newLineBehaviour();
        indentationStripping();
        trailingWhitespaceBehaviour();
    }


    private static void createBasicSyntaxStructure(){

        System.out.println("1. Comparing normal string and text block string");
        String name1 = "Pat Q. Smith";
        String name2 = """
                Pat Q. Smith""";

        System.out.printf("name1: %s, name2: %s%n",  name1, name2);
        System.out.printf("result of '%s'.equals('%s') is: %s%n",  name1, name2, name1.equals(name2));
        System.out.printf("result of '%s' == '%s' is: %s%n",  name1, name2, name1 == name2);
    }

    private static void newLineBehaviour(){

        System.out.println("\n2. The 'final Newline' Behaviour");

        String a = """
                red
                green
                blue""";

        String b = """
                red
                green
                blue
                """;

        System.out.printf("String 1 closes in own line %s, length is: %s%n", a, a.length());
        System.out.printf("String 2 closes on last content line %s, length is: %s%n", b, b.length());
    }

    private static void indentationStripping(){

        /*
        Java looks at the text block lines and finds the smallest common indentation (leading spaces/tabs) and removes it from every line.

        but preserve relative indentation of body and p
         */

        System.out.println("\n 3. The 'incidental stripping' behaviour");
        String html = """
                <html>
                    <body>
                        <p>Hello World.</p>
                    </body>
                </html>
                """;
        writeOutput(html);
    }

    private static void writeOutput(String output){
        output.lines().forEach(line -> System.out.println("[" + line + "]"));
    }

    private static void trailingWhitespaceBehaviour() {

        System.out.println("\n4. Trailing whitespace is stripped");

        String s = """
        red   \s
        green\s
        blue  \s
        """;

        // Show exact output with markers
        writeOutputWithBars(s);
    }

    private static void writeOutputWithBars(String output) {
        output.lines().forEach(line -> System.out.println("[" + line + "|]"));
    }

}

/*
Text Blocks — Style Guidelines (Interview Q/A)
Source: Oracle Programmer’s Guide to Text Blocks (G1–G12)
https://docs.oracle.com/en/java/javase/24/text-blocks/index.html

G1) When should you use a text block?
    - Use it when it improves clarity, especially for multiline strings (JSON/SQL/HTML/messages).

G2) When should you prefer a normal "..." string literal?
    - If it fits on one line without ugly concatenation/escaped newlines, a normal literal is simpler.

G3) Should you avoid escape sequences entirely in text blocks?
    - No. Use escapes when they help readability (e.g., showing explicit \n inside a table-like block).

G4) What is the recommended delimiter style?
    - Opening delimiter at end of previous line; closing delimiter on its own line at the left margin of the text block.

G5) Why avoid aligning delimiters with the text (over-indenting everything)?
    - Because changing indentation of surrounding code (variable name/modifiers) forces re-indenting the whole block.

G6) Why avoid in-line text blocks inside complex expressions?
    - It harms readability; prefer assigning the text block to a local variable or static final constant.

G7) Why should you not mix tabs and spaces in a text block?
    - Incidental indentation stripping counts characters, not “visual width”; mixing causes surprising misalignment.

G8) How do you include sequences of three double quotes inside a text block?
    - Escape the first quote of each run: write \""" ... \""".

G9) How should a text block be indented relative to surrounding Java code?
    - Usually align it naturally with the code indentation for readability.

G10) When is fully left-justifying a text block reasonable?
     - When the text is very wide and otherwise causes horizontal scrolling or ugly wrapping.

G11) When is fully left-justifying reasonable due to vertical length?
     - When there are many lines and the closing delimiter would scroll out of view; left margin makes indentation easier to track.

G12) How should you exclude the final newline in a text block (preferred way)?
     - Use the \<line-terminator> escape (backslash at end of line) so the closing delimiter can still manage indentation cleanly.
*/
