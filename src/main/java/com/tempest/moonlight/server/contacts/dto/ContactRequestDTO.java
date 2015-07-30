package com.tempest.moonlight.server.contacts.dto;

import com.tempest.moonlight.server.domain.ParticipantType;
import com.tempest.moonlight.server.domain.contacts.ContactRequest;
import com.tempest.moonlight.server.domain.contacts.GenericParticipant;
import com.tempest.moonlight.server.common.dto.BiDirectionalDTO;

/**
 * Created by Yurii on 2015-06-20.
 */
public class ContactRequestDTO implements BiDirectionalDTO<ContactRequest> {

    private String initiator;
    private String recipient;

    private int type;
    private String signature;

    private int status;
    private long time;

    public ContactRequestDTO() {
    }

    public ContactRequestDTO(String recipient, int type, String signature) {
        this.recipient = recipient;
        this.type = type;
        this.signature = signature;
    }

    public ContactRequestDTO(String recipient, ParticipantType type, String signature) {
        this(recipient, type.getValue(), signature);
    }

    public ContactRequestDTO(String initiator, String recipient, int type, String signature, int status, long time) {
        this.initiator = initiator;
        this.recipient = recipient;
        this.type = type;
        this.signature = signature;
        this.status = status;
        this.time = time;
    }

    public ContactRequestDTO(String initiator, String recipient, ParticipantType type, String signature, ContactRequest.Status status, long time) {
        this(initiator, recipient, type.getValue(), signature, status.getValue(), time);
    }

    @Override
    public void fillEntity(ContactRequest contactRequest) {
        contactRequest.setInitiator(initiator);
        contactRequest.setRecipient(recipient);

        contactRequest.setContact(new GenericParticipant(ParticipantType.getByValue(type), signature));

        contactRequest.setStatus(ContactRequest.Status.getByValue(status));
    }

    @Override
    public void fillWithEntity(ContactRequest contactRequest) {
        initiator = contactRequest.getInitiator();
        recipient = contactRequest.getRecipient();
        type = contactRequest.getContact().getType().getValue();
        signature = contactRequest.getContact().getSignature();
        status = contactRequest.getStatus().getValue();
        time = contactRequest.getTime();
    }

    @Override
    public String toString() {
        return "ContactRequestDTO{" +
                "initiator='" + initiator + '\'' +
                ", recipient='" + recipient + '\'' +
                ", type=" + type +
                ", signature='" + signature + '\'' +
                ", state=" + status +
                ", time=" + time +
                '}';
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

    public int getType() {
        return type;
    }

    public ContactRequestDTO setType(int type) {
        this.type = type;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public ContactRequestDTO setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ContactRequestDTO setStatus(int status) {
        this.status = status;
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
