import java.lang.reflect.Method;

import controllers.routes;
import play.GlobalSettings;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Result;


public class Global extends GlobalSettings 
{
	@Override
    public Action onRequest(Request request, Method actionMethod)
    {
		/*if(Controller.session("access_token_expires") != null && Controller.session("access_token") != null)
		{
			// on vérifie que le token n'ait pas expiré
			if(Long.parseLong(Controller.session("access_token_expires")) <= System.currentTimeMillis())
			{
				return new Action.Simple() 
                {
                        @Override
                        public Result call(Context arg0) throws Throwable 
                        {
                        		Controller.session().clear();
                                return Controller.redirect(routes.Application.loginInWithFacebook());
                        }
                };
			}
		}*/
		
        return super.onRequest(request, actionMethod);
    }
}
