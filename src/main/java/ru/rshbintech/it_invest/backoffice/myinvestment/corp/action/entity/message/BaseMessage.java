package ru.rshbintech.it_invest.backoffice.myinvestment.corp.action.entity.message;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "message")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "message_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseMessage {

    @Id
    @Column(name = "id", length = 36)
    private UUID id = UUID.randomUUID();

    @Column(name = "caid", nullable = true)
    private Long caid = null; // Значение по умолчанию

    @Column(name = "json", columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String json; // Хранение JSON как строки

    @Column(name = "status", nullable = false)
    private Long status = 0L;

    @CreationTimestamp
    @Column(name = "create_date_time", nullable = false, updatable = false)
    private ZonedDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "update_date_time", nullable = false)
    private ZonedDateTime updateDateTime;

    public abstract String getMessageType();
}
