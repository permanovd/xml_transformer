package com.permanovd.infrastructure;

import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class XmlTransformer {

    private final XslTransformationService transformationService;
    private final XsdValidationService validationService;
    private final File source;
    private final File output;
    private Logger logger;

    XmlTransformer(XslTransformationService transformationService,
                   XsdValidationService validationService,
                   File source,
                   File output,
                   Logger logger) {
        this.transformationService = transformationService;
        this.validationService = validationService;
        this.source = source;
        this.output = output;
        this.logger = logger;
    }

    public void validateAndTransform(File schema, File transformationConfig) throws InputFileValidationException, OutputFileValidationException, TransformerException {
        this.logger.info(String.format("Starting transformation of \"%s\" by \"%s\" schema and \"%s\" transformation.",
                source.getName(), schema.getName(), transformationConfig.getName()));
        try {
            validationService.validate(source, schema);
        } catch (SAXException | IOException e) {
            this.logger.warning(String.format("Input validation failed for file %s.", source.getName()));
            throw new InputFileValidationException(e);
        }

        try {
            transformationService.transform(source, transformationConfig, output);
        } catch (TransformerException e) {
            this.logger.warning(String.format("Transformation failed for file %s.", source.getName()));
            throw e;
        }

        try {
            validationService.validate(output, schema);
        } catch (SAXException | IOException e) {
            this.logger.warning(String.format("Output validation failed for file %s.", source.getName()));
            throw new OutputFileValidationException(e);
        }

        this.logger.info(String.format("Transformation of \"%s\" by \"%s\" schema and \"%s\" succeeded.",
                source.getName(), schema.getName(), transformationConfig.getName()));
    }
}
