package com.permanovd.infrastructure;

import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;

public class XmlTransformer {

    private final XslTransformationService transformationService;
    private final XsdValidationService validationService;
    private final Path source;
    private Path schema;
    private Path transformationConfig;
    private final Path output;

    XmlTransformer(XslTransformationService transformationService,
                   XsdValidationService validationService,
                   Path source,
                   Path output) {
        this.transformationService = transformationService;
        this.validationService = validationService;
        this.source = source;
        this.output = output;
    }

    public void validateAndTransform(Path schema, Path transformationConfig) throws IOException, SAXException, TransformerException {
        validationService.validate(source.toFile(), schema.toFile());
        transformationService.transform(source, transformationConfig, output);
        validationService.validate(output.toFile(), schema.toFile());
    }
}
