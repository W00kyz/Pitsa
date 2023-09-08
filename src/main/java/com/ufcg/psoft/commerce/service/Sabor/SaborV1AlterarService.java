package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.Sabor.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborV1AlterarService implements SaborAlterarService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Sabor alterar(Long idSabor, Long idEstabelecimento, String codigoAcesso, SaborPostPutRequestDTO requestDTO) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(new ResourceNotFoundException("Estabelecimento não existe."));
        if (!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException("Codigo de acesso invalido!"); // Bad Request
        }
        Sabor saborAlterar = saborRepository.findById(idSabor)
                .orElseThrow(new ResourceNotFoundException("Sabor não encontrado."));

        if (!saborRepository.existsByIdAndEstabelecimentoId(idSabor, idEstabelecimento)) {
            throw new AssociationNotFoundException("Associação inválida!");
        }

        modelMapper.map(requestDTO, saborAlterar);
        saborAlterar.setEstabelecimento(estabelecimentoExistente);
        return saborRepository.save(saborAlterar);
    }
}
