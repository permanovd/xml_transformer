package com.permanovd.infrastructure;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.nio.file.Path;

public class XslTransformationService {
    public Path transform(Path xmlFile, Path xslFile, Path outputFilePath) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(xslFile.toFile());
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(xmlFile.toFile());
        transformer.transform(text, new StreamResult(outputFilePath.toFile()));

        return outputFilePath;
    }
}
