package com.permanovd;

import com.permanovd.application.TransformationService;
import com.permanovd.ui.TransformationServiceFacade;
import org.apache.commons.cli.DefaultParser;

import java.io.PrintWriter;

public class Application {

    public static void main(String[] args) {
        TransformationServiceFacade serviceFacade =
                new TransformationServiceFacade(new TransformationService(), new DefaultParser());

        serviceFacade.validateAndTransform(args, new PrintWriter(System.out));
    }
}
