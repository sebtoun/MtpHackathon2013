package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Item extends Model
{
	@Id
	public Long id;
	
	public String label;
	
	public static Finder<Long, Item> find = new Finder<Long, Item>(Long.class, Item.class);
	
	/**
	 * Récupère dans la BDD l'Item dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static Item findById(String id)
	{
		return findById(Long.parseLong(id));
	}

	/**
	 * Récupère dans la BDD l'Item dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static Item findById(Long id)
	{
		return find.byId(id);
	}
}
