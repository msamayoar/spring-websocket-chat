package com.tempest.moonlight.server.services.dto;

import com.tempest.moonlight.server.exceptions.local.dto.*;
import com.tempest.moonlight.server.util.CollectionsUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yurii on 2015-06-18.
 */
@Component
public class DtoConverterImpl implements DtoConverter {
    private static final Logger logger = Logger.getLogger(DtoConverterImpl.class.getName());

//    private final Map<Class, DtosHolder> beansDtosContainer = new HashMap<>();
//    private final Map<Class<? extends EntityDTO>, Class> dtoToEntityMap = new HashMap<>();

    private DtosContainer dtosContainer = new DtosContainer();

    @Override
    public void addDTO(Class entityClass, EntityDTO.DtoType dtoType, Class<? extends EntityDTO> dtoClass) throws DtoException {
        if(!dtoType.dtoClass.isAssignableFrom(dtoClass)) {
            throw new IncorrectDtoTypeException(entityClass, dtoType, dtoClass);
        }

//        DtosHolder dtosHolder = dtosContainer.getHolderForEntityClass(entityClass);
//        if(dtosHolder == null) {
//            dtosHolder = new DtosHolder(entityClass);
//            beansDtosContainer.put(entityClass, dtosHolder);
//        }
//        dtosHolder.setDto(dtoType, dtoClass);
//
//        dtoToEntityMap.put(dtoClass, entityClass);
        dtosContainer.bind(entityClass, dtoClass, dtoType);

        logger.info("Successfully added " + dtoType + " DTO of class = " + dtoClass.getName() + " for entity class = " + entityClass);
    }

    @Override
    public <Entity> ServerToClientDTO<Entity> convertToDTO(Entity entity, Class<? extends ServerToClientDTO<Entity>> dtoClass) throws DtoException {
        return convertToDtoInternal(entity, dtoClass);
    }

    @Override
    public <Entity> Entity convertFromDTO(ClientToServerDTO<Entity> dto, Class<Entity> entityClass) throws DtoException {
        if(dto == null) {
            return null;
        }
        try {
            Entity entity = entityClass.newInstance();
            dto.fillEntity(entity);
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CanNotCreateEntityException(entityClass, e);
        }
    }

    private <Entity> ServerToClientDTO<Entity> convertToDtoInternal(Entity entity, Class<? extends ServerToClientDTO<Entity>> dtoClass) throws DtoException {
        try {
            ServerToClientDTO<Entity> serverToClientDTO = dtoClass.newInstance();
            serverToClientDTO.fillWithEntity(entity);
            return serverToClientDTO;

        } catch (InstantiationException | IllegalAccessException e) {
            throw new CanNotCreateDtoException(entity.getClass(), dtoClass, e);
        }
    }

    @Override
    public <T> ServerToClientDTO<T> convertToDTO(T entity) throws DtoException {
        if(entity == null) {
            return null;
        }

//        Class<?> entityClass = entity.getClass();
//        DtosHolder dtosHolder = getEntityDtoHolder(entityClass);
//        Class<? extends ServerToClientDTO<T>> dtoClass = (Class<? extends ServerToClientDTO<T>>) getDtoClass(dtosHolder, EntityDTO.DtoType.S2C);

        Class<? extends ServerToClientDTO<T>> dtoClass = (Class<? extends ServerToClientDTO<T>>) getDtoClass(entity, EntityDTO.DtoType.S2C);

        return convertToDtoInternal(entity, dtoClass);
    }

    private DtosHolder getEntityDtoHolder(Class<?> entityClass) {
        DtosHolder dtosHolder = dtosContainer.getHolderForEntityClass(entityClass);
        if(dtosHolder == null) {
            throw new NoDtoClassFoundException(entityClass);
        }
        return dtosHolder;
    }

    private <T> Class<? extends EntityDTO<T>> getDtoClass(DtosHolder dtosHolder, EntityDTO.DtoType dtoType) {
        Class<? extends EntityDTO> dtoClass = dtosHolder.getDTO(dtoType == EntityDTO.DtoType.C2S);
        if(dtoClass == null) {
            throw new NoDtoClassFoundException(dtosHolder.entityClass, dtoType);
        }
        return (Class<? extends EntityDTO<T>>) dtoClass;
    }

