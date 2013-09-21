package models;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Photo extends Model
{
	@Id
	public Long id;
	
	public URL url;
}
