package com.permanovd.infrastructure;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

class XslTransformationService {
    void transform(File xmlFile, File xslFile, File outputFilePath) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(xslFile);
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(xmlFile);
        transformer.transform(text, new StreamResult(outputFilePath));
    }
}
