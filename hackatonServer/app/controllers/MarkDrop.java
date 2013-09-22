package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import models.Drop;
import play.data.DynamicForm;
import play.data.Form;


public class MarkDrop extends Controller {

	public static Result like(){
		DynamicForm requestData = Form.form().bindFromRequest();
		Long dropId = Long.parseLong(requestData.get("dropId"));
		Drop drop = Drop.findById(dropId);
		drop.like();
		Drop.update(drop);
		return ok();
	}

	public static Result dislike(){
		DynamicForm requestData = Form.form().bindFromRequest();
		Long dropId = Long.parseLong(requestData.get("dropId"));
		Drop drop = Drop.findById(dropId);
		drop.dislike();
		Drop.update(drop);
		return ok();
	}
}