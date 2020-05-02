package br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.GenericEntity;
import br.com.tiagocalixto.pokedex.data_source.sql.entity.embeddable_pk.PokemonEvolutionPk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pokemon_evolution")
@SQLDelete(sql = "UPDATE pokemon_evolution SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id = ?")
@Where(clause = "deleted != true")
public class PokemonEvolutionEntity extends GenericEntity {

    @EmbeddedId
    private PokemonEvolutionPk id;

    @OneToOne
    @MapsId("idPokemonFk")
    @JoinColumn(name = "id_pokemon_fk", referencedColumnName = "id", updatable = false, insertable = false)
    private PokemonEntity pokemon;

    @OneToOne
    @MapsId("idEvolutionFk")
    @JoinColumn(name = "id_evolution_fk", referencedColumnName = "id", updatable = false, insertable = false)
    private PokemonEntity evolution;

    @Column(name = "trigger")
    private String trigger;

    @Column(name = "level")
    private long level;

    @Column(name = "item")
    private String item;
}
