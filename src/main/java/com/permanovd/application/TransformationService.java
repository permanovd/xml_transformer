package com.permanovd.application;


import com.permanovd.infrastructure.InputFileValidationException;
import com.permanovd.infrastructure.OutputFileValidationException;
import com.permanovd.infrastructure.XmlTransformer;
import com.permanovd.infrastructure.XmlTransformers;

import javax.xml.transform.TransformerException;
import java.io.File;

public class TransformationService {

    public void validateAndTransform(File xmlFile, File xslFile, File xsdFile, File output) throws TransformerException, OutputFileValidationException, InputFileValidationException {
        XmlTransformer xmlTransformer = XmlTransformers.create(xmlFile, output);
        xmlTransformer.validateAndTransform(xsdFile, xslFile);
    }
}
