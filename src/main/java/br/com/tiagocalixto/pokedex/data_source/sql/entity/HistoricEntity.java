package br.com.tiagocalixto.pokedex.data_source.sql.entity;

import br.com.tiagocalixto.pokedex.infra.util.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historic")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class HistoricEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "historic_id_auto", sequenceName = "historic_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historic_id_auto")
    private long id;

    @Column(name = "id_entity", nullable = false)
    private long idEntity;

    @SuppressWarnings("squid:S1948")
    @Type(type = "jsonb")
    @Column(name = "entity_sql", nullable = false)
    private JsonNode entity;

    @Column(name = "version")
    private long version;

    @Column(name="action")
    private String action;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "update_date", nullable = false)
    private java.sql.Timestamp updateDate;


    @PrePersist
    public void prePersist() {

        this.updateDate = new java.sql.Timestamp(Util.getCurrentTimeStamp().getTime());
    }
}