package com.phoenix.finance.entity.bond;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.phoenix.finance.entity.Event;
import com.phoenix.finance.entity.Investor;
import com.phoenix.finance.entity.Money;

@Entity
public class PropertyBond {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int propertyBondNum;

	@Column
	@Embedded
	@AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "principalAmount")))
	private Money principalAmount;

	@Column
	@Embedded
	@AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "monthlyPayment")))
	private Money monthlyPayment;

	@Column
	private BigDecimal annualInterestRate;

	@Column
	private int term;

	@Column
	private String bondType;

	@Enumerated(EnumType.STRING)
	private BondFund bondFund;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "propertyBondNum")
	private List<Event> events = new ArrayList<>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "investorNum")
	private Investor investor;

	public PropertyBond(Money principalAmount, BigDecimal annualInterestRate, int term, BondFund bondFund,
			String bondType) {
		this.principalAmount = principalAmount;
		this.annualInterestRate = annualInterestRate;
		this.term = term;
		this.bondFund = bondFund;
		this.bondType = bondType;
		this.monthlyPayment = calculateSimpleMonthlyPayment();
	}

	public PropertyBond() {
	}

	public Money getMonthlyPayment() {
		return monthlyPayment;
	}

	public Money getPrincipal() {
		return principalAmount;
	}

	public BigDecimal getAnnualInterestRate() {
		return annualInterestRate;
	}

	public BigDecimal getMonthlyInterestRate() {
		BigDecimal annualRateAsPercentage = annualInterestRate.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
		return annualRateAsPercentage.divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);
	}

	public int getTerm() {
		return term;
	}

	public BondFund getBondFund() {
		return bondFund;
	}

	public void setBondFund(BondFund bondFund) {
		this.bondFund = bondFund;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public void setMonthlyPayment(Money monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public void setInvestor(Investor investor) {
		this.investor = investor;
	}

	public int getPropertyBondNum() {
		return propertyBondNum;
	}

	public String getBondType() {
		return bondType;
	}

	public void setBondType(String bondType) {
		this.bondType = bondType;
	}

	private Money calculateSimpleMonthlyPayment() {
		return new Money(getMonthlyPaymentPercentage().multiply(getPrincipal().getValue()));
	}

	private BigDecimal getMonthlyPaymentPercentage() {
		BigDecimal monthlyBondRate = getMonthlyInterestRate();
		BigDecimal base = BigDecimal.ONE.add(monthlyBondRate);
		base = BigDecimal.ONE.divide(base, MathContext.DECIMAL128);
		base = base.pow(getTerm(), MathContext.DECIMAL128);
		base = BigDecimal.ONE.subtract(base, MathContext.DECIMAL128);
		monthlyBondRate = monthlyBondRate.divide(base, MathContext.DECIMAL128);
		return monthlyBondRate;
	}

	@Override
	public String toString() {
		return "PropertyBond [propertyBondNum=" + propertyBondNum + ", principalAmount=" + principalAmount
				+ ", monthlyPayment=" + monthlyPayment + ", annualInterestRate=" + annualInterestRate + ", term=" + term
				+ ", bondType=" + bondType + ", bondFund=" + bondFund + ", events=" + events + ", investor=" + investor + "]";
	}

}
