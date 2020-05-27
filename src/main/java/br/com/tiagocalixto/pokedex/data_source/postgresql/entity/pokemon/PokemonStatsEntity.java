package br.com.tiagocalixto.pokedex.data_source.postgresql.entity.pokemon;

import br.com.tiagocalixto.pokedex.data_source.postgresql.entity.GenericEntity;
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
@Table(name = "pokemon_stats")
@SQLDelete(sql = "UPDATE pokemon_stats SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id_pokemon_fk = ?")
@Where(clause = "deleted != true")
public class PokemonStatsEntity extends GenericEntity {

    @Id
    @Column(name = "id_pokemon_fk")
    private long id;

    @OneToOne
    @JoinColumn(name = "id_pokemon_fk", referencedColumnName = "id", insertable = false, updatable = false)
    @MapsId
    private PokemonEntity pokemon;

    @Column(name = "hp")
    private long hp;

    @Column(name = "attack")
    private long attack;

    @Column(name = "defense")
    private long defense;

    @Column(name = "special_attack")
    private long specialAttack;

    @Column(name = "special_defense")
    private long specialDefense;

    @Column(name = "speed")
    private long speed;
}
