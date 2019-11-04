package com.permanovd.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class XsdValidationTest {

    @Test
    void returnsTrue_ifFileIsValid() throws SAXException, IOException {
        // Arrange.
        XsdValidationService validationService = new XsdValidationService();
        Path xmlFile = Paths.get("src", "test", "resources", "xsd_validation", "valid", "main.xml");
        Path schemaFile = Paths.get("src", "test", "resources", "xsd_validation", "valid", "schema.xsd");

        // Act.
        validationService.validate(xmlFile.toFile(), schemaFile.toFile());

        // Assert than no exceptions were thrown.
        assertTrue(true);
    }

    @Test
    void throws_ifFileIsInvalid() {
        // Arrange.
        XsdValidationService validationService = new XsdValidationService();
        Path xmlFile = Paths.get("src", "test", "resources", "xsd_validation", "invalid", "main.xml");
        Path schemaFile = Paths.get("src", "test", "resources", "xsd_validation", "invalid", "schema.xsd");

        // Act + assert.
        Assertions.assertThrows(SAXException.class, () -> {
            validationService.validate(xmlFile.toFile(), schemaFile.toFile());
        });
    }

    @Test
    void throws_ifFileIsNotFound() {
        // Arrange.
        XsdValidationService validationService = new XsdValidationService();
        Path xmlFile = Paths.get("src", "test", "resources", "xsd_validation", "invalid", "non-existing.xml");
        Path schemaFile = Paths.get("src", "test", "resources", "xsd_validation", "invalid", "schema.xsd");

        // Act + assert.
        Assertions.assertThrows(IOException.class, () -> {
            validationService.validate(xmlFile.toFile(), schemaFile.toFile());
        });
    }
}
