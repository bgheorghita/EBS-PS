package uaic.fii.generators.publications;

import uaic.fii.generators.CityGenerator;
import uaic.fii.generators.DateGenerator;
import uaic.fii.generators.DirectionGenerator;
import uaic.fii.generators.NumberGenerator;
import uaic.fii.models.Publication;
import uaic.fii.models.PublicationField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class PublicationGenerator implements Callable<List<Publication>> {
    private final int start;
    private final int end;

    public PublicationGenerator(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public List<Publication> call() {
        PublicationField stationIdField;
        PublicationField cityField;
        PublicationField tempField;
        PublicationField rainField;
        PublicationField windField;
        PublicationField directionField;
        PublicationField dateField;

        List<Publication> publications = new ArrayList<>();
        for (int i = start; i < end; i++) {
            stationIdField = new PublicationField("stationId", String.valueOf(NumberGenerator.getRandomInt(1, 10)));
            cityField = new PublicationField("city", CityGenerator.getRandomCity());
            tempField = new PublicationField("temp", String.valueOf(NumberGenerator.getRandomInt(-20, 40)));
            rainField = new PublicationField("rain", String.valueOf(NumberGenerator.getRandomDouble(0.0, 100.0)));
            windField = new PublicationField("wind", String.valueOf(NumberGenerator.getRandomInt(0, 100)));
            directionField = new PublicationField("direction", DirectionGenerator.getRandomDirection());
            dateField = new PublicationField("date", DateGenerator.getRandomDate());

            Publication publication = new Publication(
                    Arrays.asList(stationIdField, cityField, tempField, rainField, windField, directionField, dateField)
            );
            publications.add(publication);
        }
        return publications;
    }
}