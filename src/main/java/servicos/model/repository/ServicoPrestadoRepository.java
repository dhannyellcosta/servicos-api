package servicos.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import servicos.model.entity.ServicoPrestado;

public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {

	@Query("select s from ServicoPrestado s join s.cliente c where upper(c.nome) like upper(:nome) and MONTH(s.data)= :mes")
	List<ServicoPrestado> findByNomeClienteAndData(@Param("nome") String nome, @Param("mes") Integer mes);

	@Query("select s from ServicoPrestado s join s.cliente c where upper(c.nome) like upper(:nome)")
	List<ServicoPrestado> findByNomeCliente(@Param("nome") String nome);

	@Query("select s from ServicoPrestado s where MONTH(s.data) = :mes")
	List<ServicoPrestado> findByMes(@Param("mes") Integer mes);

	@Query("select s from ServicoPrestado s join s.cliente c where c.id = :idCliente")
	List<ServicoPrestado> findByCliente(@Param("idCliente") Integer idCliente);
}
