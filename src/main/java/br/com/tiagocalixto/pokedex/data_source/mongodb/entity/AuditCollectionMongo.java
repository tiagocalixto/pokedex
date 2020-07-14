package br.com.tiagocalixto.pokedex.data_source.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(collection = "audit")
public class AuditCollectionMongo {

    @Transient
    public static final String SEQUENCE_NAME = "audit_sequence";

    @Id
    private String id;
    private String entityName;
    private String idEntity;
    private String version;
    private LocalDateTime date;
    private Object entity;
}
