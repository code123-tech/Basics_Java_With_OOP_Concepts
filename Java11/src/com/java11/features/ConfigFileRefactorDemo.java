package com.java11.features;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ConfigFileRefactorDemo {

    private ConfigFileRefactorDemo() {}

    public static void run() {

        System.out.println();
        System.out.println("---- var + Files.readString + new String APIs ----");

        Path configPath = null;
        try {
            configPath = createSampleConfigFile();

            var legacy = readConfigLegacy(configPath);
            var modern = readConfigModern(configPath);

            System.out.println("Config file: " + configPath.toAbsolutePath());
            System.out.println("Legacy parsed lines: " + legacy);
            System.out.println("Modern parsed lines: " + modern);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            cleanupTempConfig(configPath);
        }
    }

    /**
     * Java 8 style: manual loop, BufferedReader, explicit filtering.
     */
    static List<String> readConfigLegacy(Path configPath) throws IOException {

        try (var reader = new BufferedReader(new FileReader(configPath.toFile(), StandardCharsets.UTF_8))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> !s.startsWith("#"))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Java 11 style: read full file, then use String#lines and String#isBlank.
     */
    static List<String> readConfigModern(Path configPath) throws IOException {

        var content = Files.readString(configPath);

        return content.lines()
                .map(String::strip)
                .filter(Predicate.not(String::isBlank))
                .filter(Predicate.not(s -> s.startsWith("#")))
                .toList();
    }

    static Path createSampleConfigFile() throws IOException {

        var content = String.join("\n",
                "# Sample config",
                "",
                "  host = localhost  ",
                "port=8080",
                "   ",
                "# this is a comment",
                "featureX.enabled=true",
                ""
        );

        var dir = Files.createTempDirectory("java11-config-demo-");
        var file = dir.resolve("app.conf");
        Files.writeString(file, content);
        return file;
    }

    static void cleanupTempConfig(Path configPath) {
        if (configPath == null) return;

        try {
            Files.deleteIfExists(configPath);
            var parent = configPath.getParent();
            if (parent != null) {
                Files.deleteIfExists(parent);
            }
        } catch (IOException e) {
            System.err.println("Cleanup failed for temp config dir: " + configPath);
        }
    }
}
