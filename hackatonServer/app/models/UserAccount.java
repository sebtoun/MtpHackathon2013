package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

import com.avaje.ebean.Ebean;

@Entity
public class UserAccount extends Model implements Subject
{
	@Id
	public Long id;

	/**
	 * Login
	 */
	public String nickname ;
	
	/**
	 * Email
	 */
	public String email ;

	/**
	 * Date de création de l'utilisateur
	 */
	public Date creationDate;


	public static Finder<Long, UserAccount> find = new Finder<Long, UserAccount>(Long.class, UserAccount.class);

	public UserAccount()
	{
	}

	public String getNickname() 
	{
		return nickname;
	}

	public void setNickname(String nickname) 
	{
		this.nickname = nickname;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	/**
	 * Renvoi la liste de tous les utilisateurs enregistrés
	 * @return
	 */
	public static List<UserAccount> all()
	{
		return find.all();
	}

	/**
	 * Enregistre en base de données l'utilisateur passé en paramètre
	 * @param userAccount
	 */
	public static void create(UserAccount userAccount)
	{
		Ebean.save(userAccount);
	}

	/**
	 * Met à jour dans la BDD l'utilisateur passé en paramètre
	 * @param userAccount
	 */
	public static void update(UserAccount userAccount)
	{
		Ebean.save(userAccount);
	}

	/**
	 * Récupère dans la BDD l'utilisateur dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static UserAccount findById(String id)
	{
		return findById(Long.parseLong(id));
	}

	/**
	 * Récupère dans la BDD l'utilisateur dont l'identifiant a été passé en paramètre
	 * @param id
	 * @return
	 */
	public static UserAccount findById(Long id)
	{
		return find.byId(id);
	}
	/**
	 * Récupère l'utilisateur dans la base de données dont le mail est celui passé en paramètre
	 * @param mail
	 * @return
	 */
	public static UserAccount findByMail(String mail)
	{
		return find.where().eq("email", mail).findUnique();
	}

	/**
	 * Récupère dans la BDD l'utilisateur dont le nickname a été passé en paramètre
	 * @param nickname
	 * @return
	 */
	public static UserAccount findByNickname(String nickname)
	{
		return find.where().eq("nickname", nickname).findUnique();
	}
	/**
	 * Renvoi vrai si l'utilisateur passé en paramètre est identique, faux sinon
	 * @param user
	 * @return
	 */
	public boolean isSameUser(UserAccount user)
	{
		return user.id.equals(this.id);
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

	public void setId(Long id) {
		this.id = id;
	}
}
