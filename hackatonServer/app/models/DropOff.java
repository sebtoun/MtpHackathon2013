package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class DropOff extends Model
{
	@Id
	public Long id;
	
	/**
	 * ID from OSM. If equals -1 not from OSM.
	 */
	public Long idOSM;
	
	public float latitude ;
	public float longitude ;
	
	public List<Item> dropable;
	
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
	
	public static DropOff findByIdOSM(Long idOSM)
	{
		return find.where().eq("idOSM", idOSM).findUnique();
	}
	
	/**
	 * Enregistre en base de données le dropOff passé en paramètre
	 * @param userAccount
	 */
	public static void create(DropOff doff)
	{
		Ebean.save(doff);
	}

	/**
	 * Met à jour dans la BDD le dropOff passé en paramètre
	 * @param userAccount
	 */
	public static void update(DropOff doff)
	{
		Ebean.save(doff);
	}

	public Long getId() {
		return id;
	}
	
	public Long getIdOSM() {
		return idOSM;
	}

	public void setIdOSM(Long idOSM) {
		this.idOSM = idOSM;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public List<Item> getDropable() {
		return dropable;
	}

	public void setDropable(List<Item> dropable) {
		this.dropable = dropable;
	}
}