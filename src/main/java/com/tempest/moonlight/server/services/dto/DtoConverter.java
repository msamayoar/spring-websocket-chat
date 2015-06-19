package com.tempest.moonlight.server.services.dto;

import com.tempest.moonlight.server.exceptions.local.dto.DtoException;
import com.tempest.moonlight.server.util.StreamUtils;

import java.util.Collection;

/**
 * Created by Yurii on 2015-06-19.
 */
public interface DtoConverter {
    void addDTO(Class entityClass, EntityDTO.DtoType dtoType, Class<? extends EntityDTO> dtoClass) throws DtoException;

    <T> ServerToClientDTO<T> convertToOutgoing(T entity) throws DtoException;

    default <T> Collection<? extends ServerToClientDTO<T>> convertToOutgoing(Collection<T> entities) {
        return StreamUtils.convertToList(
                entities,
                entity -> {
                    try {
                        return convertToOutgoing(entity);
                    } catch (DtoException e) {
                        throw new RuntimeException("Error while converting to DTOs", e);
                    }
                }
        );
    }

    <T> T convertFromIncoming(ClientToServerDTO<T> dto) throws DtoException;

    default <T> Collection<T> convertFromIncoming(Collection<? extends ClientToServerDTO<T>> dtos) {
        return StreamUtils.convertToList(
                dtos,
                dto -> {
                    try {
                        return convertFromIncoming(dto);
                    } catch (DtoException e) {
                        throw new RuntimeException("Error while converting from DTOs", e);
                    }
                }
        );
    }
}
