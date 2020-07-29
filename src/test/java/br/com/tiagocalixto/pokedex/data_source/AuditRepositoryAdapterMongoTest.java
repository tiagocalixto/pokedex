package br.com.tiagocalixto.pokedex.data_source;

import br.com.tiagocalixto.pokedex.data_source.mongodb.adapter.AuditRepositoryAdapterMongo;
import br.com.tiagocalixto.pokedex.data_source.mongodb.entity.AuditCollectionMongo;
import br.com.tiagocalixto.pokedex.data_source.mongodb.repository.AuditRepositoryMongo;
import br.com.tiagocalixto.pokedex.data_source.mongodb.repository.SequenceGeneratorMongo;
import br.com.tiagocalixto.pokedex.domain.pokemon.Pokemon;
import br.com.tiagocalixto.pokedex.mock.MocksDomain;
import br.com.tiagocalixto.pokedex.mock.MocksEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuditRepositoryAdapterMongoTest {

    @InjectMocks
    private AuditRepositoryAdapterMongo adapter;

    @Mock
    private SequenceGeneratorMongo sequenceGenerator;

    @Mock
    private AuditRepositoryMongo repository;


    @Test
    @SneakyThrows
    public void givenPokemon_whenInsertAsyncAuditInMongo_thenOk() {

        AuditCollectionMongo audit = MocksEntity.createAuditCollection();
        Pokemon pokemon = MocksDomain.createPokemon(Long.valueOf(audit.getIdEntity()));

        when(sequenceGenerator.nextId(anyString())).thenReturn(audit.getVersion());
        when(repository.findFirstByEntityNameAndIdEntityOrderByVersionAsc(anyString(), anyString()))
                .thenReturn(Optional.of(audit));
        when(repository.save(any(AuditCollectionMongo.class))).thenReturn(audit);

        adapter.insertAsync(pokemon);

        verify(sequenceGenerator,times(1)).nextId(anyString());
        verify(repository,times(1))
                .findFirstByEntityNameAndIdEntityOrderByVersionAsc(anyString(), anyString());
        verify(repository,times(1)).save(any(AuditCollectionMongo.class));
    }
}
