/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amon.Sabul
 */
@Entity
@Table(name = "contacts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contacts.findAll", query = "SELECT c FROM Contacts c")
    , @NamedQuery(name = "Contacts.findByIdcontacts", query = "SELECT c FROM Contacts c WHERE c.idcontacts = :idcontacts")
    , @NamedQuery(name = "Contacts.findByContact", query = "SELECT c FROM Contacts c WHERE c.contact = :contact")
    , @NamedQuery(name = "Contacts.findByCreatedBy", query = "SELECT c FROM Contacts c WHERE c.createdBy = :createdBy")
    , @NamedQuery(name = "Contacts.findByCreatedOn", query = "SELECT c FROM Contacts c WHERE c.createdOn = :createdOn")})
public class Contacts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontacts")
    private Integer idcontacts;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "createdBy")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    public Contacts() {
    }

    public Contacts(Integer idcontacts) {
        this.idcontacts = idcontacts;
    }

    public Contacts(Integer idcontacts, String contact, String createdBy, Date createdOn) {
        this.idcontacts = idcontacts;
        this.contact = contact;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
    }

    public Integer getIdcontacts() {
        return idcontacts;
    }

    public void setIdcontacts(Integer idcontacts) {
        this.idcontacts = idcontacts;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontacts != null ? idcontacts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contacts)) {
            return false;
        }
        Contacts other = (Contacts) object;
        if ((this.idcontacts == null && other.idcontacts != null) || (this.idcontacts != null && !this.idcontacts.equals(other.idcontacts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Contacts[ idcontacts=" + idcontacts + " ]";
    }
    
}
