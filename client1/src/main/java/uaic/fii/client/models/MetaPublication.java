package uaic.fii.client.models;

import java.util.Set;

public class MetaPublication {
   private final Set<MetaPublicationField> metaPublicationFields;

    public MetaPublication(Set<MetaPublicationField> metaPublicationFields) {
        this.metaPublicationFields = metaPublicationFields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MetaPublication{");
        metaPublicationFields.forEach(field -> {
            sb.append(field);
            sb.append(";");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }
}
