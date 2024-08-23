package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.Partenaire;
import org.jhipster.projectintern.service.dto.PartenaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Partenaire} and its DTO {@link PartenaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartenaireMapper extends EntityMapper<PartenaireDTO, Partenaire> {}
