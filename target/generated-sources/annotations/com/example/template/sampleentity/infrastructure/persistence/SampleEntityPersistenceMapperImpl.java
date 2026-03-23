package com.example.template.sampleentity.infrastructure.persistence;

import com.example.template.sampleentity.domain.SampleEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-23T15:33:44-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class SampleEntityPersistenceMapperImpl implements SampleEntityPersistenceMapper {

    @Override
    public SampleEntity toDomain(SampleEntityJpaEntity jpaEntity) {
        if ( jpaEntity == null ) {
            return null;
        }

        SampleEntity sampleEntity = new SampleEntity();

        sampleEntity.setId( jpaEntity.getId() );
        sampleEntity.setName( jpaEntity.getName() );
        sampleEntity.setDescription( jpaEntity.getDescription() );

        return sampleEntity;
    }

    @Override
    public SampleEntityJpaEntity toJpaEntity(SampleEntity domain) {
        if ( domain == null ) {
            return null;
        }

        SampleEntityJpaEntity sampleEntityJpaEntity = new SampleEntityJpaEntity();

        sampleEntityJpaEntity.setId( domain.getId() );
        sampleEntityJpaEntity.setName( domain.getName() );
        sampleEntityJpaEntity.setDescription( domain.getDescription() );

        return sampleEntityJpaEntity;
    }
}
