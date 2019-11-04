package com.permanovd.application;

import com.permanovd.infrastructure.InputFileValidationException;
import com.permanovd.infrastructure.OutputFileValidationException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransformationServiceTest {

    @Test
    void transformsFile_ifAllConditionsAreOk() throws IOException, TransformerException, OutputFileValidationException, InputFileValidationException {
        // Arrange.
        Path expectedXmlFile = Paths.get("src", "test", "resources", "transformation", "valid", "expected.xml");

        Path xmlFile = Paths.get("src", "test", "resources", "transformation", "valid", "before_transformation.xml");
        Path xslFile = Paths.get("src", "test", "resources", "transformation", "valid", "transform.xsl");
        Path xsdFile = Paths.get("src", "test", "resources", "transformation", "valid", "schema.xsd");
        Path outputFilePath = Paths.get("src", "test", "resources", "transformation", "valid", "result.xml");

        // Act.
        TransformationService transformationService = new TransformationService();
        transformationService.validateAndTransform(xmlFile.toFile(), xslFile.toFile(), xsdFile.toFile(), outputFilePath.toFile());

        String expectedStr = FileUtils.readFileToString(expectedXmlFile.toFile(), StandardCharsets.UTF_8).replaceAll("\\s+", "");
        String actualStr = FileUtils.readFileToString(outputFilePath.toFile(), StandardCharsets.UTF_8).replaceAll("\\s+", "");

        // Assert.
        assertEquals(expectedStr, actualStr);
    }
}
