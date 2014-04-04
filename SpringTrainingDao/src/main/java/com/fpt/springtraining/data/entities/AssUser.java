package com.fpt.springtraining.data.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Mapping to user table
 * 
 * @author ThaiTC
 *
 */
@Entity
public class AssUser {

	@Id
	private String m_id;

	@Column(unique = true)
	private String m_username;

	@Column
	private String m_password;

	@Column
	private int m_isAdmin;

	@Column
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="assgroup_assuser", joinColumns={@JoinColumn(name="assuser_m_id")}, inverseJoinColumns={@JoinColumn(name="assgroup_m_id")})
	private List<AssGroup> m_groups;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "m_user")
	private List<AssUser> m_users;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="m_user_m_id")
	private AssUser m_user;
	
	public AssUser() {
		setId(UUID.randomUUID().toString());
	}

	public String getId() {
		return m_id;
	}

	public void setId(String id) {
		this.m_id = id;
	}

	public String getUsername() {
		return m_username;
	}

	public void setUsername(String m_username) {
		this.m_username = m_username;
	}

	public String getPassword() {
		return m_password;
	}

	public void setPassword(String m_password) {
		this.m_password = m_password;
	}

	public AssUser getUser() {
		return m_user;
	}

	public void setUser(AssUser user) {
		this.m_user = user;
	}

	public List<AssUser> getUsers() {
		return m_users;
	}

	public void setUsers(List<AssUser> users) {
		this.m_users = users;
	}

	public int isAdmin() {
		return m_isAdmin;
	}

	public void setAdmin(int isAdmin) {
		this.m_isAdmin = isAdmin;
	}

	public List<AssGroup> getGroups() {
		return m_groups;
	}

	public void setGroups(List<AssGroup> groups) {
		this.m_groups = groups;
	}
}
