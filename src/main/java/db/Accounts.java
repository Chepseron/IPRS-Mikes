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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author akiptoo
 */
@Entity
@Table(name = "accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Accounts a"),
    @NamedQuery(name = "Accounts.findByIdaccounts", query = "SELECT a FROM Accounts a WHERE a.idaccounts = :idaccounts"),
    @NamedQuery(name = "Accounts.findByBrcode", query = "SELECT a FROM Accounts a WHERE a.brcode = :brcode"),
    @NamedQuery(name = "Accounts.findByCustomercode", query = "SELECT a FROM Accounts a WHERE a.customercode = :customercode"),
    @NamedQuery(name = "Accounts.findByCurrency", query = "SELECT a FROM Accounts a WHERE a.currency = :currency"),
    @NamedQuery(name = "Accounts.findByLedgerCode", query = "SELECT a FROM Accounts a WHERE a.ledgerCode = :ledgerCode"),
    @NamedQuery(name = "Accounts.findBySubacctcode", query = "SELECT a FROM Accounts a WHERE a.subacctcode = :subacctcode"),
    @NamedQuery(name = "Accounts.findByAcctype", query = "SELECT a FROM Accounts a WHERE a.acctype = :acctype")})
public class Accounts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaccounts")
    private Integer idaccounts;
    @Basic(optional = false)
    @NotNull
    @Column(name = "brcode")
    private int brcode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customercode")
    private int customercode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "currency")
    private int currency;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ledgerCode")
    private int ledgerCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subacctcode")
    private int subacctcode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "acctype")
    private int acctype;
    @JoinColumn(name = "cardsID", referencedColumnName = "cardnumber")
    @ManyToOne(optional = false)
    private Cards cardsID;

    public Accounts() {
    }

    public Accounts(Integer idaccounts) {
        this.idaccounts = idaccounts;
    }

    public Accounts(Integer idaccounts, int brcode, int customercode, int currency, int ledgerCode, int subacctcode, int acctype) {
        this.idaccounts = idaccounts;
        this.brcode = brcode;
        this.customercode = customercode;
        this.currency = currency;
        this.ledgerCode = ledgerCode;
        this.subacctcode = subacctcode;
        this.acctype = acctype;
    }

    public Integer getIdaccounts() {
        return idaccounts;
    }

    public void setIdaccounts(Integer idaccounts) {
        this.idaccounts = idaccounts;
    }

    public int getBrcode() {
        return brcode;
    }

    public void setBrcode(int brcode) {
        this.brcode = brcode;
    }

    public int getCustomercode() {
        return customercode;
    }

    public void setCustomercode(int customercode) {
        this.customercode = customercode;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getLedgerCode() {
        return ledgerCode;
    }

    public void setLedgerCode(int ledgerCode) {
        this.ledgerCode = ledgerCode;
    }

    public int getSubacctcode() {
        return subacctcode;
    }

    public void setSubacctcode(int subacctcode) {
        this.subacctcode = subacctcode;
    }

    public int getAcctype() {
        return acctype;
    }

    public void setAcctype(int acctype) {
        this.acctype = acctype;
    }

    public Cards getCardsID() {
        return cardsID;
    }

    public void setCardsID(Cards cardsID) {
        this.cardsID = cardsID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaccounts != null ? idaccounts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.idaccounts == null && other.idaccounts != null) || (this.idaccounts != null && !this.idaccounts.equals(other.idaccounts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Accounts[ idaccounts=" + idaccounts + " ]";
    }
    
}
