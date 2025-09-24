package com.mindmatch.pagamento.service;

import com.mindmatch.pagamento.dto.CartaoDTO;
import com.mindmatch.pagamento.entities.Cartao;
import com.mindmatch.pagamento.entities.Cliente;
import com.mindmatch.pagamento.repositories.CartaoRepository;
import com.mindmatch.pagamento.repositories.ClienteRepository;
import com.mindmatch.pagamento.service.exceptions.DatabaseException;
import com.mindmatch.pagamento.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<CartaoDTO> findAll() {
        return cartaoRepository.findAll()
                .stream().map(CartaoDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CartaoDTO findById(Long id) {
        return new CartaoDTO(cartaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. ID: "+ id)));
    }

    @Transactional(readOnly = true)
    public List<CartaoDTO> findByClienteId(Long id) {
        try {
            return cartaoRepository.findByClienteId(id)
                    .stream().map(CartaoDTO::new)
                    .toList();
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: "+ id);
        }
    }

    @Transactional
    public CartaoDTO create(CartaoDTO request) {
        Cartao entity = new Cartao();
        toEntity(entity, request);

        entity = cartaoRepository.save(entity);

        return new CartaoDTO(entity);
    }

    @Transactional
    public CartaoDTO update(Long id, CartaoDTO request) {
        Cartao entity;
        try {
            entity = cartaoRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: "+ id);
        }

        toEntity(entity, new CartaoDTO());
        entity = cartaoRepository.save(entity);

        return new CartaoDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if(!cartaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: "+ id);
        }

        try {
            cartaoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void toEntity(Cartao entity, CartaoDTO dto) {
        entity.setNumero(dto.getNumero());
        entity.setCvv(dto.getCvv());
        entity.setTipoCartao(dto.getTipoCartao());
        entity.setVencimento(dto.getVencimento());

        Cliente cliente = new Cliente();
        cliente.setId(dto.getClienteId());
        entity.setCliente(cliente);
    }
}
