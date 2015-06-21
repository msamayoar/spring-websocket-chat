package com.tempest.moonlight.server.services.dto.contacts;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.GenericContact;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.services.dto.BiDirectionalDTO;
import com.tempest.moonlight.server.services.dto.ServerToClientDTO;

/**
 * Created by Yurii on 2015-06-16.
 */
public class GenericContactDTO implements ServerToClientDTO<GenericContact> /*implements BiDirectionalDTO<GenericContact>*/ {
    private int type;
    private String signature;
//
//    private int ownerType;
//    private String ownerSignature;

    public GenericContactDTO() {
    }

//    public GenericContactDTO(int type, String signature, int ownerType, String ownerSignature) {
//        this.type = type;
//        this.signature = signature;
//        this.ownerSignature = ownerSignature;
//        this.ownerType = ownerType;
//    }

    @Override
    public void fillWithEntity(GenericContact genericContact) {
        type =  genericContact.getContact().getType().getValue();
        signature = genericContact.getContact().getSignature();
//        ownerType = genericContact.getOwner().getType().getValue();
//        ownerSignature = genericContact.getOwner().getSignature();
    }

//    @Override
//    public void fillEntity(GenericContact contact) {
//        contact.setContact(
//                new GenericParticipant(
//                        ParticipantType.getByValue(type),
//                        signature
//                )
//        );
//    }

    @Override
    public String toString() {
        return "GenericContactDTO{" +
                "type=" + type +
                ", signature='" + signature + '\'' +
//                ", ownerSignature='" + ownerSignature + '\'' +
//                ", ownerType=" + ownerType +
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

//    public String getOwnerSignature() {
//        return ownerSignature;
//    }
//
//    public GenericContactDTO setOwnerSignature(String ownerSignature) {
//        this.ownerSignature = ownerSignature;
//        return this;
//    }
//
//    public int getOwnerType() {
//        return ownerType;
//    }
//
//    public GenericContactDTO setOwnerType(int ownerType) {
//        this.ownerType = ownerType;
//        return this;
//    }
}
