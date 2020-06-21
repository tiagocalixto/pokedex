package br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.AbilityEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.GenericEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonAbilityPk;
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
@Table(name = "pokemon_ability")
@SQLDelete(sql = "UPDATE pokemon_ability SET deleted = true, " +
        "         audit_logical_delete_date = current_timestamp WHERE id_ability_fk = ? and id_pokemon_fk = ?")
@Where(clause = "deleted != true")
public class PokemonAbilityEntity extends GenericEntity {

    @EmbeddedId
    private PokemonAbilityPk id;

    @ManyToOne
    @MapsId("idPokemonFk")
    @JoinColumn(name = "id_pokemon_fk", referencedColumnName = "id", updatable = false, insertable = false)
    private PokemonEntity pokemon;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("idAbilityFk")
    @JoinColumn(name = "id_ability_fk")
    private AbilityEntity ability;
}
