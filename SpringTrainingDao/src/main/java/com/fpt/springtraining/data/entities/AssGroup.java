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

/**
 * Mapping to group table
 * 
 * @author ThaiTC
 *
 */
@Entity
public class AssGroup {
	
	public AssGroup() { 
		setId(UUID.randomUUID().toString());
	}
	
	@Id
	private String m_id;
	
	@Column(unique = true, nullable = false)
	private String m_name;
	
	private String m_type;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 	@JoinTable(name="assgroup_assuser", joinColumns={@JoinColumn(name="assgroup_m_id")}, inverseJoinColumns={@JoinColumn(name="assuser_m_id")})
	private List<AssUser> m_users;
	
 	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 	@JoinTable(name="assgroup_assright", joinColumns={@JoinColumn(name="assgroup_m_id")}, inverseJoinColumns={@JoinColumn(name="assright_m_id")})
	private List<AssRight> m_rights;
	
	public List<AssUser> getUsers() {
		return m_users;
	}

	public void setUsers(List<AssUser> users) {
		this.m_users = users;
	}

	public List<AssRight> getRights() {
		return m_rights;
	}

	public void setRights(List<AssRight> rights) {
		this.m_rights = rights;
	}

	public String getId() {
		return m_id;
	}

	public void setId(String id) {
		this.m_id = id;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		this.m_name = name;
	}

	public String getType() {
		return m_type;
	}

	public void setType(String type) {
		this.m_type = type;
	}
}
