
package benchmark;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@Fork(3)
@State(Scope.Thread)
public class Harness {

    @Param({"sample.java"})
    public String inputPath;

    private String source;
    private long tokenCount;

    @Setup(Level.Trial)
    public void setup() throws IOException {
        source = Files.readString(
            Path.of(inputPath),
            StandardCharsets.UTF_8
        );

        tokenCount = countTokens();
        System.out.println(
            "Input: " + inputPath +
            " | Source characters: " + source.length() +
            " | Tokens: " + tokenCount
        );
    }

    private long countTokens() {
        JavaLexer lexer = new JavaLexer(
            CharStreams.fromString(source)
        );

        long count = 0;
        Token token;

        while ((token = lexer.nextToken()).getType()
                != Token.EOF) {
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                count++;
            }
        }

        return count;
    }

    @Benchmark
    public long lexSource() {
        JavaLexer lexer = new JavaLexer(
            CharStreams.fromString(source)
        );

        long count = 0;
        Token token;

        while ((token = lexer.nextToken()).getType()
                != Token.EOF) {
            if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                count++;
            }
        }

        return count;
    }
}
