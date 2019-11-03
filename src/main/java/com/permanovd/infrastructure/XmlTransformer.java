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

    XmlTransformer withSchema(Path schema) {
        this.schema = schema;
        return this;
    }

    XmlTransformer withTransformationConfig(Path transformationConfig) {
        this.transformationConfig = transformationConfig;
        return this;
    }

    public void transform() throws IOException, SAXException, TransformerException {
        if (null != schema) {
            validationService.validate(source.toFile(), schema.toFile());
        }

        if (null != transformationConfig) {
            transformationService.transform(source, transformationConfig, output);
        }

        if (null != schema) {
            validationService.validate(output.toFile(), schema.toFile());
        }
    }
}
