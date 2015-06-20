package com.tempest.moonlight.server.services.dto.contacts;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.services.dto.BiDirectionalDTO;

/**
 * Created by Yurii on 2015-06-20.
 */
public class ContactRequestDTO implements BiDirectionalDTO<ContactRequest> {

    private String initiator;
    private String recipient;

    private int contactType;
    private String contactSignature;

    private int state;
    private long time;

    public ContactRequestDTO() {
    }

    public ContactRequestDTO(String recipient, int contactType, String contactSignature) {
        this.recipient = recipient;
        this.contactType = contactType;
        this.contactSignature = contactSignature;
    }

    public ContactRequestDTO(String recipient, ParticipantType contactType, String contactSignature) {
        this(recipient, contactType.getValue(), contactSignature);
    }

    public ContactRequestDTO(String initiator, String recipient, int contactType, String contactSignature, int state, long time) {
        this.initiator = initiator;
        this.recipient = recipient;
        this.contactType = contactType;
        this.contactSignature = contactSignature;
        this.state = state;
        this.time = time;
    }

    public ContactRequestDTO(String initiator, String recipient, ParticipantType contactType, String contactSignature, ContactRequest.Status status, long time) {
        this(initiator, recipient, contactType.getValue(), contactSignature, status.getValue(), time);
    }

    @Override
    public void fillEntity(ContactRequest contactRequest) {
        contactRequest.setRecipient(recipient);
        contactRequest.setContact(new GenericParticipant(ParticipantType.getByValue(contactType), contactSignature));
    }

    @Override
    public void fillWithEntity(ContactRequest contactRequest) {
        initiator = contactRequest.getInitiator();
        recipient = contactRequest.getRecipient();
        contactType = contactRequest.getContact().getType().getValue();
        contactSignature = contactRequest.getContact().getSignature();
        state = contactRequest.getStatus().getValue();
        time = contactRequest.getTime();
    }

    public String getInitiator() {
        return initiator;
    }

    public ContactRequestDTO setInitiator(String initiator) {
        this.initiator = initiator;
        return this;
    }

    public String getRecipient() {
        return recipient;
    }

    public ContactRequestDTO setRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public int getContactType() {
        return contactType;
    }

    public ContactRequestDTO setContactType(int contactType) {
        this.contactType = contactType;
        return this;
    }

    public String getContactSignature() {
        return contactSignature;
    }

    public ContactRequestDTO setContactSignature(String contactSignature) {
        this.contactSignature = contactSignature;
        return this;
    }

    public int getState() {
        return state;
    }

    public ContactRequestDTO setState(int state) {
        this.state = state;
        return this;
    }

    public long getTime() {
        return time;
    }

    public ContactRequestDTO setTime(long time) {
        this.time = time;
        return this;
    }
}
