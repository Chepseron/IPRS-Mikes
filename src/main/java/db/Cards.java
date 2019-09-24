/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author akiptoo
 */
@Entity
@Table(name = "cards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cards.findAll", query = "SELECT c FROM Cards c"),
    @NamedQuery(name = "Cards.findByIdcards", query = "SELECT c FROM Cards c WHERE c.idcards = :idcards"),
    @NamedQuery(name = "Cards.findByBranchname", query = "SELECT c FROM Cards c WHERE c.branchname = :branchname"),
    @NamedQuery(name = "Cards.findByProductname", query = "SELECT c FROM Cards c WHERE c.productname = :productname"),
    @NamedQuery(name = "Cards.findByCardnumber", query = "SELECT c FROM Cards c WHERE c.cardnumber = :cardnumber"),
    @NamedQuery(name = "Cards.findByCardtype", query = "SELECT c FROM Cards c WHERE c.cardtype = :cardtype"),
    @NamedQuery(name = "Cards.findByAcctnumber", query = "SELECT c FROM Cards c WHERE c.acctnumber = :acctnumber"),
    @NamedQuery(name = "Cards.findByAcctstatus", query = "SELECT c FROM Cards c WHERE c.acctstatus = :acctstatus"),
    @NamedQuery(name = "Cards.findByClientId", query = "SELECT c FROM Cards c WHERE c.clientId = :clientId"),
    @NamedQuery(name = "Cards.findByCustomername", query = "SELECT c FROM Cards c WHERE c.customername = :customername"),
    @NamedQuery(name = "Cards.findByEmbossingname", query = "SELECT c FROM Cards c WHERE c.embossingname = :embossingname"),
    @NamedQuery(name = "Cards.findByContractnumber", query = "SELECT c FROM Cards c WHERE c.contractnumber = :contractnumber"),
    @NamedQuery(name = "Cards.findByContractcreation", query = "SELECT c FROM Cards c WHERE c.contractcreation = :contractcreation"),
    @NamedQuery(name = "Cards.findByContractlimit", query = "SELECT c FROM Cards c WHERE c.contractlimit = :contractlimit"),
    @NamedQuery(name = "Cards.findByAccountlimit", query = "SELECT c FROM Cards c WHERE c.accountlimit = :accountlimit"),
    @NamedQuery(name = "Cards.findByCardcreation", query = "SELECT c FROM Cards c WHERE c.cardcreation = :cardcreation"),
    @NamedQuery(name = "Cards.findByCardactivation", query = "SELECT c FROM Cards c WHERE c.cardactivation = :cardactivation"),
    @NamedQuery(name = "Cards.findByActivedays", query = "SELECT c FROM Cards c WHERE c.activedays = :activedays"),
    @NamedQuery(name = "Cards.findByExpirydate", query = "SELECT c FROM Cards c WHERE c.expirydate = :expirydate"),
    @NamedQuery(name = "Cards.findByCmsstatus", query = "SELECT c FROM Cards c WHERE c.cmsstatus = :cmsstatus"),
    @NamedQuery(name = "Cards.findByOnlinestatus", query = "SELECT c FROM Cards c WHERE c.onlinestatus = :onlinestatus"),
    @NamedQuery(name = "Cards.findByPhonenumber", query = "SELECT c FROM Cards c WHERE c.phonenumber = :phonenumber"),
    @NamedQuery(name = "Cards.findByMobilenumber", query = "SELECT c FROM Cards c WHERE c.mobilenumber = :mobilenumber"),
    @NamedQuery(name = "Cards.findByEmail", query = "SELECT c FROM Cards c WHERE c.email = :email"),
    @NamedQuery(name = "Cards.findByExternalaccount", query = "SELECT c FROM Cards c WHERE c.externalaccount = :externalaccount"),
    @NamedQuery(name = "Cards.findByIssuancepriority", query = "SELECT c FROM Cards c WHERE c.issuancepriority = :issuancepriority"),
    @NamedQuery(name = "Cards.findByIssuancereason", query = "SELECT c FROM Cards c WHERE c.issuancereason = :issuancereason"),
    @NamedQuery(name = "Cards.findByFinancialprofile", query = "SELECT c FROM Cards c WHERE c.financialprofile = :financialprofile"),
    @NamedQuery(name = "Cards.findByAddress", query = "SELECT c FROM Cards c WHERE c.address = :address"),
    @NamedQuery(name = "Cards.findByContracttype", query = "SELECT c FROM Cards c WHERE c.contracttype = :contracttype"),
    @NamedQuery(name = "Cards.findByDebitfinprofname", query = "SELECT c FROM Cards c WHERE c.debitfinprofname = :debitfinprofname"),
    @NamedQuery(name = "Cards.findByUserid", query = "SELECT c FROM Cards c WHERE c.userid = :userid"),
    @NamedQuery(name = "Cards.findByTelebankID", query = "SELECT c FROM Cards c WHERE c.telebankID = :telebankID"),
    @NamedQuery(name = "Cards.findByBlockreissue", query = "SELECT c FROM Cards c WHERE c.blockreissue = :blockreissue"),
    @NamedQuery(name = "Cards.findByAssigned", query = "SELECT c FROM Cards c WHERE c.assigned = :assigned")})
