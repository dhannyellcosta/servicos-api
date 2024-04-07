package servicos.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicos.model.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

    List<Cliente> findByCpf(String cpf);

    @Query("select c from Cliente c where upper(c.nome) like upper(:nome)")
    List<Cliente> findByNome(@Param("nome") String nome);
}
