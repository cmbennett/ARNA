package edu.ycp.cs481.shared.persistence;

import java.util.List;
import edu.ycp.cs481.arna.shared.model.POI;

public interface IDatabase {	
	/**
	 * Get the current notes (list of {@link Note}s.
	 * 
	 * @return the current notes (list of {@link Note}s
	 */
	public List<POI> getPOI();

	/**
	 * Add a {@link Note} to the database.
	 * 
	 * @param item the {@link Item} to add
	 */
	public void addPOI(POI poi);

	/**
	 * Delete specific note from the database.
	 */
	public void deletePOI(POI poi);

	/**
	 * Delete all notes from the database.
	 */
	public void deleteAllPOI();

}