    private <T> Class<? extends EntityDTO<T>> getDtoClass(T entity, EntityDTO.DtoType dtoType) {
        Class<?> entityClass = entity.getClass();
        DtosHolder dtosHolder = getEntityDtoHolder(entityClass);
        return getDtoClass(dtosHolder, dtoType);
    }

    @Override
    public <T> Collection<? extends ServerToClientDTO<T>> convertToDTOs(Collection<T> entities) {
        if(entities == null) {
            throw new NullPointerException();
        }
        if(entities.isEmpty()) {
            return new ArrayList<>();
        }

        T firstEntity = entities.iterator().next();
        Class<? extends ServerToClientDTO<T>> dtoClass = (Class<? extends ServerToClientDTO<T>>) getDtoClass(firstEntity, EntityDTO.DtoType.S2C);

        return CollectionsUtils.convertToList(
                entities,
                entity -> convertToDtoInternal(entity, dtoClass)
        );
    }

    @Override
    public <T> T convertFromDTO(ClientToServerDTO<T> dto) throws DtoException {
        if(dto == null) {
            return null;
        }

        Class<? extends EntityDTO> dtoClass = dto.getClass();
        Class entityClass = dtosContainer.getEntityClassByDtoClass(dtoClass);
        if(entityClass == null) {
            throw new NoEntityClassFoundException(dtoClass);
        }

        try {
            T entity = (T) entityClass.newInstance();
            dto.fillEntity(entity);
            return entity;

        } catch (InstantiationException | IllegalAccessException e) {
            throw new CanNotCreateEntityException(entityClass, dtoClass, e);
        }
    }


    private class DtosContainer {
        private final Map<Class, DtosHolder> beansDtosContainer = new HashMap<>();
        private final Map<Class<? extends EntityDTO>, Class> dtoToEntityMap = new HashMap<>();

        public void bind(Class entityClass, Class<? extends EntityDTO> dtoClass, EntityDTO.DtoType dtoType) throws DtoException {
            DtosHolder dtosHolder = beansDtosContainer.computeIfAbsent(entityClass, DtosHolder::new);
            dtosHolder.setDto(dtoType, dtoClass);
            dtoToEntityMap.put(dtoClass, entityClass);
        }

        public DtosHolder getHolderForEntityClass(Class entityClass) {
            return beansDtosContainer.get(entityClass);
        }

        public Class getEntityClassByDtoClass(Class dtoClass) {
            return dtoToEntityMap.get(dtoClass);
        }
    }


    private class DtosHolder {
        public final Class entityClass;

        private Class<? extends ClientToServerDTO> c2sDto;
        private Class<? extends ServerToClientDTO> s2cDto;

        public DtosHolder(Class entityClass) {
            this.entityClass = entityClass;
        }

        public void setDto(EntityDTO.DtoType dtoType, Class<? extends EntityDTO> dtoClass) throws DtoException {
            switch (dtoType) {
                case C2S: {
                    setC2SDto((Class<? extends ClientToServerDTO>) dtoClass);
                    break;
                }
                case S2C: {
                    setS2CDto((Class<? extends ServerToClientDTO>) dtoClass);
                    break;
                }
                case BiDir: {
                    setC2SDto((Class<? extends ClientToServerDTO>) dtoClass);
                    setS2CDto((Class<? extends ServerToClientDTO>) dtoClass);
                    break;
                }
                default:
                    throw new UnsupportedDtoTypeException(dtoType);
            }
        }

        public void setC2SDto(Class<? extends ClientToServerDTO> c2sDtoClass) throws OverrideDtoTypeException {
            if(c2sDto != null) {
                throw new OverrideDtoTypeException(entityClass, EntityDTO.DtoType.C2S, c2sDtoClass, c2sDto);
            }
            c2sDto = c2sDtoClass;
        }

        public void setS2CDto(Class<? extends ServerToClientDTO> s2cDtoClass) throws OverrideDtoTypeException {
            if(s2cDto != null) {
                throw new OverrideDtoTypeException(entityClass, EntityDTO.DtoType.S2C, s2cDtoClass, s2cDto);
            }
            this.s2cDto = s2cDtoClass;
        }

        public Class<? extends EntityDTO> getDTO(boolean incoming) {
            return incoming ? c2sDto : s2cDto;
        }
    }
}
