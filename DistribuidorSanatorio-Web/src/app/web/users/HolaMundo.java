package app.web.users;

import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import app.interfaces.base.FacadeHandlerLocal;
import app.schema.enumerated.Gender;
import app.schema.persons.Person;

@RequestScoped
@ManagedBean(name = "holaMundoPB")
public class HolaMundo {

	@EJB
	private FacadeHandlerLocal local;

	public void holaMundo() {
		Person persona = new Person();

		persona.setFirstName("Angel");
		persona.setSecondName("Alberto");
		persona.setSurname("Alfaro");
		persona.setSecondSurname("Herrera");
		persona.setBirthDay(new Date());
		persona.setGender(Gender.MALE);
		try {
			persona = local.createEntity(persona);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (persona != null) {
			System.out.println("-------------");
			System.out.println(persona.getID());
			System.out.println("-----------------");
		}
	}
}
