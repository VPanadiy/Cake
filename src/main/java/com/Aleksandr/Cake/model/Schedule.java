package com.Aleksandr.Cake.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "schedule")
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	@Column(name = "title")
	@NotNull
	@Size(min=5, max=20,  message = "Username size should be in the range [2...30]")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	@DateTimeFormat(iso=ISO.DATE)
//	@Temporal(TemporalType.TIMESTAMP) // if need show time in calendar
	@Temporal(TemporalType.DATE)
	@Column(name = "date_order")
	private Date dateOreder;
	
	@Column(name = "active")
	private int active;
	
	public Date getDateOrder() {
		return dateOreder;
	}

	public void setDateOrder(Date date_order) {
		this.dateOreder = date_order;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + active;
		result = prime * result + ((dateOreder == null) ? 0 : dateOreder.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (active != other.active)
			return false;
		if (dateOreder == null) {
			if (other.dateOreder != null)
				return false;
		} else if (!dateOreder.equals(other.dateOreder))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", title=" + title + ", description=" + description + ", name=" + name
				+ ", phone=" + phone + ", date=" + dateOreder + ", active=" + active + "]";
	}
	
	
}
