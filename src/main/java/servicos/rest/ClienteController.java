package servicos.rest;

import java.util.List;

import javax.validation.Valid;

import servicos.exception.ExcluirClienteException;
import servicos.exception.RegraDeNegocioException;
import servicos.rest.dto.ClienteDTO;
import servicos.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import servicos.model.entity.Cliente;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
	private final ClienteService service;
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid ClienteDTO dto) {
		return service.salvar(dto);
	}
	
	@GetMapping
	public List<Cliente> pesquisar(
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf){
		return service.pesquisar(nome, cpf);
	}
	
	@GetMapping("{id}")
	public Cliente buscarPorId(@PathVariable Integer id) {
		return service
				.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		try {
			service.deletar(id);
		} catch (RegraDeNegocioException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (ExcluirClienteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid ClienteDTO dto) {
		try {
			service.atualizar(id, dto);
		} catch (RegraDeNegocioException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	
	
	
	
	
	
	

}
