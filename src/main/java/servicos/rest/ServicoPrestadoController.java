package servicos.rest;

import java.util.List;
import javax.validation.Valid;
import servicos.exception.RegraDeNegocioException;
import servicos.service.ServicoPrestadoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import servicos.model.entity.ServicoPrestado;
import servicos.rest.dto.ServicoPrestadoDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {

	private final ServicoPrestadoService servicoPrestadoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto) {
		try {
			return servicoPrestadoService.salvar(dto);
		} catch (RegraDeNegocioException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping
	public List<ServicoPrestado> pesquisar(
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "mes", required = false) String mes
			) {
		return servicoPrestadoService.pesquisar(nome, mes);
	}

	@GetMapping("{id}")
	public ServicoPrestado buscarPorId(@PathVariable Integer id) {
		return servicoPrestadoService
				.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado."));
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoPrestadoDTO dto) {
		try {
			servicoPrestadoService.atualizar(id, dto);
		} catch (RegraDeNegocioException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id) {
		try {
			servicoPrestadoService.excluir(id);
		} catch (RegraDeNegocioException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
