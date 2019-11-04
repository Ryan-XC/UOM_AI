/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbatm;

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
 * @author Ken
 */
@Entity
@Table(name = "APP.ACCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByAccno", query = "SELECT a FROM Account a WHERE a.accno = :accno"),
    @NamedQuery(name = "Account.findByBalance", query = "SELECT a FROM Account a WHERE a.balance = :balance")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "ACCNO")
    private String accno;
    @Column(name = "BALANCE")
    private Integer balance;
    
    
    public Account() {
    }

    public Account(String accno) {
        this.accno = accno;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public Integer getBalance() {
        return this.balance;
    }
    

    
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accno != null ? accno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accno == null && other.accno != null) || (this.accno != null && !this.accno.equals(other.accno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejbatm.Account[ accno=" + accno + " ]";
    }
    
}
