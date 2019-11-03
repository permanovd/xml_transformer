package com.permanovd.infrastructure;

import java.nio.file.Path;

public final class XmlTransformers {

    private XmlTransformers() {
    }

    public static XmlTransformer create(Path sourceFile, Path outputFilePath) {
        // todo inject.
        XslTransformationService transformationService = new XslTransformationService();
        XsdValidationService validationService = new XsdValidationService();
        
        return new XmlTransformer(
                transformationService,
                validationService,
                sourceFile,
                outputFilePath
        );
    }
}
