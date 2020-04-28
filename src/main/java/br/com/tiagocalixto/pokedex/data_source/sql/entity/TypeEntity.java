package br.com.tiagocalixto.pokedex.data_source.sql.entity;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon.PokemonTypeEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon.PokemonWeaknessesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "types")
@SQLDelete(sql = "UPDATE types SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id = ?")
@Where(clause = "deleted != true")
public class TypeEntity extends GenericEntity {

    @Id
    @SequenceGenerator(name = "type_id_auto", sequenceName = "type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_id_auto")
    private long id;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @OneToMany(mappedBy = "type")
    private List<PokemonTypeEntity> pokemonType;
}
