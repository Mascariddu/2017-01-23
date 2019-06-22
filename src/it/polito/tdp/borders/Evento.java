package it.polito.tdp.borders;

import it.polito.tdp.borders.model.Country;

public class Evento implements Comparable<Evento>{

	public enum Tipo{
		
		PARTENZA,
		ARRIVO
		
	}
	
	private Tipo tipo;
	private Country country;
	private int passo;
	private int persone;
	
	public Evento(Tipo tipo, Country country, int passo, int i) {
		super();
		this.tipo = tipo;
		this.country = country;
		this.passo = passo;
		this.persone = i;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public int getPasso() {
		return passo;
	}
	public void setPasso(int passo) {
		this.passo = passo;
	}
	public int getPersone() {
		return persone;
	}
	public void setPersone(int persone) {
		this.persone = persone;
	}
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.passo - o.passo;
	}
	
}
