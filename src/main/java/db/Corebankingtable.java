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
 * @author akiptoo
 */
@Entity
@Table(name = "corebankingtable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Corebankingtable.findAll", query = "SELECT c FROM Corebankingtable c"),
    @NamedQuery(name = "Corebankingtable.findByIdcorebankingtable", query = "SELECT c FROM Corebankingtable c WHERE c.idcorebankingtable = :idcorebankingtable"),
    @NamedQuery(name = "Corebankingtable.findByBrcode", query = "SELECT c FROM Corebankingtable c WHERE c.brcode = :brcode"),
    @NamedQuery(name = "Corebankingtable.findByCustomernumber", query = "SELECT c FROM Corebankingtable c WHERE c.customernumber = :customernumber"),
    @NamedQuery(name = "Corebankingtable.findByCurrency", query = "SELECT c FROM Corebankingtable c WHERE c.currency = :currency"),
    @NamedQuery(name = "Corebankingtable.findByLedgercode", query = "SELECT c FROM Corebankingtable c WHERE c.ledgercode = :ledgercode"),
    @NamedQuery(name = "Corebankingtable.findBySubacccode", query = "SELECT c FROM Corebankingtable c WHERE c.subacccode = :subacccode"),
    @NamedQuery(name = "Corebankingtable.findByTrantype", query = "SELECT c FROM Corebankingtable c WHERE c.trantype = :trantype"),
    @NamedQuery(name = "Corebankingtable.findByAmount", query = "SELECT c FROM Corebankingtable c WHERE c.amount = :amount"),
    @NamedQuery(name = "Corebankingtable.findByBatchcode", query = "SELECT c FROM Corebankingtable c WHERE c.batchcode = :batchcode"),
    @NamedQuery(name = "Corebankingtable.findByBatchcode2", query = "SELECT c FROM Corebankingtable c WHERE c.batchcode2 = :batchcode2"),
    @NamedQuery(name = "Corebankingtable.findByNarration", query = "SELECT c FROM Corebankingtable c WHERE c.narration = :narration")})
public class Corebankingtable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcorebankingtable")
    private Integer idcorebankingtable;
    @Size(max = 45)
    @Column(name = "brcode")
    private String brcode;
    @Size(max = 45)
    @Column(name = "customernumber")
    private String customernumber;
    @Size(max = 45)
    @Column(name = "currency")
    private String currency;
    @Size(max = 45)
    @Column(name = "ledgercode")
    private String ledgercode;
    @Size(max = 45)
    @Column(name = "subacccode")
    private String subacccode;
    @Size(max = 45)
    @Column(name = "trantype")
    private String trantype;
    @Column(name = "amount")
    private Integer amount;
    @Size(max = 45)
    @Column(name = "batchcode")
    private String batchcode;
    @Size(max = 45)
    @Column(name = "batchcode2")
    private String batchcode2;
    @Size(max = 300)
    @Column(name = "narration")
    private String narration;

    public Corebankingtable() {
    }

    public Corebankingtable(Integer idcorebankingtable) {
        this.idcorebankingtable = idcorebankingtable;
    }

    public Integer getIdcorebankingtable() {
        return idcorebankingtable;
    }

    public void setIdcorebankingtable(Integer idcorebankingtable) {
        this.idcorebankingtable = idcorebankingtable;
    }

    public String getBrcode() {
        return brcode;
    }

    public void setBrcode(String brcode) {
        this.brcode = brcode;
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLedgercode() {
        return ledgercode;
    }

    public void setLedgercode(String ledgercode) {
        this.ledgercode = ledgercode;
    }

    public String getSubacccode() {
        return subacccode;
    }

    public void setSubacccode(String subacccode) {
        this.subacccode = subacccode;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    public String getBatchcode2() {
        return batchcode2;
    }

    public void setBatchcode2(String batchcode2) {
        this.batchcode2 = batchcode2;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcorebankingtable != null ? idcorebankingtable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Corebankingtable)) {
            return false;
        }
        Corebankingtable other = (Corebankingtable) object;
        if ((this.idcorebankingtable == null && other.idcorebankingtable != null) || (this.idcorebankingtable != null && !this.idcorebankingtable.equals(other.idcorebankingtable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Corebankingtable[ idcorebankingtable=" + idcorebankingtable + " ]";
    }
    
}
