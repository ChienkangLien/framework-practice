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
@Table(name = "USER")
@ToString(exclude = { "rolePOs" }) // 避免循環引用stackOverflow
@EqualsAndHashCode(exclude = "rolePOs") // 避免循環引用stackOverflow
public class UserPO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, columnDefinition = "INT AUTO_INCREMENT")
	private Integer id;

	@Column(name = "USERNAME", columnDefinition = "VARCHAR(30)")
	private String username;

	@Column(name = "PASSWORD", columnDefinition = "VARCHAR(120)")
	private String password;

	@Column(name = "STATUS", columnDefinition = "DEFAULT '1'")
	private Integer status;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "UID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "RID", referencedColumnName = "ID"))
	private Set<RolePO> rolePOs;
}
