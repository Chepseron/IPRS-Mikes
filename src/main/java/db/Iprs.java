/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amon.Sabul
 */
@Entity
@Table(name = "iprs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Iprs.findAll", query = "SELECT i FROM Iprs i"),
    @NamedQuery(name = "Iprs.findByIdiprs", query = "SELECT i FROM Iprs i WHERE i.idiprs = :idiprs"),
    @NamedQuery(name = "Iprs.findByIdnumber", query = "SELECT i FROM Iprs i WHERE i.idnumber = :idnumber"),
    @NamedQuery(name = "Iprs.findBySerialNumber", query = "SELECT i FROM Iprs i WHERE i.serialNumber = :serialNumber"),
    @NamedQuery(name = "Iprs.findByFirstName", query = "SELECT i FROM Iprs i WHERE i.firstName = :firstName"),
    @NamedQuery(name = "Iprs.findBySecondName", query = "SELECT i FROM Iprs i WHERE i.secondName = :secondName"),
    @NamedQuery(name = "Iprs.findByLastName", query = "SELECT i FROM Iprs i WHERE i.lastName = :lastName"),
    @NamedQuery(name = "Iprs.findByDob", query = "SELECT i FROM Iprs i WHERE i.dob = :dob"),
    @NamedQuery(name = "Iprs.findByGender", query = "SELECT i FROM Iprs i WHERE i.gender = :gender"),
    @NamedQuery(name = "Iprs.findByDistrictOfBirth", query = "SELECT i FROM Iprs i WHERE i.districtOfBirth = :districtOfBirth"),
    @NamedQuery(name = "Iprs.findByPlaceOfIssue", query = "SELECT i FROM Iprs i WHERE i.placeOfIssue = :placeOfIssue"),
    @NamedQuery(name = "Iprs.findByDateOfIssue", query = "SELECT i FROM Iprs i WHERE i.dateOfIssue = :dateOfIssue"),
    @NamedQuery(name = "Iprs.findByHoldersSign", query = "SELECT i FROM Iprs i WHERE i.holdersSign = :holdersSign"),
    @NamedQuery(name = "Iprs.findByDistrict", query = "SELECT i FROM Iprs i WHERE i.district = :district"),
    @NamedQuery(name = "Iprs.findByDivision", query = "SELECT i FROM Iprs i WHERE i.division = :division"),
    @NamedQuery(name = "Iprs.findByLocation", query = "SELECT i FROM Iprs i WHERE i.location = :location"),
    @NamedQuery(name = "Iprs.findBySublocation", query = "SELECT i FROM Iprs i WHERE i.sublocation = :sublocation"),
    @NamedQuery(name = "Iprs.findByCreatedBy", query = "SELECT i FROM Iprs i WHERE i.createdBy = :createdBy"),
    @NamedQuery(name = "Iprs.findByCreatedOn", query = "SELECT i FROM Iprs i WHERE i.createdOn = :createdOn"),
    @NamedQuery(name = "Iprs.findByStatus", query = "SELECT i FROM Iprs i WHERE i.status = :status")})
public class Iprs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idiprs")
    private Integer idiprs;
    @Column(name = "idnumber")
    private Integer idnumber;
    @Size(max = 45)
    @Column(name = "photo")
    private String photo;
    @Size(max = 45)
    @Column(name = "serialNumber")
    private String serialNumber;
    @Size(max = 100)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 100)
    @Column(name = "secondName")
    private String secondName;
    @Size(max = 100)
    @Column(name = "lastName")
    private String lastName;
    @Size(max = 45)
    @Column(name = "dob")
    private String dob;
    @Size(max = 45)
    @Column(name = "gender")
    private String gender;
    @Size(max = 45)
    @Column(name = "districtOfBirth")
    private String districtOfBirth;
    @Size(max = 45)
    @Column(name = "placeOfIssue")
    private String placeOfIssue;
    @Size(max = 45)
    @Column(name = "dateOfIssue")
    private String dateOfIssue;
    @Size(max = 45)
    @Column(name = "holdersSign")
    private String holdersSign;
    @Size(max = 45)
    @Column(name = "district")
    private String district;
    @Size(max = 45)
    @Column(name = "division")
    private String division;
    @Size(max = 45)
    @Column(name = "location")
    private String location;
    @Size(max = 45)
    @Column(name = "sublocation")
    private String sublocation;
    @Column(name = "createdBy")
    private Integer createdBy;
    @Size(max = 45)
    @Column(name = "createdOn")
    private String createdOn;
    @Size(max = 45)
    @Column(name = "status")
    private String status;

    public Iprs() {
    }

    public Iprs(Integer idiprs) {
        this.idiprs = idiprs;
    }

    public Integer getIdiprs() {
        return idiprs;
    }

    public void setIdiprs(Integer idiprs) {
        this.idiprs = idiprs;
    }

    public Integer getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(Integer idnumber) {
        this.idnumber = idnumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistrictOfBirth() {
        return districtOfBirth;
    }

    public void setDistrictOfBirth(String districtOfBirth) {
        this.districtOfBirth = districtOfBirth;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getHoldersSign() {
        return holdersSign;
    }

    public void setHoldersSign(String holdersSign) {
        this.holdersSign = holdersSign;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSublocation() {
        return sublocation;
    }

    public void setSublocation(String sublocation) {
        this.sublocation = sublocation;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idiprs != null ? idiprs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iprs)) {
            return false;
        }
        Iprs other = (Iprs) object;
        if ((this.idiprs == null && other.idiprs != null) || (this.idiprs != null && !this.idiprs.equals(other.idiprs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Iprs[ idiprs=" + idiprs + " ]";
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
