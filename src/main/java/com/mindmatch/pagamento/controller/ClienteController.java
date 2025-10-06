package com.mindmatch.pagamento.controller;

import com.mindmatch.pagamento.dto.ClienteDTO;
import com.mindmatch.pagamento.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/clientes")
@CrossOrigin(origins = "*")
@Tag(name = "Cliente", description = "Controller para cliente")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(
            description = "Listar Clientes",
            summary = "Retorna uma lista com todos os Clientes registrados.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> res = clienteService.findAll();
        return ResponseEntity.ok(res);
    }

    @Operation(
            description = "Busca cliente por Id",
            summary = "Consulta um cliente por Id.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @GetMapping(path = "/id/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ClienteDTO> findById(@PathVariable Long id) {
        ClienteDTO res = clienteService.findById(id);
        return ResponseEntity.ok(res);
    }

    @Operation(
            description = "Busca cliente por E-mail",
            summary = "Consulta um cliente por E-mail.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @GetMapping(path = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ClienteDTO> findByEmail(@PathVariable String email) {
        ClienteDTO res = clienteService.findByEmail(email);
        return ResponseEntity.ok(res);
    }

    @Operation(
            description = "Busca cliente por Telefone",
            summary = "Consulta um cliente por Telefone",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @GetMapping(path = "/telefone/{telefone}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ClienteDTO> findByTelefone(@PathVariable String telefone) {
        ClienteDTO res = clienteService.findByTelefone(telefone);
        return ResponseEntity.ok(res);
    }

    @Operation(
            description = "Cria um novo Cliente",
            summary = "Salva um novo Cliente.",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClienteDTO request){
        request = clienteService.create(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id/{id:[0-9]+}")
                .buildAndExpand(request.getId())
                .toUri();

        return ResponseEntity.created(uri).body(request);
    }

    @Operation(
            description = "Atualiza um Cliente apartir do identificador (id)",
            summary = "Atualiza o Cliente pelo id.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @PutMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> update( @PathVariable Long id, @RequestBody @Valid ClienteDTO request){
        request = clienteService.update(id, request);
        return ResponseEntity.ok(request);
    }

    @Operation(
            description = "Apaga um Cliente apartir do identificador (id)",
            summary = "Deleta o Cliente pelo id.",
            responses = {
                    @ApiResponse(description = "No Contert", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @DeleteMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
