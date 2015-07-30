package com.tempest.moonlight.server.common.dto;

/**
 * Created by Yurii on 2015-06-18.
 */
public interface EntityDTO<Entity> {

    enum DtoType {
        C2S(ClientToServerDTO.class),
        S2C(ServerToClientDTO.class),
        BiDir(BiDirectionalDTO.class)
        ;

        public final Class<? extends EntityDTO> dtoClass;

        DtoType(Class<? extends EntityDTO> dtoClass) {
            this.dtoClass = dtoClass;
        }
    }
}
