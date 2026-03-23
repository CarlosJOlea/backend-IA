package com.example.template.sampleentity.infrastructure.web;

import com.example.template.sampleentity.domain.SampleEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-23T15:33:44-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class SampleEntityWebMapperImpl implements SampleEntityWebMapper {

    @Override
    public SampleEntityResponse toResponse(SampleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();

        SampleEntityResponse sampleEntityResponse = new SampleEntityResponse( id, name, description );

        return sampleEntityResponse;
    }

    @Override
    public SampleEntity toDomain(SampleEntityRequest request) {
        if ( request == null ) {
            return null;
        }

        SampleEntity sampleEntity = new SampleEntity();

        sampleEntity.setName( request.name() );
        sampleEntity.setDescription( request.description() );

        return sampleEntity;
    }
}
