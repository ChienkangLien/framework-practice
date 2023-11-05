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
@Table(name = "PERMISSION")
@ToString(exclude = { "rolePOs" }) // 避免循環引用stackOverflow
@EqualsAndHashCode(exclude = "rolePOs") // 避免循環引用stackOverflow
public class PermissionPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, columnDefinition = "INT AUTO_INCREMENT")
	private Integer id;

	@Column(name = "PERMISSION_NAME", columnDefinition = "VARCHAR(30)")
	private String permissionName;

	@Column(name = "PERMISSION_DESC", columnDefinition = "VARCHAR(60)")
	private String permissionDesc;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PERMISSION", joinColumns = @JoinColumn(name = "PID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "RID", referencedColumnName = "ID"))
	private Set<RolePO> rolePOs;
}
