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
 * Mapping to right table 
 * 
 * @author ThaiTC
 *
 */
@Entity
public class AssRight {

	@Id
	private String m_id;
	
	@Column(unique = true)
	private String m_type; 
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 	@JoinTable(name="assgroup_assright", joinColumns={@JoinColumn(name="assright_m_id")}, inverseJoinColumns={@JoinColumn(name="assgroup_m_id")})
	private List<AssGroup> m_groups;
	
	public AssRight() {
		setId(UUID.randomUUID().toString());
	}
	
	public String getId() {
		return m_id;
	}
	public void setId(String id) {
		this.m_id = id;
	}
	public String getType() {
		return m_type;
	}
	public void setType(String type) {
		this.m_type = type;
	}
	public List<AssGroup> getGroups() {
		return m_groups;
	}
	public void setGroups(List<AssGroup> groups) {
		this.m_groups = groups;
	}
}
