package com.permanovd.application;

import com.permanovd.infrastructure.TransformationServiceBuilder;
import com.permanovd.infrastructure.XsdValidationService;
import com.permanovd.infrastructure.XslTransformationService;


class TransformationService {

    static TransformationServiceBuilder builder() {
        // todo inject services.
        return new TransformationServiceBuilder(new XslTransformationService(), new XsdValidationService());
    }


}
