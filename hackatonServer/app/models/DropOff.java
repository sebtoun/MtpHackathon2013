package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class DropOff extends Model
{
	@Id
	public Long idOSM;
}
