package com.tempest.moonlight.server.services.dto;

import com.tempest.moonlight.server.domain.contacts.GenericContact;

/**
 * Created by Yurii on 2015-06-16.
 */
public class GenericContactDTO implements ServerToClientDTO<GenericContact> {
    private int contactType;
    private String contactSignature;

    private int ownerType;
    private String ownerSignature;

    public GenericContactDTO() {
    }

    public GenericContactDTO(int contactType, String contactSignature, int ownerType, String ownerSignature) {
        this.contactType = contactType;
        this.contactSignature = contactSignature;
        this.ownerSignature = ownerSignature;
        this.ownerType = ownerType;
    }

    @Override
    public void fillWithEntity(GenericContact genericContact) {
        contactType =  genericContact.getContact().getType().getValue();
        contactSignature = genericContact.getContact().getSignature();
        ownerType = genericContact.getOwner().getType().getValue();
        ownerSignature = genericContact.getOwner().getSignature();
    }

    @Override
    public String toString() {
        return "GenericContactDTO{" +
                "contactType=" + contactType +
                ", contactSignature='" + contactSignature + '\'' +
                ", ownerSignature='" + ownerSignature + '\'' +
                ", ownerType=" + ownerType +
                '}';
    }

    public int getContactType() {
        return contactType;
    }

    public GenericContactDTO setContactType(int contactType) {
        this.contactType = contactType;
        return this;
    }

    public String getContactSignature() {
        return contactSignature;
    }

    public GenericContactDTO setContactSignature(String contactSignature) {
        this.contactSignature = contactSignature;
        return this;
    }

    public String getOwnerSignature() {
        return ownerSignature;
    }

    public GenericContactDTO setOwnerSignature(String ownerSignature) {
        this.ownerSignature = ownerSignature;
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
