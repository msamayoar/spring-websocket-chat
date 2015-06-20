package com.tempest.moonlight.server.services.dto;

import com.tempest.moonlight.server.exceptions.local.dto.DtoException;
import com.tempest.moonlight.server.util.CollectionsUtils;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-19.
 */
public interface DtoConverter {
    void addDTO(Class entityClass, EntityDTO.DtoType dtoType, Class<? extends EntityDTO> dtoClass) throws DtoException;

    <Entity> ServerToClientDTO<Entity> convertToDTO(Entity entity, Class<? extends ServerToClientDTO<Entity>> dtoClass) throws DtoException;

    default <Entity> Collection<? extends ServerToClientDTO<Entity>> convertToDTOs(Collection<Entity> entities, Class<? extends ServerToClientDTO<Entity>> dtoClass) {
        return CollectionsUtils.convertToList(
                entities,
                entity -> {
                    try {
                        return convertToDTO(entity, dtoClass);
                    } catch (DtoException e) {
                        throw new RuntimeException("Error during converting to DTOs", e);
                    }
                }
        );
    }

    <Entity> Entity convertFromDTO(ClientToServerDTO<Entity> dto, Class<Entity> entityClass) throws DtoException;

    default <Entity> Collection<Entity> convertFromDTOs(Collection<ClientToServerDTO<Entity>> dtos, Class<Entity> entityClass) throws DtoException {
        return CollectionsUtils.convertToList(
                dtos,
                dto -> {
                    try {
                        return convertFromDTO(dto, entityClass);
                    } catch (DtoException e) {
                        throw new RuntimeException("Error while converting from DTO", e);
                    }
                }
        );
    }

    <T> ServerToClientDTO<T> convertToDTO(T entity) throws DtoException;

    <T> Collection<? extends ServerToClientDTO<T>> convertToDTOs(Collection<T> entities);

    <T> T convertFromDTO(ClientToServerDTO<T> dto) throws DtoException;

    default <T> Collection<T> convertFromDTO(Collection<? extends ClientToServerDTO<T>> dtos) {
        return CollectionsUtils.convertToList(
                dtos,
                dto -> {
                    try {
                        return convertFromDTO(dto);
                    } catch (DtoException e) {
                        throw new RuntimeException("Error while converting from DTOs", e);
                    }
                }
        );
    }
}
