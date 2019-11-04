package com.permanovd.ui;

import com.permanovd.application.TransformationService;
import com.permanovd.infrastructure.InputFileValidationException;
import com.permanovd.infrastructure.OutputFileValidationException;
import org.apache.commons.cli.*;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;

import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransformationServiceFacade {

    private TransformationService transformationService;
    private CommandLineParser commandLineParser;

    public TransformationServiceFacade(TransformationService transformationService, CommandLineParser commandLineParser) {
        this.transformationService = transformationService;
        this.commandLineParser = commandLineParser;
    }

    public void validateAndTransform(String[] args, PrintWriter output) {
        CommandLine cli;
        try {
            cli = parse(args);
            validateInput(cli);
        } catch (ParseException e) {
            output.println(String.format("Failed to parse command line args: %s.", e.getMessage()));
            output.flush();
            return;
        } catch (FileNotFoundException e) {
            output.println(String.format("Validation failed: %s", e.getMessage()));
            output.flush();
            return;
        }

        process(cli, output);
    }

    private void process(CommandLine cli, PrintWriter output) {
        Path source = Paths.get(cli.getOptionValue("s"));
        Path outputFile = Paths.get(cli.getOptionValue("o"));
        Path xsd = Paths.get(cli.getOptionValue("sc"));
        Path xsl = Paths.get(cli.getOptionValue("t"));

        try {
            transformationService.validateAndTransform(source.toFile(), xsl.toFile(), xsd.toFile(), outputFile.toFile());
            output.println(String.format("Success. Please see output file %s", outputFile.getFileName()));
        } catch (InputFileValidationException e) {
            output.println("Input file validation failure: ".concat(e.getMessage()));
        } catch (TransformerException e) {
            output.println("Transformation failure: ".concat(e.getMessage()));
        } catch (OutputFileValidationException e) {
            output.println("Output file validation (after transformation) failure: ".concat(e.getMessage()));
        } finally {
            output.flush();
        }
    }

    private void validateInput(CommandLine cliInput) throws FileNotFoundException {
        Options options = getOptions();
        final List<String> errorList = new ArrayList<>();

        options.getOptions().stream()
                .filter(Option::isRequired)
                .forEach(option -> {
                    String optionValue = cliInput.getOptionValue(option.getOpt());
                    if (fileExists(optionValue)) {
                        return;
                    }

                    errorList.add(optionValue);
                });

        if (errorList.isEmpty()) {
            return;
        }

        String errorMessage = "Files are not found: " + String.join(", ", errorList) + ".";

        throw new FileNotFoundException(errorMessage);
    }

    boolean fileExists(String optionValue) {
        return Files.exists(Paths.get(optionValue));
    }

    private CommandLine parse(String[] args) throws ParseException {
        Options options = getOptions();

        return commandLineParser.parse(options, args, true);
    }

    private Options getOptions() {
        Options options = new Options();
        options.addRequiredOption("s", "source", true, "Source file.");
        options.addRequiredOption("o", "output", true, "Output file.");
        options.addRequiredOption("sc", "schema", true, "Xsd schema file.");
        options.addRequiredOption("t", "transform", true, "Xslt file.");

        return options;
    }
}
