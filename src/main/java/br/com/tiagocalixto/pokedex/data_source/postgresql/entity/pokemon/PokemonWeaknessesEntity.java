package br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.GenericEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.TypeEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonTypePk;
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
@Table(name = "pokemon_weaknesses")
@SQLDelete(sql = "UPDATE pokemon_weaknesses SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id = ?")
@Where(clause = "deleted != true")
public class PokemonWeaknessesEntity extends GenericEntity {

    @EmbeddedId
    private PokemonTypePk id;

    @ManyToOne
    @MapsId("idPokemonFk")
    @JoinColumn(name = "id_pokemon_fk", referencedColumnName = "id", updatable = false, insertable = false)
    private PokemonEntity pokemon;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("idTypeFk")
    @JoinColumn(name = "id_type_fk")
    private TypeEntity type;
}
