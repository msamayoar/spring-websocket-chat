package com.tempest.moonlight.server.services.dto;

import com.tempest.moonlight.server.domain.GenericContact;

/**
 * Created by Yurii on 2015-06-16.
 */
public class GenericContactDTO implements ServerToClientDTO<GenericContact> {
    private int type;
    private String signature;
    private String owner;
    private int ownerType;

    public GenericContactDTO() {
    }

    public GenericContactDTO(int type, String signature, String owner, int ownerType) {
        this.type = type;
        this.signature = signature;
        this.owner = owner;
        this.ownerType = ownerType;
    }

    @Override
    public void fillWithEntity(GenericContact genericContact) {
        type =  genericContact.getType().getValue();
        signature = '@' + genericContact.getSignature();
        owner = '#' + genericContact.getOwner();
        ownerType = genericContact.getOwnerType().getValue();
    }

    @Override
    public String toString() {
        return "GenericContactDTO{" +
                "type=" + type +
                ", signature='" + signature + '\'' +
                ", owner='" + owner + '\'' +
                ", ownerType=" + ownerType +
                '}';
    }

    public int getType() {
        return type;
    }

    public GenericContactDTO setType(int type) {
        this.type = type;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public GenericContactDTO setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public GenericContactDTO setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public GenericContactDTO setOwnerType(int ownerType) {
        this.ownerType = ownerType;
        return this;
    }
}
