package uaic.fii.converters;

import uaic.fii.models.PublicationField;
import uaic.fii.protobuf.PublicationOuterClass;

public class PublicationFieldConverter {
    public static PublicationField toPublicationField(PublicationOuterClass.PublicationField proto) {
        return new PublicationField(proto.getName(), proto.getValue());
    }

    public static PublicationOuterClass.PublicationField toProto(PublicationField publicationField) {
        return PublicationOuterClass.PublicationField.newBuilder()
                .setName(publicationField.getKey())
                .setValue(publicationField.getValue())
                .build();
    }
}
