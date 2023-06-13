package uaic.fii.converters;

import uaic.fii.models.Publication;
import uaic.fii.models.PublicationField;
import uaic.fii.protobuf.PublicationOuterClass;

import java.util.ArrayList;
import java.util.List;

public class PublicationConverter {
    public static Publication fromProtoBuf(PublicationOuterClass.Publication proto) {
        List<PublicationField> publicationFields = new ArrayList<>();
        for (PublicationOuterClass.PublicationField fieldProto : proto.getPublicationFieldListList()) {
            PublicationField publicationField = new PublicationField(fieldProto.getName(), fieldProto.getValue());
            publicationFields.add(publicationField);
        }
        return new Publication(publicationFields);
    }

    public static PublicationOuterClass.Publication toProtoBuf(Publication publication) {
        PublicationOuterClass.Publication.Builder builder = PublicationOuterClass.Publication.newBuilder();
        for (PublicationField publicationField : publication.getPublicationFieldList()) {
            PublicationOuterClass.PublicationField.Builder fieldBuilder = PublicationOuterClass.PublicationField
                    .newBuilder()
                    .setName(publicationField.getKey())
                    .setValue(publicationField.getValue());
            builder.addPublicationFieldList(fieldBuilder);
        }
        return builder.build();
    }
}
