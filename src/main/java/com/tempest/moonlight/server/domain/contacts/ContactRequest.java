package com.tempest.moonlight.server.domain.contacts;

import com.tempest.moonlight.server.annotations.DTO;
import com.tempest.moonlight.server.domain.HasIntValue;
import com.tempest.moonlight.server.repository.dao.IdentifiedEntity;
import com.tempest.moonlight.server.services.dto.EntityDTO;
import com.tempest.moonlight.server.services.dto.contacts.ContactRequestDTO;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yurii on 2015-06-20.
 * <br>
 * If User A invites User B, than request fields would be:
 * <ul>
 * <li>{@link #initiator} = A</li>
 * <li>{@link #recipient} = B</li>
 * <li>{@link #contact} = {USER, A}</li>
 * </ul>
 * If User A invites User B into Group C, than request fields would be:
 * <ul>
 * <li>{@link #initiator} = A</li>
 * <li>{@link #recipient} = B</li>
 * <li>{@link #contact} = {GROUP, C}</li>
 * </ul>
 */
@Component
@DTO(dto = ContactRequestDTO.class, type = EntityDTO.DtoType.BiDir)
public class ContactRequest implements IdentifiedEntity<ContactRequest>, Serializable {

    public enum Status implements HasIntValue {
        PENDING(0),
        APPROVED(1),
        REJECTED(2),
        REVOKED(3)
        ;

        public final int value;

        Status(int value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        private static final Map<Integer, Status> MAP = new HashMap<>();

        static {
            for (Status status : values()) {
                MAP.put(status.value, status);
            }
        }

        public static Status getByValue(int value) {
            return MAP.get(value);
        }
    }

    /**
     * User, who sends request
     */
    private String initiator;
    /**
     * User, who receives request
     */
    private String recipient;

    /**
     * New contact, to be added to recipient's contacts
     */
    private GenericParticipant contact;

    private Status status;

    private long time;

    public ContactRequest() {
    }

    public ContactRequest(String initiator, String recipient, GenericParticipant contact) {
        this.initiator = initiator;
        this.recipient = recipient;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ContactRequest{" +
                "initiator='" + initiator + '\'' +
                ", recipient='" + recipient + '\'' +
                ", contact=" + contact +
                ", status=" + status +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactRequest that = (ContactRequest) o;

        if (initiator != null ? !initiator.equals(that.initiator) : that.initiator != null) return false;
        if (recipient != null ? !recipient.equals(that.recipient) : that.recipient != null) return false;
        return !(contact != null ? !contact.equals(that.contact) : that.contact != null);

    }

    @Override
    public int hashCode() {
        int result = initiator != null ? initiator.hashCode() : 0;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        return result;
    }

    @Override
    public ContactRequest getKey() {
        return this;
    }

    public String getInitiator() {
        return initiator;
    }

    public ContactRequest setInitiator(String initiator) {
        this.initiator = initiator;
        return this;
    }

    public String getRecipient() {
        return recipient;
    }

    public ContactRequest setRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public GenericParticipant getContact() {
        return contact;
    }

    public ContactRequest setContact(GenericParticipant contact) {
        this.contact = contact;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public ContactRequest setStatus(Status status) {
        this.status = status;
        return this;
    }

    public long getTime() {
        return time;
    }

    public ContactRequest setTime(long time) {
        this.time = time;
        return this;
    }
}
