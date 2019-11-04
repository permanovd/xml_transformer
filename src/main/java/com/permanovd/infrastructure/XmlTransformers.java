package com.permanovd.infrastructure;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public final class XmlTransformers {

    private XmlTransformers() {
    }

    public static XmlTransformer create(File sourceFile, File outputFilePath) {
        // todo inject.
        XslTransformationService transformationService = new XslTransformationService();
        XsdValidationService validationService = new XsdValidationService();

        Logger logger = Logger.getLogger("application");
        logger.setLevel(Level.ALL);

        try {
            FileHandler fh = new FileHandler("./app.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            // Do nothing. We will log output directly to console.
            logger.warning("Failed to create log file. Reason: " + e.getMessage());
        }

        return new XmlTransformer(
                transformationService,
                validationService,
                sourceFile,
                outputFilePath,
                logger
        );
    }
}
