/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import bean.Groups;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "admins")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admins.findAll", query = "SELECT a FROM Admins a")
    , @NamedQuery(name = "Admins.findByAdminId", query = "SELECT a FROM Admins a WHERE a.adminId = :adminId")
    , @NamedQuery(name = "Admins.findByFirstName", query = "SELECT a FROM Admins a WHERE a.firstName = :firstName")
    , @NamedQuery(name = "Admins.findByLastName", query = "SELECT a FROM Admins a WHERE a.lastName = :lastName")
    , @NamedQuery(name = "Admins.findByPhone", query = "SELECT a FROM Admins a WHERE a.phone = :phone")
    , @NamedQuery(name = "Admins.findByUsername", query = "SELECT a FROM Admins a WHERE a.username = :username")
    , @NamedQuery(name = "Admins.findByPassword", query = "SELECT a FROM Admins a WHERE a.password = :password")
    , @NamedQuery(name = "Admins.findByEmailAdd", query = "SELECT a FROM Admins a WHERE a.emailAdd = :emailAdd")
    , @NamedQuery(name = "Admins.findByMiddlename", query = "SELECT a FROM Admins a WHERE a.middlename = :middlename")
    , @NamedQuery(name = "Admins.findByDateCreated", query = "SELECT a FROM Admins a WHERE a.dateCreated = :dateCreated")
    , @NamedQuery(name = "Admins.findByStatus", query = "SELECT a FROM Admins a WHERE a.status = :status")
    , @NamedQuery(name = "Admins.findByOtp", query = "SELECT a FROM Admins a WHERE a.otp = :otp")})
public class Admins implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "adminId")
    private Integer adminId;
    @Size(max = 50)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 50)
    @Column(name = "lastName")
    private String lastName;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 15)
    @Column(name = "phone")
    private String phone;
    @Size(max = 50)
    @Column(name = "username")
    private String username;
    @Size(max = 1000)
    @Column(name = "password")
    private String password;
    @Size(max = 100)
    @Column(name = "emailAdd")
    private String emailAdd;
    @Size(max = 45)
    @Column(name = "middlename")
    private String middlename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "otp")
    private String otp;
    @JoinColumn(name = "groupID", referencedColumnName = "idgroups")
    @ManyToOne(optional = false)
    private Groups groupID;

    public Admins() {
    }

    public Admins(Integer adminId) {
        this.adminId = adminId;
    }

    public Admins(Integer adminId, Date dateCreated, int status, String otp) {
        this.adminId = adminId;
        this.dateCreated = dateCreated;
        this.status = status;
        this.otp = otp;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Groups getGroupID() {
        return groupID;
    }

    public void setGroupID(Groups groupID) {
        this.groupID = groupID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adminId != null ? adminId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admins)) {
            return false;
        }
        Admins other = (Admins) object;
        if ((this.adminId == null && other.adminId != null) || (this.adminId != null && !this.adminId.equals(other.adminId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Admins[ adminId=" + adminId + " ]";
    }
    
}
