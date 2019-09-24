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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author akiptoo
 */
@Entity
@Table(name = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emp.findAll", query = "SELECT e FROM Emp e"),
    @NamedQuery(name = "Emp.findByPan", query = "SELECT e FROM Emp e WHERE e.pan = :pan"),
    @NamedQuery(name = "Emp.findByFfid", query = "SELECT e FROM Emp e WHERE e.ffid = :ffid"),
    @NamedQuery(name = "Emp.findByTerminalID", query = "SELECT e FROM Emp e WHERE e.terminalID = :terminalID"),
    @NamedQuery(name = "Emp.findByReasonCode", query = "SELECT e FROM Emp e WHERE e.reasonCode = :reasonCode"),
    @NamedQuery(name = "Emp.findByBankAccount", query = "SELECT e FROM Emp e WHERE e.bankAccount = :bankAccount"),
    @NamedQuery(name = "Emp.findByOriginalMessageType", query = "SELECT e FROM Emp e WHERE e.originalMessageType = :originalMessageType"),
    @NamedQuery(name = "Emp.findByMessageType", query = "SELECT e FROM Emp e WHERE e.messageType = :messageType"),
    @NamedQuery(name = "Emp.findByProcessingCode", query = "SELECT e FROM Emp e WHERE e.processingCode = :processingCode"),
    @NamedQuery(name = "Emp.findByBillingCurrency", query = "SELECT e FROM Emp e WHERE e.billingCurrency = :billingCurrency"),
    @NamedQuery(name = "Emp.findByBillingCureencyDecimals", query = "SELECT e FROM Emp e WHERE e.billingCureencyDecimals = :billingCureencyDecimals"),
    @NamedQuery(name = "Emp.findByBillingAmount", query = "SELECT e FROM Emp e WHERE e.billingAmount = :billingAmount"),
    @NamedQuery(name = "Emp.findByAmountIndicator", query = "SELECT e FROM Emp e WHERE e.amountIndicator = :amountIndicator"),
    @NamedQuery(name = "Emp.findByPostingDate", query = "SELECT e FROM Emp e WHERE e.postingDate = :postingDate"),
    @NamedQuery(name = "Emp.findByTransactionDate", query = "SELECT e FROM Emp e WHERE e.transactionDate = :transactionDate"),
    @NamedQuery(name = "Emp.findByTranCurrency", query = "SELECT e FROM Emp e WHERE e.tranCurrency = :tranCurrency"),
    @NamedQuery(name = "Emp.findByTranCurrencyDecimals", query = "SELECT e FROM Emp e WHERE e.tranCurrencyDecimals = :tranCurrencyDecimals"),
    @NamedQuery(name = "Emp.findByTransactionAmount", query = "SELECT e FROM Emp e WHERE e.transactionAmount = :transactionAmount"),
    @NamedQuery(name = "Emp.findByClearingCurrency", query = "SELECT e FROM Emp e WHERE e.clearingCurrency = :clearingCurrency"),
    @NamedQuery(name = "Emp.findByClearingCurrencyDecimals", query = "SELECT e FROM Emp e WHERE e.clearingCurrencyDecimals = :clearingCurrencyDecimals"),
    @NamedQuery(name = "Emp.findByClearingAmount", query = "SELECT e FROM Emp e WHERE e.clearingAmount = :clearingAmount"),
    @NamedQuery(name = "Emp.findByRate", query = "SELECT e FROM Emp e WHERE e.rate = :rate"),
    @NamedQuery(name = "Emp.findByMessageDescription", query = "SELECT e FROM Emp e WHERE e.messageDescription = :messageDescription"),
    @NamedQuery(name = "Emp.findByTerminalLocation", query = "SELECT e FROM Emp e WHERE e.terminalLocation = :terminalLocation"),
    @NamedQuery(name = "Emp.findByCountry", query = "SELECT e FROM Emp e WHERE e.country = :country"),
    @NamedQuery(name = "Emp.findByMcc", query = "SELECT e FROM Emp e WHERE e.mcc = :mcc"),
    @NamedQuery(name = "Emp.findByApproval", query = "SELECT e FROM Emp e WHERE e.approval = :approval"),
    @NamedQuery(name = "Emp.findByArn", query = "SELECT e FROM Emp e WHERE e.arn = :arn"),
    @NamedQuery(name = "Emp.findByRelation", query = "SELECT e FROM Emp e WHERE e.relation = :relation"),
    @NamedQuery(name = "Emp.findByBankRef", query = "SELECT e FROM Emp e WHERE e.bankRef = :bankRef"),
    @NamedQuery(name = "Emp.findByUniqueID", query = "SELECT e FROM Emp e WHERE e.uniqueID = :uniqueID"),
    @NamedQuery(name = "Emp.findByEntryCode", query = "SELECT e FROM Emp e WHERE e.entryCode = :entryCode"),
    @NamedQuery(name = "Emp.findByClientID", query = "SELECT e FROM Emp e WHERE e.clientID = :clientID"),
    @NamedQuery(name = "Emp.findByExternalClientCode", query = "SELECT e FROM Emp e WHERE e.externalClientCode = :externalClientCode"),
    @NamedQuery(name = "Emp.findByContractNumber", query = "SELECT e FROM Emp e WHERE e.contractNumber = :contractNumber"),
    @NamedQuery(name = "Emp.findByCardBranch", query = "SELECT e FROM Emp e WHERE e.cardBranch = :cardBranch"),
    @NamedQuery(name = "Emp.findByAcctbranch", query = "SELECT e FROM Emp e WHERE e.acctbranch = :acctbranch"),
    @NamedQuery(name = "Emp.findByInternalAcctNumber", query = "SELECT e FROM Emp e WHERE e.internalAcctNumber = :internalAcctNumber"),
    @NamedQuery(name = "Emp.findByWebmotoID", query = "SELECT e FROM Emp e WHERE e.webmotoID = :webmotoID"),
    @NamedQuery(name = "Emp.findByRrn", query = "SELECT e FROM Emp e WHERE e.rrn = :rrn"),
    @NamedQuery(name = "Emp.findByStan", query = "SELECT e FROM Emp e WHERE e.stan = :stan"),
    @NamedQuery(name = "Emp.findByCpd", query = "SELECT e FROM Emp e WHERE e.cpd = :cpd"),
    @NamedQuery(name = "Emp.findByAcquiereID", query = "SELECT e FROM Emp e WHERE e.acquiereID = :acquiereID"),
    @NamedQuery(name = "Emp.findByExtStan", query = "SELECT e FROM Emp e WHERE e.extStan = :extStan"),
    @NamedQuery(name = "Emp.findByReserved", query = "SELECT e FROM Emp e WHERE e.reserved = :reserved")})
