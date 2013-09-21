package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Content extends Model
{
	@Id
	public Long id;
	
	public List<Item> items;
	
	public static Finder<Long, Content> find = new Finder<Long, Content>(Long.class, Content.class);
	
	/**
	 * Récupère dans la BDD le content dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static Content findById(String id)
	{
		return findById(Long.parseLong(id));
	}

	/**
	 * Récupère dans la BDD le content dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static Content findById(Long id)
	{
		return find.byId(id);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	/**
	 * Enregistre en base de données le content passé en paramètre
	 * @param userAccount
	 */
	public static void create(Content content)
	{
		Ebean.save(content);
	}

	/**
	 * Met à jour dans la BDD le content passé en paramètre
	 * @param userAccount
	 */
	public static void update(Content content)
	{
		Ebean.save(content);
	}
}
