package org.tutorial.model.PO.embeddings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.tutorial.model.PO.PermissionPO;
import org.tutorial.model.PO.RolePO;

import lombok.Data;
import lombok.ToString;

@Data
//@ToString(exclude = { "rolePO", "permissionPO" }) // 避免循環引用stackOverflow
@Embeddable
public class RolePermissionId implements Serializable {
	private static final long serialVersionUID = 1L;

//	@ManyToOne
//	@JoinColumn(name = "RID", nullable = false)
//	private RolePO rolePO;
//
//	@ManyToOne
//	@JoinColumn(name = "PID", nullable = false)
//	private PermissionPO permissionPO;
	
	@Column(name = "RID", nullable = false)
	private Integer rid;
	
	@Column(name = "PID", nullable = false)
	private Integer pid;
}
