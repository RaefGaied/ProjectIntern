package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.AuthentificationConfiguration;
import org.jhipster.projectintern.service.dto.AuthentificationConfigurationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AuthentificationConfiguration} and its DTO {@link AuthentificationConfigurationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthentificationConfigurationMapper
    extends EntityMapper<AuthentificationConfigurationDTO, AuthentificationConfiguration> {}
