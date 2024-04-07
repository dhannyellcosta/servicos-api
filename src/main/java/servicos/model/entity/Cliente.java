package servicos.model.entity;

import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cliente", schema = "geral")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String nome;
	
	@Column(nullable = false, length = 11)
	private String cpf;
	
	@Column(name = "dt_cadastro", updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCadastro;
	
	@Column(name = "dt_alteracao")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAlteracao;
	
	@PrePersist
	public void prePersist() {
		setDataCadastro(LocalDate.now());
	}

	@PreUpdate
	public void preUpdate() {
		setDataAlteracao(LocalDate.now());
	}
	
	public void setCpf(String cpf) {
		if(cpf != null) {
			cpf = cpf.replace(".", "").replace("-", "");
		}
		this.cpf = cpf;
	}
}
