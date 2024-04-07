package servicos.service;

import servicos.exception.ExcluirClienteException;
import servicos.exception.RegraDeNegocioException;
import servicos.model.entity.Cliente;
import servicos.model.repository.ClienteRepository;
import servicos.model.repository.ServicoPrestadoRepository;
import servicos.rest.dto.ClienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    private final ServicoPrestadoRepository servicoPrestadoRepository;

    @Transactional
    public Cliente salvar(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        return repository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public void deletar(Integer id) {

        if(!servicoPrestadoRepository.findByCliente(id).isEmpty()){
            throw new ExcluirClienteException("O cliente não pode ser removido, pois possui serviços");
        }

        repository
                .findById(id)
                .map(cliente -> {
                        repository.delete(cliente);
                        return Void.TYPE;
                })
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    @Transactional
    public void atualizar(Integer id, ClienteDTO dto) {
        repository
                .findById(id)
                .map(cliente -> {
                    cliente.setNome(dto.getNome());
                    cliente.setCpf(dto.getCpf());
                    return repository.save(cliente);
                })
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public List<Cliente> pesquisar(String nome, String cpf) {

        if (!nome.equals("") && !nome.equals("undefined")) {
            return repository.findByNome("%" + nome + "%");
        }
        if(!cpf.equals("") && !cpf.equals("undefined")) {
            return repository.findByCpf(cpf);
        }

        return repository.findAll(Sort.by("id"));
    }
}
