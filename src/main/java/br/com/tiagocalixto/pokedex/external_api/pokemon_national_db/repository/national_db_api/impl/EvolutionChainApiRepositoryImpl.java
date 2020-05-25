package br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.repository.national_db_api.impl;

import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.evolution_chain.EvolutionChainEvolveToNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.entity.evolution_chain.EvolutionChainNationalDb;
import br.com.tiagocalixto.pokedex.external_api.pokemon_national_db.repository.national_db_api.EvolutionChainApiRepository;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.ChainLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EvolutionChainApiRepositoryImpl implements EvolutionChainApiRepository {

    private me.sargunvohra.lib.pokekotlin.model.EvolutionChain chain;
    private PokeApi pokeApi;

    public EvolutionChainApiRepositoryImpl(PokeApi pokeApi) {

        this.pokeApi = pokeApi;
    }


    @Override
    public void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain) {

        this.chain = pokeApi.getEvolutionChain(idEvolutionChain);
    }

    @Override
    public Optional<EvolutionChainNationalDb> getEvolutions(Long number) {

        if (this.chain == null)
            return Optional.empty();

        List<EvolutionChainNationalDb> allPokemonOnChain = new ArrayList<>();
        allPokemonOnChain.add(getFirstPokemonOfChain());
        List<ChainLink> nextLevelChain = chain.getChain().getEvolvesTo();

        while (!nextLevelChain.isEmpty()) {

            List<EvolutionChainNationalDb> listNextEvolveTo = new ArrayList<>();
            List<ChainLink> nextLevelSecondary = new ArrayList<>();

            nextLevelChain.forEach(item -> {
                listNextEvolveTo.add(getNextPokemonOfChain(item));
                nextLevelSecondary.addAll(item.getEvolvesTo());
            });

            allPokemonOnChain.addAll(listNextEvolveTo);
            nextLevelChain = nextLevelSecondary;
        }

        return Optional.of(getEvolutionByNumber(allPokemonOnChain, number));
    }

    private EvolutionChainNationalDb getFirstPokemonOfChain() {

        return EvolutionChainNationalDb.builder()
                .name(chain.getChain().getSpecies().getName())
                .number(Long.valueOf(chain.getChain().getSpecies().getId()))
                .idChain(Long.valueOf(chain.getId()))
                .evolveTo(getListEvolveTo(chain.getChain().getEvolvesTo()))
                .build();
    }

    private EvolutionChainNationalDb getNextPokemonOfChain(ChainLink evolveTo) {

        return EvolutionChainNationalDb.builder()
                .name(evolveTo.getSpecies().getName())
                .number(Long.valueOf(evolveTo.getSpecies().getId()))
                .idChain(Long.valueOf(chain.getId()))
                .evolveTo(getListEvolveTo(evolveTo.getEvolvesTo()))
                .build();
    }

    private List<EvolutionChainEvolveToNationalDb> getListEvolveTo(List<ChainLink> evolveTo) {

        return evolveTo.stream()
                .map(item -> EvolutionChainEvolveToNationalDb.builder()
                        .name(item.getSpecies().getName())
                        .number(Long.valueOf(item.getSpecies().getId()))
                        .build())
                .collect(Collectors.toList());
    }

    private EvolutionChainNationalDb getEvolutionByNumber(List<EvolutionChainNationalDb> evolveToList, Long number) {

        return evolveToList.stream()
                .filter(item -> item.getNumber().equals(number))
                .findFirst().orElse(null);
    }
}
