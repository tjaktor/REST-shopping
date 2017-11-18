package com.tjaktor.restshopping.security.user;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 6808143552646523771L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotEmpty
	private String rolename;	
	
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<ApplicationUser> users;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(
			name = "roles_privileges",
			joinColumns = {@JoinColumn(name = "role_id")},
			inverseJoinColumns = {@JoinColumn(name = "privilege_id")})
	private Set<UserPrivilege> privileges;
	
	public UserRole() { }

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Set<ApplicationUser> getUsers() {
		return users;
	}

	public void setUsers(Set<ApplicationUser> users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public Set<UserPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<UserPrivilege> privileges) {
		this.privileges = privileges;
	}
	
}
