package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;

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

	public DropOff getDropOff() {
		return dropOff;
	}

	public void setDropOff(DropOff dropOff) {
		this.dropOff = dropOff;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}
	
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	/**
	 * Enregistre en base de données le dropOff passé en paramètre
	 * @param userAccount
	 */
	public static void create(Drop drop)
	{
		Ebean.save(drop);
	}

	/**
	 * Met à jour dans la BDD le dropOff passé en paramètre
	 * @param userAccount
	 */
	public static void update(Drop drop)
	{
		Ebean.save(drop);
	}
	
	public static List<Drop> findByUser(UserAccount user)
	{
		return find.where().eq("user", user).findList();
	}
}