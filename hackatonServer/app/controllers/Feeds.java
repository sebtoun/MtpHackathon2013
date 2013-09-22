package controllers;

import java.util.List;

import models.Drop;
import models.UserAccount;
import play.mvc.Controller;
import play.mvc.Result;

public class Feeds extends Controller 
{
	
	public static Result listUserFeed(String nickname)
	{
		UserAccount user = UserAccount.findByNickname(nickname);
		
		List<Drop> drops = Drop.findByUser(user);
		return ok(views.html.drops.render(drops));
	}
	
}
