package starwars;
/**
 * Capabilities that various entities may have.  This is useful in <code>canDo()</code> methods of 
 * <code>SWActionInterface</code> implementations.
 *  
 * @author 	ram
 * @see 	SWActionInterface
 * @see     starwars.entities.Fillable
 */

public enum Capability {
	CHOPPER,//CHOPPER capability allows an entity to Chop another entity which has the Chop Affordance
	WEAPON,//WEAPON capability allows an entity to Attack another entity which has the Attack Affordance
	FILLABLE,//FILLABLE capability allows an entity to be refilled by another entity that 
	            // has the Dip affordance.  Any FILLABLE Entity MUST implement the Fillable interface
	DRINKABLE,//DRINKABLE capability allows an entity to be consumed by another entity 
	
	MIND_CONTROL, //MIND_CONTROL capability allows an entity on the light side of the force to attempt to mind control those without it
	CHOKE, //MIND_CONTROL capability allows an entity on the dark side of the force to  attempt to choke another
	HOME, //HOME allows it to be used at home - doesnt do anything yet
	INFLUENCE, //INFLUENCE allows one to sway another forceactor to their way of the force
}
