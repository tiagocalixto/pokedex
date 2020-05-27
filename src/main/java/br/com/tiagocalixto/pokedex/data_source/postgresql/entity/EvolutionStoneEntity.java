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
@Table(name = "evolution_stones")
@SQLDelete(sql = "UPDATE evolution_stones SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id = ?")
@Where(clause = "deleted != true")
public class EvolutionStoneEntity extends GenericEntity {

    @Id
    @SequenceGenerator(name = "stone_id_auto", sequenceName = "stone_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stone_id_auto")
    private long id;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @OneToMany(mappedBy = "evolutionItem")
    private List<PokemonEvolutionEntity> pokemonEvolution;
}
