package ru.systemoteh.photos.generator.alternative;

import ru.systemoteh.photos.ejb.repository.jpa.StaticJPAQueryInitializer;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.Bean;

@Dependent
@TestDataGeneratorEnvironment
public class JPARepositoryFinderAlternative extends StaticJPAQueryInitializer.JPARepositoryFinder {

    @Override
    protected boolean isCandidateValid(Bean<?> bean) {
        return false;
    }
}
