package com.permanovd.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class XmlTransformationTest {

    @Test
    void throwsException_ifInputFileValidationFailed() throws IOException, SAXException {
        // Arrange.
        XsdValidationService validationService = Mockito.mock(XsdValidationService.class);
        XslTransformationService transformationService = Mockito.mock(XslTransformationService.class);
        doThrow(SAXException.class)
                .when(validationService)
                .validate(any(), any());

        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.OFF);

        XmlTransformer transformer =
                new XmlTransformer(transformationService, validationService, new File(""), new File(""), logger);

        // Act + assert.
        Assertions.assertThrows(InputFileValidationException.class, () -> {
            transformer.validateAndTransform(new File(""), new File(""));
        });
    }

    @Test
    void throwsException_ifTransformationFailed() throws TransformerException {
        // Arrange.
        XsdValidationService validationService = Mockito.mock(XsdValidationService.class);
        XslTransformationService transformationService = Mockito.mock(XslTransformationService.class);

        doThrow(TransformerException.class)
                .when(transformationService)
                .transform(any(), any(), any());

        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.OFF);

        XmlTransformer transformer =
                new XmlTransformer(transformationService, validationService, new File(""), new File(""), logger);

        // Act + assert.
        Assertions.assertThrows(TransformerException.class, () -> {
            transformer.validateAndTransform(new File(""), new File(""));
        });
    }

    @Test
    void throwsException_ifOutputFileValidationFailed() throws IOException, SAXException {
        // Arrange.
        XsdValidationService validationService = Mockito.mock(XsdValidationService.class);
        XslTransformationService transformationService = Mockito.mock(XslTransformationService.class);

        doNothing()
                .doThrow(SAXException.class)
                .when(validationService)
                .validate(any(), any());

        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.OFF);

        XmlTransformer transformer =
                new XmlTransformer(transformationService, validationService, new File(""), new File(""), logger);

        // Act + assert.
        Assertions.assertThrows(OutputFileValidationException.class, () -> {
            transformer.validateAndTransform(new File(""), new File(""));
        });
    }
}