public class Emp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 45)
    @Column(name = "pan")
    private String pan;
    @Size(max = 45)
    @Column(name = "ffid")
    private String ffid;
    @Size(max = 10)
    @Column(name = "terminalID")
    private String terminalID;
    @Size(max = 5)
    @Column(name = "reasonCode")
    private String reasonCode;
    @Size(max = 40)
    @Column(name = "bankAccount")
    private String bankAccount;
    @Size(max = 10)
    @Column(name = "originalMessageType")
    private String originalMessageType;
    @Size(max = 45)
    @Column(name = "messageType")
    private String messageType;
    @Size(max = 17)
    @Column(name = "processingCode")
    private String processingCode;
    @Size(max = 5)
    @Column(name = "billingCurrency")
    private String billingCurrency;
    @Size(max = 2)
    @Column(name = "billingCureencyDecimals")
    private String billingCureencyDecimals;
    @Column(name = "billingAmount")
    private Integer billingAmount;
    @Size(max = 10)
    @Column(name = "amountIndicator")
    private String amountIndicator;
    @Size(max = 40)
    @Column(name = "postingDate")
    private String postingDate;
    @Size(max = 40)
    @Column(name = "transactionDate")
    private String transactionDate;
    @Size(max = 45)
    @Column(name = "tranCurrency")
    private String tranCurrency;
    @Size(max = 45)
    @Column(name = "tranCurrencyDecimals")
    private String tranCurrencyDecimals;
    @Column(name = "transactionAmount")
    private Integer transactionAmount;
    @Size(max = 4)
    @Column(name = "clearingCurrency")
    private String clearingCurrency;
    @Size(max = 2)
    @Column(name = "clearingCurrencyDecimals")
    private String clearingCurrencyDecimals;
    @Size(max = 45)
    @Column(name = "clearingAmount")
    private String clearingAmount;
    @Size(max = 45)
    @Column(name = "rate")
    private String rate;
    @Size(max = 100)
    @Column(name = "messageDescription")
    private String messageDescription;
    @Size(max = 200)
    @Column(name = "terminalLocation")
    private String terminalLocation;
    @Size(max = 5)
    @Column(name = "country")
    private String country;
    @Size(max = 5)
    @Column(name = "mcc")
    private String mcc;
    @Size(max = 7)
    @Column(name = "approval")
    private String approval;
    @Size(max = 45)
    @Column(name = "arn")
    private String arn;
    @Size(max = 10)
    @Column(name = "relation")
    private String relation;
    @Size(max = 32)
    @Column(name = "bankRef")
    private String bankRef;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 22)
    @Column(name = "uniqueID")
    private String uniqueID;
    @Size(max = 55)
    @Column(name = "entryCode")
    private String entryCode;
    @Size(max = 22)
    @Column(name = "clientID")
    private String clientID;
    @Size(max = 22)
    @Column(name = "externalClientCode")
    private String externalClientCode;
    @Size(max = 33)
    @Column(name = "contractNumber")
    private String contractNumber;
    @Size(max = 4)
    @Column(name = "cardBranch")
    private String cardBranch;
    @Size(max = 4)
    @Column(name = "acctbranch")
    private String acctbranch;
    @Size(max = 40)
    @Column(name = "internalAcctNumber")
    private String internalAcctNumber;
    @Size(max = 5)
    @Column(name = "webmotoID")
    private String webmotoID;
    @Size(max = 14)
    @Column(name = "rrn")
    private String rrn;
    @Size(max = 7)
    @Column(name = "stan")
    private String stan;
    @Size(max = 45)
    @Column(name = "cpd")
    private String cpd;
    @Size(max = 8)
    @Column(name = "acquiereID")
    private String acquiereID;
    @Size(max = 8)
    @Column(name = "extStan")
    private String extStan;
    @Size(max = 10)
    @Column(name = "reserved")
    private String reserved;

    public Emp() {
    }

    public Emp(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getFfid() {
        return ffid;
    }

    public void setFfid(String ffid) {
        this.ffid = ffid;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getOriginalMessageType() {
        return originalMessageType;
    }

    public void setOriginalMessageType(String originalMessageType) {
        this.originalMessageType = originalMessageType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getBillingCurrency() {
        return billingCurrency;
    }

    public void setBillingCurrency(String billingCurrency) {
        this.billingCurrency = billingCurrency;
    }

    public String getBillingCureencyDecimals() {
        return billingCureencyDecimals;
    }

    public void setBillingCureencyDecimals(String billingCureencyDecimals) {
        this.billingCureencyDecimals = billingCureencyDecimals;
    }

    public Integer getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(Integer billingAmount) {
        this.billingAmount = billingAmount;
    }

    public String getAmountIndicator() {
        return amountIndicator;
    }

    public void setAmountIndicator(String amountIndicator) {
        this.amountIndicator = amountIndicator;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTranCurrency() {
        return tranCurrency;
    }

    public void setTranCurrency(String tranCurrency) {
        this.tranCurrency = tranCurrency;
    }

    public String getTranCurrencyDecimals() {
        return tranCurrencyDecimals;
    }

    public void setTranCurrencyDecimals(String tranCurrencyDecimals) {
        this.tranCurrencyDecimals = tranCurrencyDecimals;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getClearingCurrency() {
        return clearingCurrency;
    }

    public void setClearingCurrency(String clearingCurrency) {
        this.clearingCurrency = clearingCurrency;
    }

    public String getClearingCurrencyDecimals() {
        return clearingCurrencyDecimals;
    }

    public void setClearingCurrencyDecimals(String clearingCurrencyDecimals) {
        this.clearingCurrencyDecimals = clearingCurrencyDecimals;
    }

    public String getClearingAmount() {
        return clearingAmount;
    }

    public void setClearingAmount(String clearingAmount) {
        this.clearingAmount = clearingAmount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public String getTerminalLocation() {
        return terminalLocation;
    }

    public void setTerminalLocation(String terminalLocation) {
        this.terminalLocation = terminalLocation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getBankRef() {
        return bankRef;
    }

    public void setBankRef(String bankRef) {
        this.bankRef = bankRef;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getExternalClientCode() {
        return externalClientCode;
    }

    public void setExternalClientCode(String externalClientCode) {
        this.externalClientCode = externalClientCode;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getCardBranch() {
        return cardBranch;
    }

    public void setCardBranch(String cardBranch) {
        this.cardBranch = cardBranch;
    }

    public String getAcctbranch() {
        return acctbranch;
    }

    public void setAcctbranch(String acctbranch) {
        this.acctbranch = acctbranch;
    }

    public String getInternalAcctNumber() {
        return internalAcctNumber;
    }

    public void setInternalAcctNumber(String internalAcctNumber) {
        this.internalAcctNumber = internalAcctNumber;
    }

    public String getWebmotoID() {
        return webmotoID;
    }

    public void setWebmotoID(String webmotoID) {
        this.webmotoID = webmotoID;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getCpd() {
        return cpd;
    }

    public void setCpd(String cpd) {
        this.cpd = cpd;
    }

    public String getAcquiereID() {
        return acquiereID;
    }

    public void setAcquiereID(String acquiereID) {
        this.acquiereID = acquiereID;
    }

    public String getExtStan() {
        return extStan;
    }

    public void setExtStan(String extStan) {
        this.extStan = extStan;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uniqueID != null ? uniqueID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emp)) {
            return false;
        }
        Emp other = (Emp) object;
        if ((this.uniqueID == null && other.uniqueID != null) || (this.uniqueID != null && !this.uniqueID.equals(other.uniqueID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Emp[ uniqueID=" + uniqueID + " ]";
    }
    
}
