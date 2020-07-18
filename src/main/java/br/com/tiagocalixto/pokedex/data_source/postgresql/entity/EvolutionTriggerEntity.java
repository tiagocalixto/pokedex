package br.com.tiagocalixto.pokedex.data_source.postgresql.entity;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonEvolutionEntity;
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
@Table(name = "evolution_trigger")
@SQLDelete(sql = "UPDATE evolution_trigger SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id = ?")
@Where(clause = "deleted != true")
public class EvolutionTriggerEntity extends GenericEntity {

    @Id
    @SequenceGenerator(name = "trigger_id_auto", sequenceName = "trigger_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trigger_id_auto")
    private Long id;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @OneToMany(mappedBy = "evolutionTrigger")
    private List<PokemonEvolutionEntity> pokemonEvolution;
}