public class Cards implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "idcards")
    private Integer idcards;
    @Size(max = 100)
    @Column(name = "branchname")
    private String branchname;
    @Size(max = 100)
    @Column(name = "productname")
    private String productname;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cardnumber")
    private String cardnumber;
    @Size(max = 10)
    @Column(name = "cardtype")
    private String cardtype;
    @Size(max = 100)
    @Column(name = "acctnumber")
    private String acctnumber;
    @Size(max = 45)
    @Column(name = "acctstatus")
    private String acctstatus;
    @Size(max = 45)
    @Column(name = "clientId")
    private String clientId;
    @Size(max = 100)
    @Column(name = "customername")
    private String customername;
    @Size(max = 100)
    @Column(name = "embossingname")
    private String embossingname;
    @Size(max = 45)
    @Column(name = "contractnumber")
    private String contractnumber;
    @Size(max = 45)
    @Column(name = "contractcreation")
    private String contractcreation;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "contractlimit")
    private Double contractlimit;
    @Column(name = "accountlimit")
    private Double accountlimit;
    @Size(max = 45)
    @Column(name = "cardcreation")
    private String cardcreation;
    @Size(max = 45)
    @Column(name = "cardactivation")
    private String cardactivation;
    @Size(max = 45)
    @Column(name = "activedays")
    private String activedays;
    @Size(max = 45)
    @Column(name = "expirydate")
    private String expirydate;
    @Size(max = 45)
    @Column(name = "cmsstatus")
    private String cmsstatus;
    @Size(max = 45)
    @Column(name = "onlinestatus")
    private String onlinestatus;
    @Size(max = 45)
    @Column(name = "phonenumber")
    private String phonenumber;
    @Size(max = 45)
    @Column(name = "mobilenumber")
    private String mobilenumber;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 45)
    @Column(name = "externalaccount")
    private String externalaccount;
    @Size(max = 5)
    @Column(name = "issuancepriority")
    private String issuancepriority;
    @Size(max = 45)
    @Column(name = "issuancereason")
    private String issuancereason;
    @Size(max = 100)
    @Column(name = "financialprofile")
    private String financialprofile;
    @Size(max = 45)
    @Column(name = "address")
    private String address;
    @Size(max = 45)
    @Column(name = "contracttype")
    private String contracttype;
    @Size(max = 45)
    @Column(name = "debitfinprofname")
    private String debitfinprofname;
    @Size(max = 45)
    @Column(name = "userid")
    private String userid;
    @Size(max = 45)
    @Column(name = "telebankID")
    private String telebankID;
    @Size(max = 45)
    @Column(name = "blockreissue")
    private String blockreissue;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "assigned")
    private String assigned;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardsID")
    private Collection<Accounts> accountsCollection;

    public Cards() {
    }

    public Cards(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public Cards(String cardnumber, String assigned) {
        this.cardnumber = cardnumber;
        this.assigned = assigned;
    }

    public Integer getIdcards() {
        return idcards;
    }

    public void setIdcards(Integer idcards) {
        this.idcards = idcards;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getAcctnumber() {
        return acctnumber;
    }

    public void setAcctnumber(String acctnumber) {
        this.acctnumber = acctnumber;
    }

    public String getAcctstatus() {
        return acctstatus;
    }

    public void setAcctstatus(String acctstatus) {
        this.acctstatus = acctstatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getEmbossingname() {
        return embossingname;
    }

    public void setEmbossingname(String embossingname) {
        this.embossingname = embossingname;
    }

    public String getContractnumber() {
        return contractnumber;
    }

    public void setContractnumber(String contractnumber) {
        this.contractnumber = contractnumber;
    }

    public String getContractcreation() {
        return contractcreation;
    }

    public void setContractcreation(String contractcreation) {
        this.contractcreation = contractcreation;
    }

    public Double getContractlimit() {
        return contractlimit;
    }

    public void setContractlimit(Double contractlimit) {
        this.contractlimit = contractlimit;
    }

    public Double getAccountlimit() {
        return accountlimit;
    }

    public void setAccountlimit(Double accountlimit) {
        this.accountlimit = accountlimit;
    }

    public String getCardcreation() {
        return cardcreation;
    }

    public void setCardcreation(String cardcreation) {
        this.cardcreation = cardcreation;
    }

    public String getCardactivation() {
        return cardactivation;
    }

    public void setCardactivation(String cardactivation) {
        this.cardactivation = cardactivation;
    }

    public String getActivedays() {
        return activedays;
    }

    public void setActivedays(String activedays) {
        this.activedays = activedays;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getCmsstatus() {
        return cmsstatus;
    }

    public void setCmsstatus(String cmsstatus) {
        this.cmsstatus = cmsstatus;
    }

    public String getOnlinestatus() {
        return onlinestatus;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExternalaccount() {
        return externalaccount;
    }

    public void setExternalaccount(String externalaccount) {
        this.externalaccount = externalaccount;
    }

    public String getIssuancepriority() {
        return issuancepriority;
    }

    public void setIssuancepriority(String issuancepriority) {
        this.issuancepriority = issuancepriority;
    }

    public String getIssuancereason() {
        return issuancereason;
    }

    public void setIssuancereason(String issuancereason) {
        this.issuancereason = issuancereason;
    }

    public String getFinancialprofile() {
        return financialprofile;
    }

    public void setFinancialprofile(String financialprofile) {
        this.financialprofile = financialprofile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContracttype() {
        return contracttype;
    }

    public void setContracttype(String contracttype) {
        this.contracttype = contracttype;
    }

    public String getDebitfinprofname() {
        return debitfinprofname;
    }

    public void setDebitfinprofname(String debitfinprofname) {
        this.debitfinprofname = debitfinprofname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTelebankID() {
        return telebankID;
    }

    public void setTelebankID(String telebankID) {
        this.telebankID = telebankID;
    }

    public String getBlockreissue() {
        return blockreissue;
    }

    public void setBlockreissue(String blockreissue) {
        this.blockreissue = blockreissue;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    @XmlTransient
    public Collection<Accounts> getAccountsCollection() {
        return accountsCollection;
    }

    public void setAccountsCollection(Collection<Accounts> accountsCollection) {
        this.accountsCollection = accountsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardnumber != null ? cardnumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cards)) {
            return false;
        }
        Cards other = (Cards) object;
        if ((this.cardnumber == null && other.cardnumber != null) || (this.cardnumber != null && !this.cardnumber.equals(other.cardnumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Cards[ cardnumber=" + cardnumber + " ]";
    }
    
}
