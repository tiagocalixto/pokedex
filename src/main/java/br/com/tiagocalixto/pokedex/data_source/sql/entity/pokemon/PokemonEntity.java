package br.com.tiagocalixto.pokedex.data_source.sql.entity.pokemon;

import br.com.tiagocalixto.pokedex.data_source.sql.entity.GenericEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pokemon")
@SQLDelete(sql = "UPDATE pokemon SET deleted = true, audit_logical_delete_date = current_timestamp WHERE id = ?")
@Where(clause = "deleted != true")
public class PokemonEntity extends GenericEntity {

    @Id
    @SequenceGenerator(name = "pokemon_id_auto", sequenceName = "pokemon_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pokemon_id_auto")
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "number", nullable = false, length = 4)
    private String number;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "height")
    private BigDecimal height;

    @Column(name = "about", nullable = false, length = 500)
    private String about;

    @Column(name = "url_picture", nullable = false)
    private String urlPicture;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pokemon", orphanRemoval = true)
    private PokemonStatsEntity stats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemon", orphanRemoval = true)
    private List<PokemonTypeEntity> type;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pokemon", orphanRemoval = true)
    private PokemonEvolutionEntity evolveTo;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "evolution", orphanRemoval = true)
    private PokemonEvolutionEntity evolvedFrom;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemon", orphanRemoval = true)
    private List<PokemonMoveEntity> moves;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemon", orphanRemoval = true)
    private List<PokemonAbilityEntity> abilities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemon", orphanRemoval = true)
    private List<PokemonWeaknessesEntity> weaknesses;
}
