package uaic.fii.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Publication {
    private List<PublicationField> publicationFieldList;

    public Publication(){
        publicationFieldList = new ArrayList<>();
    }

    public Publication(List<PublicationField> publicationFieldList) {
        this.publicationFieldList = publicationFieldList;
    }

    public void setPublicationFieldList(List<PublicationField> publicationFieldList) {
        this.publicationFieldList = publicationFieldList;
    }

    public Optional<String> getFieldValue(String key){
        for(PublicationField field : publicationFieldList){
            if(field.getKey().equalsIgnoreCase(key)){
                return Optional.of(field.getValue());
            }
        }
        return Optional.empty();
    }

    public List<PublicationField> getPublicationFieldList(){
        return new ArrayList<>(publicationFieldList);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Publication: {");
        for(PublicationField publicationField : publicationFieldList){
            s.append(publicationField);
            s.append(";");
        }
        s.deleteCharAt(s.length()-1);
        s.append("}");
        return s.toString();
    }
}
