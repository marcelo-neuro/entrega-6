package com.mindmatch.pagamento.controller;
import com.mindmatch.pagamento.dto.CartaoDTO;
import com.mindmatch.pagamento.service.CartaoService;
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
@RequestMapping(path = "/cartoes")
@CrossOrigin(origins = "*")
@Tag(name = "Cartões", description = "Controller para cartões dos clientes")
public class CartaoController {

    private final CartaoService cartaoService;

    @Operation(
            description = "Listar Cartões",
            summary = "Retorna uma lista com todos os Cartões registrados.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartaoDTO>> findAll() {
        List<CartaoDTO> res = cartaoService.findAll();
        return ResponseEntity.ok(res);
    }

    @Operation(
            description = "Busca cartão por Id",
            summary = "Consulta um cartão por Id.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @GetMapping(path = "/id/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <CartaoDTO> findById(@PathVariable Long id) {
        CartaoDTO res = cartaoService.findById(id);
        return ResponseEntity.ok(res);
    }

    @Operation(
            description = "Busca cartões a partir do Id do cliente",
            summary = "Retorna todos os cartões de um cliente a partir do Id do cliente",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @GetMapping(path = "/clienteId/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<CartaoDTO>> findByClienteId(@PathVariable Long id) {
        List<CartaoDTO> res = cartaoService.findByClienteId(id);
        return ResponseEntity.ok(res);
    }

    @Operation(
            description = "Cria um novo Cartão",
            summary = "Salva um novo Cartão.",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartaoDTO> create(@RequestBody @Valid CartaoDTO request){
        request = cartaoService.create(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id/{id:[0-9]+}")
                .buildAndExpand(request.getId())
                .toUri();

        return ResponseEntity.created(uri).body(request);
    }

    @Operation(
            description = "Atualiza um Cartão apartir do identificador (id)",
            summary = "Atualiza o Cartão pelo id.",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @PutMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartaoDTO> update( @PathVariable Long id, @RequestBody @Valid CartaoDTO request){
        request = cartaoService.update(id, request);
        return ResponseEntity.ok(request);
    }

    @Operation(
            description = "Apaga um Cartão apartir do identificador (id)",
            summary = "Deleta o Cartão pelo id.",
            responses = {
                    @ApiResponse(description = "No Contert", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    @DeleteMapping(value = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cartaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
