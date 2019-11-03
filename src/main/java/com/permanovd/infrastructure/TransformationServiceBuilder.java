package com.permanovd.infrastructure;

import java.nio.file.Path;

public class TransformationServiceBuilder {
    private XslTransformationService transformationService;
    private XsdValidationService validationService;

    private Path transformationFilePath;
    private Path schemaFilePath;
    private Path outputFilePath;
    private Path sourceFile;

    public TransformationServiceBuilder(XslTransformationService transformationService, XsdValidationService validationService) {
        this.transformationService = transformationService;
        this.validationService = validationService;
    }

    public TransformationServiceBuilder sourceFile(Path file) {
        sourceFile = file;
        return this;
    }

    public TransformationServiceBuilder transformWith(Path xslFile) {
        transformationFilePath = xslFile;
        return this;
    }

    public TransformationServiceBuilder validateWith(Path xsdFile) {
        schemaFilePath = xsdFile;
        return this;
    }

    public TransformationServiceBuilder outputTo(Path outputFilePath) {
        this.outputFilePath = outputFilePath;
        return this;
    }

    public XmlTransformer get() {
        XmlTransformer result = new XmlTransformer(
                transformationService,
                validationService,
                sourceFile,
                outputFilePath
        );

        if (null != schemaFilePath) {
            result = result.withSchema(schemaFilePath);
        }

        if (null != transformationFilePath) {
            result = result.withTransformationConfig(transformationFilePath);
        }

        return result;
    }

}
