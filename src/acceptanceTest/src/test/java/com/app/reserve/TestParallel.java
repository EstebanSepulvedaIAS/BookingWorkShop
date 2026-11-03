package com.app.reserve;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestParallel {

    private final static int WORKERS = 1;

    @Test
    void testAll(){
        Results results = Runner.path("classpath:features")
                .outputCucumberJson(true)
                .relativeTo(getClass())
                .parallel(WORKERS);
        generateReport(results.getReportDir());
        org.junit.jupiter.api.Assertions.assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    private static void generateReport(String karateOutputPath) {
        List<String> jsonFiles = new ArrayList<>();
        Path dirPath = Paths.get(karateOutputPath);

        try(Stream<Path> pathStream = Files.walk(dirPath)){
            pathStream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> jsonFiles.add(path.toAbsolutePath().toString()));
        }catch (Exception e){
            e.printStackTrace();
        }
        Configuration configuration = new Configuration(new File("target"), "acceptance_test");
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}