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
@Table(name = "quotes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quotes.findAll", query = "SELECT q FROM Quotes q"),
    @NamedQuery(name = "Quotes.findByIdquotes", query = "SELECT q FROM Quotes q WHERE q.idquotes = :idquotes"),
    @NamedQuery(name = "Quotes.findByQuoter", query = "SELECT q FROM Quotes q WHERE q.quoter = :quoter"),
    @NamedQuery(name = "Quotes.findByQuoteDescription", query = "SELECT q FROM Quotes q WHERE q.quoteDescription = :quoteDescription")})
public class Quotes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idquotes")
    private Integer idquotes;
    @Size(max = 45)
    @Column(name = "quoter")
    private String quoter;
    @Size(max = 400)
    @Column(name = "quoteDescription")
    private String quoteDescription;

    public Quotes() {
    }

    public Quotes(Integer idquotes) {
        this.idquotes = idquotes;
    }

    public Integer getIdquotes() {
        return idquotes;
    }

    public void setIdquotes(Integer idquotes) {
        this.idquotes = idquotes;
    }

    public String getQuoter() {
        return quoter;
    }

    public void setQuoter(String quoter) {
        this.quoter = quoter;
    }

    public String getQuoteDescription() {
        return quoteDescription;
    }

    public void setQuoteDescription(String quoteDescription) {
        this.quoteDescription = quoteDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idquotes != null ? idquotes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quotes)) {
            return false;
        }
        Quotes other = (Quotes) object;
        if ((this.idquotes == null && other.idquotes != null) || (this.idquotes != null && !this.idquotes.equals(other.idquotes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Quotes[ idquotes=" + idquotes + " ]";
    }
    
}
