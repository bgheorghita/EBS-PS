package uaic.fii.utils;

import uaic.fii.converters.PublicationConverter;
import uaic.fii.models.Publication;
import uaic.fii.protobuf.PublicationOuterClass;

public class PublicationProtoDeserializer {
    public static Publication deserialize(byte[] bytes) {
        try {
            PublicationOuterClass.Publication proto = PublicationOuterClass.Publication.parseFrom(bytes);
            return PublicationConverter.fromProtoBuf(proto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
