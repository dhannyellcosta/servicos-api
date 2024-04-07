package servicos.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "servico_prestado", schema = "geral")
public class ServicoPrestado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@Column
	private BigDecimal valor;
	
	@Column
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;

	@Column(name = "dt_cadastro", updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCadastro;

	@Column(name = "dt_alteracao")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAlteracao;

	@PrePersist
	public void prePersit() {
		setDataCadastro(LocalDate.now());
	}

	@PreUpdate
	public void preUpdate() {
		setDataAlteracao(LocalDate.now());
	}

}
