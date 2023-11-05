package org.tutorial.model.PO.embeddings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.tutorial.model.PO.RolePO;
import org.tutorial.model.PO.UserPO;

import lombok.Data;
import lombok.ToString;

@Data
//@ToString(exclude = { "userPO", "rolePO" }) // 避免循環引用stackOverflow
@Embeddable
public class UserRoleId implements Serializable {
	private static final long serialVersionUID = 1L;

//	@ManyToOne
//	@JoinColumn(name = "UID", nullable = false)
//	private UserPO userPO;
//
//	@ManyToOne
//	@JoinColumn(name = "RID", nullable = false)
//	private RolePO rolePO;
	
	@Column(name = "UID", nullable = false)
	private Integer uid;
	
	@Column(name = "RID", nullable = false)
	private Integer rid;
}
