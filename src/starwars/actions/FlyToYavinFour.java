/**
 * starwars.actions package
 * 
 * Initiates actions that will be able to be initiated by SWActors in the Star Wars
 * roguelike game. This includes actions like Obey (the Force), TakeOwnership (of Droids),
 * Leave (an item) and so forth!
 *
 */
package starwars.actions;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.entities.Fillable;
import starwars.entities.MilleniumFalcon;
import starwars.entities.actors.Droid;
import starwars.entities.actors.Player;
import starwars.swinterfaces.SWGridController;
import starwars.worlds.YavinFour;

/**
 * Class for Fly to Yavin IV
 * 
 * The fly action will enable the SWActor (Player) the opportunity to travel to the moon
 * Yavin IV from either the Death Star or Tatooine
 * 
 * @author jas
 * @author mewc
 *
 */
public class FlyToYavinFour extends SWAffordance {

	SWLocation locTravelTo;
	
	EntityManager<SWEntityInterface, SWLocation> theEM;
	
	/**
	 * Constructor for the <code>Fly</code> class. 
	 * 
	 * @param theTarget 	- the Millenium Falcon being flown in (which is a SWEntity)
	 * @param m 	- the message renderer to display messages
	 */
	public FlyToYavinFour(SWEntityInterface theTarget, SWLocation Benloc, EntityManager<SWEntityInterface, SWLocation> em, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		this.locTravelTo = Benloc;
		this.theEM = em;
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * Public Method canDo(SWActor a)
	 *
	 * Returns a boolean exclaiming that the particular SWActor (a) is able to
	 * use Fly (within the Millenium Falcon
	 * 
	 * @param 	a	- The SWActor in question of being able to undertake this action
	 * 
	 * @return 	- Boolean (true) that exclaims this actor can undertake the action of
	 * flight in the Millenium Falcon
	 *
	 */
	@Override
	public boolean canDo(SWActor a) {
		return true;
	}
	
	/**
	 * Public Method act(SWActor a)
	 *
	 * Initiates the Fly process once option is selected from the same menu.
	 *
	 * 
	 * @param 	a	- The SWActor in question of being able to undertake the process of 
	 * flight.
	 *
	 */
	@Override
	public void act(SWActor a) {
		//If the entity trying to fly is Luke
		if (a instanceof Player) {
			
			//Get the Array List depicting the followers of Luke
			ArrayList<String> followersList = a.getFollowerList();
			
			//If no one is following Luke
			if (followersList.size() == 0)
			{
				a.say("No one is following me");
				
				a.say(a.getShortDescription() + " is on world " + a.getWorld().getWorldName() 
						+ " in the " + a.getWorld().getUniverse().getUniverseName());
				
				//Yavin IV is at index 1 of the universe world list.. obtain it
				SWWorld yavin = a.getWorld().getUniverse().getWorlds().get(1);
				a.say(yavin.getWorldName());
				
				
				/*
				//Set the world of luke to yavin IV
				a.setWorld(yavin);
				*/
				
				//Initially transport to 
				this.theEM.setLocation(a, this.locTravelTo);
			
			}
			
		}
		
	}

	/**
	 * public method getDescription()
	 * 
	 * Returns a string description of Fly. Used when showing the player they 
	 * are able to complete this action selected.
	 * 
	 * @return	-	String of action - implemented in game selection options.
	 *
	 */
	@Override
	public String getDescription() {
		return "Fly to Yavin IV";
	}
}

/*
REFERENCES

Javatpoint 2017, Java Switch Statement, viewed 10 May 2017,
https://www.javatpoint.com/java-switch 

Stack Overflow 2011, Getting random numbers in Java [duplicate], viewed 10 May 2017,
http://stackoverflow.com/questions/5887709/getting-random-numbers-in-java

Stack Overflow 2010, In Java, how do I check if a string contains a substring (ignoring case)? [duplicate], viewed 7 May 2017,
http://stackoverflow.com/questions/2275004/in-java-how-do-i-check-if-a-string-contains-a-substring-ignoring-case

Stack Overflow 2010, In Java how does one turn a String into a char or a char into a String?, viewed 10 April 2017,
http://stackoverflow.com/questions/2429228/in-java-how-does-one-turn-a-string-into-a-char-or-a-char-into-a-string

The Internet Movie Database 2017, Quotes for C-3PO (Character), viewed 10 April 2017,
http://www.imdb.com/character/ch0000048/quotes 

*/
