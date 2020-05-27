package br.com.tiagocalixto.pokedex.data_source.postgresql.entity;


import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon.PokemonAbilityEntity;
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
@Table(name = "abilities")
@SQLDelete(sql = "UPDATE abilities SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id = ?")
@Where(clause = "deleted != true")
public class AbilityEntity extends GenericEntity {

    @Id
    @SequenceGenerator(name = "ability_id_auto", sequenceName = "ability_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ability_id_auto")
    private long id;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @OneToMany(mappedBy = "id.idAbilityFk")
    private List<PokemonAbilityEntity> pokemonAbility;
}
