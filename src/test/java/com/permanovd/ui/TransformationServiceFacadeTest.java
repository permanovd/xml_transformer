package com.permanovd.ui;

import com.permanovd.application.TransformationService;
import com.permanovd.infrastructure.InputFileValidationException;
import com.permanovd.infrastructure.OutputFileValidationException;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TransformationServiceFacadeTest {

    @Test
    void writesFailureMessage_ifParamValidationFailed() throws ParseException {
        String expected = "Failed to parse command line args: failure." + System.getProperty("line.separator");
        TransformationService service = Mockito.mock(TransformationService.class);
        CommandLineParser parser = Mockito.mock(CommandLineParser.class);

        when(parser.parse(any(), any(), anyBoolean())).thenThrow(new ParseException("failure"));
        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        String[] args = {"invalid", "args"};

        TransformationServiceFacade facade = new TransformationServiceFacade(service, parser);
        facade.validateAndTransform(args, writer);

        assertEquals(expected, out.toString());
    }

    @Test
    void writesFailureMessage_ifFilesNotFound() throws ParseException {
        // Arrange.
        String expected = "Validation failed: Files are not found: file1.xml, file2.xml, file3.xml, file4.xml." + System.getProperty("line.separator");

        CommandLineParser parser = new DefaultParser();
        TransformationService service = Mockito.mock(TransformationService.class);
        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        String[] args = {"-s", "file1.xml", "-o", "file2.xml", "-sc", "file3.xml", "-t", "file4.xml"};

        TransformationServiceFacade facade = new TransformationServiceFacade(service, parser);
        TransformationServiceFacade facadeMock = Mockito.spy(facade);
        when(facadeMock.fileExists(anyString())).then(v -> false);

        // Act.
        facadeMock.validateAndTransform(args, writer);
        // Assert.
        assertEquals(expected, out.toString());
    }

    @Test
    void writesFailureMessage_ifInputFileValidationFailed() throws ParseException, TransformerException, OutputFileValidationException, InputFileValidationException {
        // Arrange.
        String expected = "Input file validation failure: Validation failure." + System.getProperty("line.separator");

        CommandLineParser parser = new DefaultParser();
        TransformationService service = Mockito.mock(TransformationService.class);
        doThrow(new InputFileValidationException(new SAXException("Validation failure.")))
                .when(service)
                .validateAndTransform(any(), any(), any(), any());

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        String[] args = {"-s", "file1.xml", "-o", "file2.xml", "-sc", "file3.xml", "-t", "file4.xml"};

        TransformationServiceFacade facade = new TransformationServiceFacade(service, parser);
        TransformationServiceFacade facadeMock = Mockito.spy(facade);
        when(facadeMock.fileExists(anyString())).then(v -> true);

        // Act.
        facadeMock.validateAndTransform(args, writer);
        // Assert.
        assertEquals(expected, out.toString());
    }

    @Test
    void writesFailureMessage_ifTransformationFailed() throws ParseException, TransformerException, OutputFileValidationException, InputFileValidationException {
        // Arrange.
        String expected = "Transformation failure: Transformation failure" + System.getProperty("line.separator");

        CommandLineParser parser = new DefaultParser();
        TransformationService service = Mockito.mock(TransformationService.class);
        doThrow(new TransformerException("Transformation failure"))
                .when(service)
                .validateAndTransform(any(), any(), any(), any());

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        String[] args = {"-s", "file1.xml", "-o", "file2.xml", "-sc", "file3.xml", "-t", "file4.xml"};

        TransformationServiceFacade facade = new TransformationServiceFacade(service, parser);
        TransformationServiceFacade facadeMock = Mockito.spy(facade);
        when(facadeMock.fileExists(anyString())).then(v -> true);

        // Act.
        facadeMock.validateAndTransform(args, writer);
        // Assert.
        assertEquals(expected, out.toString());
    }

    @Test
    void writesFailureMessage_ifOutputFileValidationFailed() throws ParseException, TransformerException, OutputFileValidationException, InputFileValidationException {
        // Arrange.
        String expected = "Output file validation (after transformation) failure: Validation failure." + System.getProperty("line.separator");

        CommandLineParser parser = new DefaultParser();
        TransformationService service = Mockito.mock(TransformationService.class);
        doThrow(new OutputFileValidationException(new SAXException("Validation failure.")))
                .when(service)
                .validateAndTransform(any(), any(), any(), any());

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        String[] args = {"-s", "file1.xml", "-o", "file2.xml", "-sc", "file3.xml", "-t", "file4.xml"};

        TransformationServiceFacade facade = new TransformationServiceFacade(service, parser);
        TransformationServiceFacade facadeMock = Mockito.spy(facade);
        when(facadeMock.fileExists(anyString())).then(v -> true);

        // Act.
        facadeMock.validateAndTransform(args, writer);
        // Assert.
        assertEquals(expected, out.toString());
    }

    @Test
    void writesSuccessMessage_ifProcessingIsOk() throws ParseException, TransformerException, OutputFileValidationException, InputFileValidationException {
        // Arrange.
        String expected = "Success. Please see output file file2.xml" + System.getProperty("line.separator");

        CommandLineParser parser = new DefaultParser();
        TransformationService service = Mockito.mock(TransformationService.class);
        doNothing().when(service).validateAndTransform(any(), any(), any(), any());

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        String[] args = {"-s", "file1.xml", "-o", "file2.xml", "-sc", "file3.xml", "-t", "file4.xml"};

        TransformationServiceFacade facade = new TransformationServiceFacade(service, parser);
        TransformationServiceFacade facadeMock = Mockito.spy(facade);
        when(facadeMock.fileExists(anyString())).then(v -> true);

        // Act.
        facadeMock.validateAndTransform(args, writer);
        // Assert.
        assertEquals(expected, out.toString());
    }
}
