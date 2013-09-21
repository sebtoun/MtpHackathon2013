package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Drop extends Model
{
	@Id
	public Long id;
	
	public DropOff dropOff;
	
	public UserAccount user;
	
	public Photo photo ;
	
	public Content content;
	
	/**
	 * Date de drop
	 */
	public Date creationDate;
	
	public static Finder<Long, Drop> find = new Finder<Long, Drop>(Long.class, Drop.class);
	
	/**
	 * Récupère dans la BDD le drop dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static Drop findById(String id)
	{
		return findById(Long.parseLong(id));
	}

	/**
	 * Récupère dans la BDD le drop dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static Drop findById(Long id)
	{
		return find.byId(id);
	}
}
