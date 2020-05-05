package br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.repository.impl;

import br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.entity.evolution_chain.EvolutionChainEvolveToApi;
import br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.entity.evolution_chain.EvolutionChainPokemonApi;
import br.com.tiagocalixto.pokedex.data_source.national_pokemon_data_base.repository.EvolutionChainRepository;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.ChainLink;
import me.sargunvohra.lib.pokekotlin.model.EvolutionChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class EvolutionChainRepositoryImpl implements EvolutionChainRepository {

    private EvolutionChain chain;
    private PokeApi pokeApi = new PokeApiClient();

    @Override
    public void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain) {

        this.chain = pokeApi.getEvolutionChain(idEvolutionChain);
    }

    @Override
    public EvolutionChainPokemonApi getEvolutions(Long number) {

        List<EvolutionChainPokemonApi> allPokemonOnChain = new ArrayList<>();
        allPokemonOnChain.add(getFirstPokemonOfChain());
        List<ChainLink> nextLevelChain = chain.getChain().getEvolvesTo();

        while (!nextLevelChain.isEmpty()) {

            List<EvolutionChainPokemonApi> listNextEvolveTo = new ArrayList<>();
            List<ChainLink> nextLevelSecondary = new ArrayList<>();

            nextLevelChain.forEach(item -> {
                listNextEvolveTo.add(getNextPokemonOfChain(item));
                nextLevelSecondary.addAll(item.getEvolvesTo());
            });

            allPokemonOnChain.addAll(listNextEvolveTo);
            nextLevelChain = nextLevelSecondary;
        }

        return getEvolutionByNumber(allPokemonOnChain, number);
    }

    private EvolutionChainPokemonApi getFirstPokemonOfChain() {

        return EvolutionChainPokemonApi.builder()
                .name(chain.getChain().getSpecies().getName())
                .number(Long.valueOf(chain.getChain().getSpecies().getId()))
                .idChain(Long.valueOf(chain.getId()))
                .evolveTo(getListEvolveTo(chain.getChain().getEvolvesTo()))
                .build();
    }

    private EvolutionChainPokemonApi getNextPokemonOfChain(ChainLink evolveTo) {

        return EvolutionChainPokemonApi.builder()
                .name(evolveTo.getSpecies().getName())
                .number(Long.valueOf(evolveTo.getSpecies().getId()))
                .idChain(Long.valueOf(chain.getId()))
                .evolveTo(getListEvolveTo(evolveTo.getEvolvesTo()))
                .build();
    }

    private List<EvolutionChainEvolveToApi> getListEvolveTo(List<ChainLink> evolveTo) {

        return evolveTo.stream()
                .map(item -> EvolutionChainEvolveToApi.builder()
                        .name(item.getSpecies().getName())
                        .number(Long.valueOf(item.getSpecies().getId()))
                        .build())
                .collect(Collectors.toList());
    }

    private EvolutionChainPokemonApi getEvolutionByNumber(List<EvolutionChainPokemonApi> evolveToList, Long number) {

        return evolveToList.stream()
                .filter(item -> item.getNumber().equals(number))
                .findFirst().orElse(null);
    }
}
