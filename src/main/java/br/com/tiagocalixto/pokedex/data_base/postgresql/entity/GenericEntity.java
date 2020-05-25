package br.com.tiagocalixto.pokedex.data_base.postgresql.entity;

import br.com.tiagocalixto.pokedex.infra.util.Util;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;


@MappedSuperclass
public class GenericEntity implements Serializable {

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "audit_creation_date", nullable = false, updatable = false)
    private java.sql.Timestamp creationDate;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "audit_last_update_date", insertable = false)
    private java.sql.Timestamp updateDate;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "audit_logical_delete_date", insertable = false)
    private java.sql.Timestamp deleteDate;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;


    @PrePersist
    public void prePersist() {

        this.creationDate = new java.sql.Timestamp(Util.getCurrentTimeStamp().getTime());
        deleted = false;
    }

    @PreUpdate
    public void preUpdate() {

        this.updateDate = new java.sql.Timestamp(Util.getCurrentTimeStamp().getTime());
        deleted = false;
    }
}
