package org.tutorial.model.PO;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "ROLE")
@ToString(exclude = { "userPOs", "permissionPOs" }) // 避免循環引用stackOverflow
@EqualsAndHashCode(exclude = { "userPOs", "permissionPOs" }) // 避免循環引用stackOverflow
public class RolePO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, columnDefinition = "INT AUTO_INCREMENT")
	private Integer id;

	@Column(name = "ROLE_NAME", columnDefinition = "VARCHAR(30)")
	private String roleName;

	@Column(name = "ROLE_DESC", columnDefinition = "VARCHAR(60)")
	private String roleDesc;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "RID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "UID", referencedColumnName = "ID"))
	private Set<UserPO> userPOs;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PERMISSION", joinColumns = @JoinColumn(name = "RID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PID", referencedColumnName = "ID"))
	private Set<PermissionPO> permissionPOs;

}
