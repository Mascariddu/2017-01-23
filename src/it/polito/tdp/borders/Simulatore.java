package it.polito.tdp.borders;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.sun.javafx.collections.MapAdapterChange;

import it.polito.tdp.borders.Evento.Tipo;
import it.polito.tdp.borders.model.Country;

public class Simulatore {
	
	PriorityQueue<Evento> queue;
	HashMap<Country, Integer> mappaPersone;
	SimpleGraph<Country, DefaultEdge> grafo;
	int passo;

	public Simulatore(SimpleGraph<Country, DefaultEdge> grafo) {
		
		queue = new PriorityQueue<Evento>();
		mappaPersone = new HashMap<Country, Integer>();
		for(Country country : grafo.vertexSet())
			mappaPersone.put(country, 0);
		this.grafo = grafo;
		passo = 0;
		
	}

	public void init(Country country) {
		// TODO Auto-generated method stub
			
		if(grafo.degreeOf(country) > 0) {
			queue.add(new Evento(Tipo.ARRIVO, country, 1,10000000));
		} else {
			mappaPersone.replace(country, 10000000);
			passo = 1;
		}
		
	}

	public void run() {
		// TODO Auto-generated method stub
		Evento evento;
		
		while((evento = queue.poll()) != null) {
			
			switch (evento.getTipo()) {
			
			case PARTENZA:
				
				System.out.println("PARTITI");
				mappaPersone.replace(evento.getCountry(), mappaPersone.get(evento.getCountry()) - evento.getPersone());
				System.out.println("PAESE "+evento.getCountry()+" CON: "+mappaPersone.get(evento.getCountry()));
				int persone = (int)(evento.getPersone()/grafo.degreeOf(evento.getCountry()));
				int passo = evento.getPasso()+1;
				this.passo = passo;
				
				for(Country country : Graphs.neighborListOf(grafo, evento.getCountry())) {
					
					System.out.println("SCHEDULO ARRIVO NEL COUNTRY: "+country.toString());
					queue.add(new Evento(Tipo.ARRIVO,country,passo,persone));

				}
				
				break;

			case ARRIVO:
				
				System.out.println("ARRIVATI");
				mappaPersone.replace(evento.getCountry(), mappaPersone.get(evento.getCountry()) + evento.getPersone());
				System.out.println("PAESE "+evento.getCountry()+" CON: "+mappaPersone.get(evento.getCountry()));
				int noStanziale = evento.getPersone()/2;
				int divisore = grafo.degreeOf(evento.getCountry());
				
				if(noStanziale >= divisore) {
					
					System.out.println("SCHEDULO PARTENZA DAL COUNTRY: "+evento.getCountry().toString());
					queue.add(new Evento(Tipo.PARTENZA,evento.getCountry(),evento.getPasso(),noStanziale));
					
				}
				
				this.passo = evento.getPasso();
				
				break;
			}
		}
	}
	
	
}
