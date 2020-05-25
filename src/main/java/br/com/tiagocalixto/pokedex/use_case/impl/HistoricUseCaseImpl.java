package br.com.tiagocalixto.pokedex.use_case.impl;

import br.com.tiagocalixto.pokedex.ports.data_base_port.InsertRepositoryPort;
import br.com.tiagocalixto.pokedex.domain.Historic;
import br.com.tiagocalixto.pokedex.use_case.SaveUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("HistoricUseCaseImpl")
public class HistoricUseCaseImpl implements SaveUseCase<Historic> {

    private InsertRepositoryPort<Historic> insertRepository;

    @Autowired
    public HistoricUseCaseImpl(@Qualifier("historicRepositorySql") InsertRepositoryPort<Historic> insertRepository){

        this.insertRepository = insertRepository;
    }


    @Override
    public Historic save(Historic historic) {

       return insertRepository.insert(historic);
    }
}
