package com.ufcg.psoft.commerce.service.Associacao;

import com.ufcg.psoft.commerce.model.Associacao;

@FunctionalInterface
public interface AssociacaoAlterarService {
    public Associacao alterar(Long entregadorId, Long estabelecimentoId, String codigoAcesso);
}
