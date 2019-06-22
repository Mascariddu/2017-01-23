package it.polito.tdp.borders.db;

import it.polito.tdp.borders.Adiacenza;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BordersDAO {
	
	public List<Country> loadAllCountries(HashMap<Integer, Country> idMap) {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
				idMap.put(Integer.parseInt(rs.getString("ccode")), c);
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}

	public List<Adiacenza> getAdiacenze(int anno) {
		String sql ="SELECT c.state1no as c1,c.state2no as c2 FROM contiguity c WHERE YEAR < ? AND c.conttype = 1";

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery() ;
			
			List<Adiacenza> list = new LinkedList<Adiacenza>() ;
			
			while( rs.next() ) {
				
				Adiacenza adiacenza = new Adiacenza(rs.getInt("c1"), rs.getInt("c2"));
				
				list.add(adiacenza) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	
}
