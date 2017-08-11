package app.web.users;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import app.interfaces.base.FacadeHandlerLocal;
import app.security.User;

@RequestScoped
@ManagedBean(name = "holaMundoPB")
public class HolaMundo {

	@EJB
	private FacadeHandlerLocal local;

	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void holaMundo() {
		System.out.println("---------------");
		System.out.println(user.getUserName());
		System.out.println(user.getPassWord());
		System.out.println("---------------");
//		Person persona = new Person();
//
//		persona.setFirstName("Angel");
//		persona.setSecondName("Alberto");
//		persona.setSurname("Alfaro");
//		persona.setSecondSurname("Herrera");
//		persona.setBirthDay(new Date());
//		persona.setGender(Gender.MALE);
//		try {
//			persona = local.createEntity(persona);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (persona != null) {
//			System.out.println("-------------");
//			System.out.println(persona.getID());
//			System.out.println("-----------------");
//		}
	}
}
