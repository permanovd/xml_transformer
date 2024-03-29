package com.permanovd.infrastructure;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.transform.TransformerException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XslTransformationTest {

    @Test
    void transforms_ifFileIsValid() throws IOException, TransformerException {
        // Arrange.
        Path expectedXmlFile = Paths.get("src", "test", "resources", "xsl_transformation", "valid", "expected.html");
        Path xmlFile = Paths.get("src", "test", "resources", "xsl_transformation", "valid", "before_transformation.xml");
        Path xslFile = Paths.get("src", "test", "resources", "xsl_transformation", "valid", "transform.xsl");
        Path outputFilePath = Paths.get("src", "test", "resources", "xsl_transformation", "valid", "result.html");

        // Act.
        XslTransformationService transformer = new XslTransformationService();
        transformer.transform(xmlFile.toFile(), xslFile.toFile(), outputFilePath.toFile());

        String expectedStr = FileUtils.readFileToString(expectedXmlFile.toFile(), StandardCharsets.UTF_8).replaceAll("\\s+", "");
        String actualStr = FileUtils.readFileToString(outputFilePath.toFile(), StandardCharsets.UTF_8).replaceAll("\\s+", "");

        // Assert.
        assertEquals(expectedStr, actualStr);
    }

    @Test
    void throws_ifXslFileIsInvalid() {
        // Arrange.
        Path xmlFile = Paths.get("src", "test", "resources", "xsl_transformation", "invalid", "before_transformation.xml");
        Path xslFile = Paths.get("src", "test", "resources", "xsl_transformation", "invalid", "transform.xsl");
        Path outputFilePath = Paths.get("src", "test", "resources", "xsl_transformation", "invalid", "result.html");

        // Act + assert.
        XslTransformationService transformer = new XslTransformationService();
        Assertions.assertThrows(TransformerException.class, () -> {
            transformer.transform(xmlFile.toFile(), xslFile.toFile(), outputFilePath.toFile());
        });
    }
}
