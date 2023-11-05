package org.tutorial.model.PO;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tutorial.model.PO.embeddings.UserRoleId;

import lombok.Data;

@Data
@Entity
@Table(name = "USER_ROLE")
public class UserRolePO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRoleId id;
}
