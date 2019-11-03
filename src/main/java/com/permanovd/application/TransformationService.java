package com.permanovd.application;


import com.permanovd.infrastructure.XmlTransformer;
import com.permanovd.infrastructure.XmlTransformers;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;

class TransformationService {

    void validateAndTransform(Path xmlFile, Path xslFile, Path xsdFile, Path output) throws TransformerException, SAXException, IOException {
        XmlTransformer xmlTransformer = XmlTransformers.create(xmlFile, output);
        xmlTransformer.validateAndTransform(xsdFile, xslFile);
    }
}
