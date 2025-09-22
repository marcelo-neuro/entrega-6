package com.mindmatch.pagamento.service;

import com.mindmatch.pagamento.dto.ClienteDTO;
import com.mindmatch.pagamento.entities.Cliente;
import com.mindmatch.pagamento.repositories.ClienteRepository;
import com.mindmatch.pagamento.service.exceptions.DatabaseException;
import com.mindmatch.pagamento.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        return  clienteRepository.findAll()
                .stream().map(ClienteDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        return new ClienteDTO(clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. ID: "+ id)));
    }

    @Transactional(readOnly = true)
    public ClienteDTO findByEmail(String email) {
        return new ClienteDTO(clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. Email: "+ email)));
    }

    @Transactional(readOnly = true)
    public ClienteDTO findByTelefone(String telefone) {
        return new ClienteDTO(clienteRepository.findByTelefone(telefone)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado. Telefone: "+ telefone)));
    }

    @Transactional
    public ClienteDTO create(ClienteDTO request) {
        Cliente entity = new Cliente();
        toEntity(entity, request);
        entity.setValorMedioCompra(0.0);
        entity = clienteRepository.save(entity);

        return new ClienteDTO(entity);
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteDTO request) {
        Cliente entity;
        try {
            entity = clienteRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: "+ id);
        }

        toEntity(entity, request);
        entity = clienteRepository.save(entity);

        return new ClienteDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if(!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: "+ id);
        }

        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void toEntity(Cliente entity ,ClienteDTO dto) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setValorMedioCompra(dto.getValorMedioCompra());
    }
}
