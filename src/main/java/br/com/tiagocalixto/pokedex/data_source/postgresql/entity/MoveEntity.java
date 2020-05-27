package br.com.tiagocalixto.pokedex.data_source.postgresql.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "moves")
@SQLDelete(sql = "UPDATE moves SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id = ?")
@Where(clause = "deleted != true")
public class MoveEntity extends GenericEntity {

    @Id
    @SequenceGenerator(name = "move_id_auto", sequenceName = "move_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "move_id_auto")
    private long id;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "pp", nullable = false)
    private long pp;

    @Column(name = "power", nullable = false)
    private long power;

    @Column(name = "accuracy", nullable = false)
    private BigDecimal accuracy;

    @Column(name = "about", nullable = false)
    private String about;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_type_fk", referencedColumnName = "id")
    private TypeEntity type;
}
