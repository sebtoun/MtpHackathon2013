package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class DropOff extends Model
{
	@Id
	public Long idOSM;
	
	public float latitude ;
	public float longitude ;
	
	public static Finder<Long, DropOff> find = new Finder<Long, DropOff>(Long.class, DropOff.class);
	
	/**
	 * Récupère dans la BDD le dropoff dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static DropOff findById(String id)
	{
		return findById(Long.parseLong(id));
	}

	/**
	 * Récupère dans la BDD le dropoff dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static DropOff findById(Long id)
	{
		return find.byId(id);
	}
}
