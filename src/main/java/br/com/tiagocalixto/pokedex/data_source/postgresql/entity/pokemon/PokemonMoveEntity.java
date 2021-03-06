package br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon;


import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.GenericEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.MoveEntity;
import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.embeddable_pk.PokemonMovePk;
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
@Table(name = "pokemon_moves")
@SQLDelete(sql = "UPDATE pokemon_moves SET deleted = true, " +
        "         audit_logical_delete_date = current_timestamp WHERE id_move_fk = ? and id_pokemon_fk = ?")
@Where(clause = "deleted != true")
public class PokemonMoveEntity extends GenericEntity {

    @EmbeddedId
    private PokemonMovePk id;

    @ManyToOne
    @MapsId("idPokemonFk")
    @JoinColumn(name = "id_pokemon_fk", referencedColumnName = "id", updatable = false, insertable = false)
    private PokemonEntity pokemon;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("idMoveFk")
    @JoinColumn(name = "id_move_fk")
    private MoveEntity move;

    @Column(name = "level_learn")
    private long levelLearn;
}
