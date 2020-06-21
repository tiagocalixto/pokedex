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
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pokemon_fk", referencedColumnName = "id", insertable = false, updatable = false)
    @MapsId
    private PokemonEntity pokemon;

    @Column(name = "hp")
    private Long hp;

    @Column(name = "attack")
    private Long attack;

    @Column(name = "defense")
    private Long defense;

    @Column(name = "special_attack")
    private Long specialAttack;

    @Column(name = "special_defense")
    private Long specialDefense;

    @Column(name = "speed")
    private Long speed;
}
