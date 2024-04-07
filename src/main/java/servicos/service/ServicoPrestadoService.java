package servicos.service;

import servicos.exception.RegraDeNegocioException;
import servicos.model.entity.Cliente;
import servicos.model.entity.ServicoPrestado;
import servicos.model.repository.ClienteRepository;
import servicos.model.repository.ServicoPrestadoRepository;
import servicos.rest.dto.ServicoPrestadoDTO;
import servicos.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicoPrestadoService {

    private final ClienteRepository clienteRepository;

    private final BigDecimalConverter bigDecimalConverter;
    private final ServicoPrestadoRepository servicoPrestadoRepository;

    @Transactional
    public ServicoPrestado salvar(ServicoPrestadoDTO dto) {

        LocalDateTime data = LocalDateTime.parse(dto.getData(), DateTimeFormatter.ISO_DATE_TIME);
        Integer idCliente = dto.getIdCliente();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente inexistente."));

        ServicoPrestado servicoPrestado =  new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setData(data.toLocalDate());
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor(bigDecimalConverter.converter(dto.getValor()));

        return servicoPrestadoRepository.save(servicoPrestado);
    }

    public List<ServicoPrestado> pesquisar(String nome, String mes) {

        if (!nome.equals("") && !nome.equals("undefined") && !mes.equals("") && !mes.equals("undefined")) {
            return servicoPrestadoRepository.findByNomeClienteAndData("%" + nome + "%", Integer.parseInt(mes));
        }
        if (!nome.equals("") && !nome.equals("undefined")) {
            return servicoPrestadoRepository.findByNomeCliente("%" + nome + "%");
        }
        if (!mes.equals("") && !mes.equals("undefined")) {
            return servicoPrestadoRepository.findByMes(Integer.parseInt(mes));
        }

        return servicoPrestadoRepository.findAll(Sort.by(Sort.Direction.DESC,"data"));
    }

    public Optional<ServicoPrestado> buscarPorId(Integer id) {
        return servicoPrestadoRepository.findById(id);
    }

    public void atualizar(Integer id, ServicoPrestadoDTO dto) {

        LocalDate data = parsDate(dto.getData());
        Integer idCliente = dto.getIdCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente inexistente."));

        servicoPrestadoRepository
                .findById(id)
                .map(servico -> {
                    servico.setCliente(cliente);
                    servico.setDescricao(dto.getDescricao());
                    servico.setData(data);
                    servico.setValor(new BigDecimal(dto.getValor()));
                    return servicoPrestadoRepository.save(servico);
                })
                .orElseThrow(() -> new RegraDeNegocioException("Serviço prestado não encontrado."));
    }

    public void excluir(Integer id) {
        servicoPrestadoRepository
                .findById(id)
                .map(servico -> {
                    servicoPrestadoRepository.delete(servico);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new RegraDeNegocioException("Serviço prestado não encontrado"));
    }

    public LocalDate parsDate(String dataString) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dataString, formatter);
        } catch (DateTimeParseException ex) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                return LocalDate.parse(dataString, formatter);
            } catch (DateTimeParseException ex2) {
                throw  new IllegalArgumentException("Formato de data invalido: " + dataString);
            }
        }
    }
}
