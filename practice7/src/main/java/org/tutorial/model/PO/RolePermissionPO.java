package org.tutorial.model.PO;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tutorial.model.PO.embeddings.RolePermissionId;

import lombok.Data;

@Data
@Entity
@Table(name = "ROLE_PERMISSION")
public class RolePermissionPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolePermissionId id;

}
