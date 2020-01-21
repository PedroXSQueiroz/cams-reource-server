package br.com.pedroxsqueiroz.camsresourceserver.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Table(name = "cam")
@Entity
@Data
public class CamModel extends NodeModel {
	
	@ManyToOne
	@JoinColumn(name = "id_client")
	@JsonProperty(access = Access.WRITE_ONLY)
	private ClientModel client;
	
}
