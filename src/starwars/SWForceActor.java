package starwars;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Choke;

import starwars.entities.Force;

/**
 * This class represents "FORCE ACTORS" whom are people posessing the power of the force
 * These people are able to perform the obey command on weak minded people (non force posessors) 
 * You start with a force power of 5 and are able to train that up as you progress to unlock certain traits like
 * being able to pweform MindControl and wielding a lightsabre
 * @author Michael Carter
 *
 */
public abstract class SWForceActor extends SWActor implements SWForceEntityInterface {

	
	
	/**The set actors entity of the <code>Force </code> of this <code>SWActor</code>*/
	private Force force = null;
	private int influence = 0;		//influence is only for the person, not the entity "force"

	private String[] lightTitles = {" the Wanderer"," the Enlightened ", " the Jedi ", " the Master Jedi ", " the CHOSEN ONE "};
	private String[] darkTitles = {" the Corrupt"," the Descended ", " the Degenerate ", " the Sith ", " the SITH LORD "};

	/** 
	 * Protected constructor to prevent random other code
	 * 
	 * @param team
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	
	protected SWForceActor(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
		Force defaultForce = new Force(m, 5);
		setForce(defaultForce);
		setInfluence(20);
		for (Affordance affEntity : this.getAffordances()) {
			if (affEntity.getDescription().contains("obey")) {
				this.removeAffordance(affEntity);		//removes the obey affordance from actor
			}
		}
		estSideOfForce();

		
	}



	/**
	 * Returns  the  <code>Title</code> of this Force Actor according to their forcePower.
	 * 
	 * @return 	the string of the force actors name 
	 */
	@Override
	public String getShortDescription() {
		
		return super.getShortDescription() + this.getTitle() ;
	}
	




	/**
	 * Returns the points of the <code>Force</code> in terms of strength
	 * 
	 * @return 	the boolean of force presence in an <code>SWActor</code> 
	 * @see 	#force
	 */
	
	public int getForcePower() {
		return (force != null)? force.getPower() : -1;
	}

	/**
	 * Returns the points of the <code>Force</code> in terms of strength
	 *
	 * @return 	the boolean of force presence in an <code>SWActor</code>
	 * @see 	#force
	 */

	public int getForceCharge() {
		return (force != null)? force.getCharge() : -1;
	}

	/**
	 * Reduces the the points of the <code>Charge</code> that allows use of mind control/choke
	 *
	 * @see 	#force
	 */

	public void useCharge(int chargeUsed) {
		this.force.useCharge(chargeUsed);
	}
	
	
	/**
	 * Sets the <code>Force</code> of the <code>SWActor</code>.
	 * <p>
	 * Useful when the <code>SWActor</code>'s team needs to change dynamically during the simulation.
	 * For example, a bite from an evil actor makes a good actor bad.
	 *
	 * @param 	force the force of this <code>SWActor</code>
	 * @see 	#force
	 */
	protected void setForce(Force force) {
		this.force = force;
	}                                                                                                       
	
	
	/**
	 * Incrememts the <code>ForcePower</code> in terms of strength
	 * 
	 * @see 	#force
	 * @see 	#influence
	 */
	public void trainForce(){	
			force.trainPower();	
	}	


	
	/**
	 * Returns the string of the SWForceActors title
	 * 
	 * @see 	#force
	 * @see 	#influence
	 */
	protected String getTitle(){
		if(getInfluence() > 0){
			return lightTitles[getForcePower() / 20];
		}else{
			return darkTitles[getForcePower() / 20];
		}

	}
	
	//needed for moving other players on mind control
	public Scheduler getScheduler(){
		return scheduler;
	}

	public int getInfluence() {
		return influence;
	}

	public String getInfluenceString() {
		return (influence>0)?"+"+influence:"-"+influence;
	}


	public void influence(int influence) {

		if((influence + this.getInfluence()) <= LIGHTSIDE_MAX ||
				(influence + this.getInfluence()) >= DARKSIDE_MAX){
			this.influence += (influence < 0)?influence *2:influence;//dark side is more corrupting but more seductive and easy
		}

		if(this.influence < 10 && this.influence > 0){
			say(this.getShortDescription() + " has almost been swayed to the DARK SIDE (" + this.influence + ")");
		}
		estSideOfForce();

	}

	protected void setInfluence(int influence) {
		if((influence + this.getInfluence()) <= LIGHTSIDE_MAX ||
				(influence + this.getInfluence()) >= DARKSIDE_MAX){
			this.influence += influence;
		}

	}

	/**
	 * When a force actor gets influenced, or starts off with a particular affinity to a side of the force,
	 * you will then
	 *
	 */
	protected void estSideOfForce(){
		//this is real ugly,TODO fix ugliness
		//Check each time any influence to ones affinity happens.
		if(influence >= 0){	//when influence is positive, have light side powers enabled
			for (Affordance affEntity : this.getAffordances()) {
				if (affEntity.getDescription().contains("choke")) {	//only triggered when going from dark to light
					this.removeAffordance(affEntity);		//removes the obey affordance from actor
					force.capabilities.remove(Capability.CHOKE);
					force.capabilities.add(Capability.MIND_CONTROL);   // and WEAPON so that it can be used to attack

				}
			}
		}else{  //when influence is negative, have darkside powers enabled
			for (Affordance affEntity : this.getAffordances()) {
				if (affEntity.getDescription().contains("mind")) {	//when theyre already on the darkside, skip
					this.addAffordance(affEntity);		//removes the obey affordance from actor
					force.capabilities.add(Capability.CHOKE);
					force.capabilities.remove(Capability.MIND_CONTROL);   // and WEAPON so that it can be used to attack

				}
			}
		}
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
