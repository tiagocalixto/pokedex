package br.com.tiagocalixto.pokedex.external_api.national_dex.client.impl;

import br.com.tiagocalixto.pokedex.external_api.national_dex.client.EvolutionChainClient;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.evolution_chain.EvolveToNationalDexDto;
import br.com.tiagocalixto.pokedex.external_api.national_dex.dto.evolution_chain.EvolutionChainNationalDexDto;
import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.ChainLink;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EvolutionChainClientImpl implements EvolutionChainClient {

    private me.sargunvohra.lib.pokekotlin.model.EvolutionChain chain;
    private PokeApi pokeApi;

    public EvolutionChainClientImpl(PokeApi pokeApi) {

        this.pokeApi = pokeApi;
    }


    @Override
    public void getEvolutionChainFromNationalDataBase(Integer idEvolutionChain) {

        this.chain = pokeApi.getEvolutionChain(idEvolutionChain);
    }

    @Override
    public Optional<EvolutionChainNationalDexDto> getEvolutions(Long number) {

        if (this.chain == null)
            return Optional.empty();

        List<EvolutionChainNationalDexDto> allPokemonOnChain = new ArrayList<>();
        allPokemonOnChain.add(getFirstPokemonOfChain());
        List<ChainLink> nextLevelChain = chain.getChain().getEvolvesTo();

        while (!nextLevelChain.isEmpty()) {

            List<EvolutionChainNationalDexDto> listNextEvolveTo = new ArrayList<>();
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

    private EvolutionChainNationalDexDto getFirstPokemonOfChain() {

        return EvolutionChainNationalDexDto.builder()
                .name(chain.getChain().getSpecies().getName())
                .number(Long.valueOf(chain.getChain().getSpecies().getId()))
                .idChain(Long.valueOf(chain.getId()))
                .evolveTo(getListEvolveTo(chain.getChain().getEvolvesTo()))
                .build();
    }

    private EvolutionChainNationalDexDto getNextPokemonOfChain(ChainLink evolveTo) {

        return EvolutionChainNationalDexDto.builder()
                .name(evolveTo.getSpecies().getName())
                .number(Long.valueOf(evolveTo.getSpecies().getId()))
                .idChain(Long.valueOf(chain.getId()))
                .evolveTo(getListEvolveTo(evolveTo.getEvolvesTo()))
                .build();
    }

    private List<EvolveToNationalDexDto> getListEvolveTo(List<ChainLink> evolveTo) {

        return evolveTo.stream()
                .map(item -> EvolveToNationalDexDto.builder()
                        .name(item.getSpecies().getName())
                        .number(Long.valueOf(item.getSpecies().getId()))
                        .build())
                .collect(Collectors.toList());
    }

    private EvolutionChainNationalDexDto getEvolutionByNumber(List<EvolutionChainNationalDexDto> evolveToList, Long number) {

        return evolveToList.stream()
                .filter(item -> item.getNumber().equals(number))
                .findFirst().orElse(null);
    }
}
