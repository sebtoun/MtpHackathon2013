package models;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Photo extends Model
{
	@Id
	public Long id;
	
	public URL url;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}
	
	public static Finder<Long, Photo> find = new Finder<Long, Photo>(Long.class, Photo.class);
	
	/**
	 * Récupère dans la BDD le dropoff dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static Photo findById(String id)
	{
		return findById(Long.parseLong(id));
	}

	/**
	 * Récupère dans la BDD le dropoff dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static Photo findById(Long id)
	{
		return find.byId(id);
	}
	
	/**
	 * Enregistre en base de données la photo passée en paramètre
	 * @param userAccount
	 */
	public static void create(Photo photo)
	{
		Ebean.save(photo);
	}

	/**
	 * Met à jour dans la BDD la photo passée en paramètre
	 * @param userAccount
	 */
	public static void update(Photo photo)
	{
		Ebean.save(photo);
	}
}