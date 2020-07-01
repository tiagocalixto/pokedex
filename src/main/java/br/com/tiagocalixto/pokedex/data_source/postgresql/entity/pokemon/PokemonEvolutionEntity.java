package br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.EvolutionStoneEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.EvolutionTriggerEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.GenericEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonEvolutionPk;
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
@SQLDelete(sql = "UPDATE pokemon_evolution SET deleted = true, " +
        "         audit_logical_delete_date = current_timestamp WHERE id_evolution_fk = ? and id_pokemon_fk = ?")
@Where(clause = "deleted != true")
public class PokemonEvolutionEntity extends GenericEntity {

    @EmbeddedId
    private PokemonEvolutionPk id;

    @OneToOne
    @MapsId("idPokemonFk")
    @JoinColumn(name = "id_pokemon_fk", referencedColumnName = "id", updatable = false)
    private PokemonEntity pokemon;

    @OneToOne
    @MapsId("idEvolutionFk")
    @JoinColumn(name = "id_evolution_fk", referencedColumnName = "id", updatable = false)
    private PokemonEntity evolution;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_trigger_fk", referencedColumnName = "id", nullable = false)
    private EvolutionTriggerEntity evolutionTrigger;

    @Column(name = "level")
    private Long level;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_stone_fk", referencedColumnName = "id")
    private EvolutionStoneEntity evolutionItem;
}
