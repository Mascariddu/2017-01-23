package it.polito.tdp.borders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;
import it.polito.tdp.borders.model.Country;
import javafx.scene.input.ClipboardContent;

public class Model {
	
	BordersDAO dao;
	List<Country> vertex;
	SimpleGraph<Country, DefaultEdge> grafo;
	HashMap<Integer, Country> idMap;
	List<Adiacenza> adiacenze;
	private int passo = 0;
	private HashMap<Country, Integer> mappa;

	public Model() {
		
		dao = new BordersDAO();
		idMap = new HashMap<Integer, Country>();
		vertex = new ArrayList<Country>(dao.loadAllCountries(idMap));
		mappa = new HashMap<Country, Integer>();
		
	}
	
	public void creaGrafo(int anno) {
		
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(grafo, vertex);
		adiacenze = dao.getAdiacenze(anno);
		
		for(Adiacenza a : adiacenze) {
			
			Country source = idMap.get(a.getStato1());
			Country target = idMap.get(a.getStato2());
			
			if(grafo.getEdge(target, source) == null || grafo.getEdge(source, target) == null)
				grafo.addEdge(source, target);
			
		}
		
		System.out.println("#vertici : "+grafo.vertexSet().size());
		System.out.println("#archi : "+grafo.edgeSet().size());
	}

	public List<Country> getConfini() {
		// TODO Auto-generated method stub
		List<Country> result = new ArrayList<Country>();
		
		for(Country country : grafo.vertexSet()) {
			
			country.setNumeroConfinanti(grafo.degreeOf(country));
			result.add(country);
			
		}
		
		Collections.sort(result, new Comparator<Country>() {

			@Override
			public int compare(Country o1, Country o2) {
				// TODO Auto-generated method stub
				return -(o1.getNumeroConfinanti() - o2.getNumeroConfinanti());
			}
		});
		
		return result;
	}

	public List<Country> getVertex() {
		// TODO Auto-generated method stub
		return this.vertex;
	}

	public void simula(Country country) {
		// TODO Auto-generated method stub
		Simulatore sim = new Simulatore(this.grafo);
		
		sim.init(country);
		
		sim.run();
		
		this.passo = sim.passo;
		this.mappa = sim.mappaPersone;
		
	}

	public List<String> getPersone() {
		// TODO Auto-generated method stub
		List<String> result = new ArrayList<String>();
		List<Country> paesi = new ArrayList<Country>();
		
		for(Country country : mappa.keySet()) {
			
			if(mappa.get(country) > 0)
				paesi.add(country);
			
		}
		
		Collections.sort(paesi, new Comparator<Country>() {

			@Override
			public int compare(Country o1, Country o2) {
				// TODO Auto-generated method stub
				return -(mappa.get(o1)-mappa.get(o2));
			}
		});
		
		for(Country country : paesi)
			result.add(country.getStateAbb()+", "+country.getStateName()+", "+mappa.get(country));
		
		return result;
	}

	public int getPasso() {
		// TODO Auto-generated method stub
		return passo;
	}
}
