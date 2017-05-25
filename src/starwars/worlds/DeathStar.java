package starwars.worlds;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.actions.FlyToDeathStar;
import starwars.actions.FlyToTatooine;
import starwars.actions.FlyToYavinFour;
import starwars.entities.*;
import starwars.entities.actors.*;

/**
 * Class representing the Death Star 'world' in the Star Wars universe. This spacecraft houses
 * the evil Darth Vader and various Stormtrppoers - who patrol in a random like fashion. Princess
 * Leia is also trapped on the Death Star - hoping Luke will come in her aid.
 * 
 * @author ram
 * @author jas
 * @author mewc
 */

public class DeathStar extends SWWorld {

	/**
	 * Constructor of <code>DeathStar</code>. This will initialize the <code>SWLocationMaker</code>
	 * and the grid for setup of the Death Star instance.
	 * 
	 * @param 	inUniverse		The universe into which this Death Star instance belongs to.
	 */
	public DeathStar(SWUniverse inUniverse) {
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		myGrid = new SWGrid(10,10, factory);
		space = myGrid;
		name = "The Death Star";
		universe = inUniverse;
	}

	/**
	 * Set up the Death Star, setting descriptions for locations and placing items and actors
	 * on the grid.
	 *
	 * @param iface a MessageRenderer to be passed onto newly-created entities
	 * @author ram
	 */
	public void initializeWorld(MessageRenderer iface) {
		SWLocation loc;
		// Set default location string
		for (int row = 0; row < height(); row++) {
			for (int col = 0; col < width(); col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription(name + " (" + col + ", " + row + ")");
				loc.setShortDescription(name + " (" + col + ", " + row + ")");
				loc.setSymbol('x');
			}
		}


		Direction[] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
				CompassBearing.SOUTH,
				CompassBearing.WEST, CompassBearing.WEST,
				CompassBearing.SOUTH,
				CompassBearing.EAST, CompassBearing.EAST,
				CompassBearing.NORTHWEST, CompassBearing.NORTHWEST};

        // The Millenium Falcon
        MilleniumFalcon millFalcDS = new MilleniumFalcon(iface);
        loc = myGrid.getLocationByCoordinates(0, 0);
        entityManager.setLocation(millFalcDS, loc);
        
        //Append the MF location to the MF Location list in SWUNiverse
        this.universe.getMFList().add(loc);
        millFalcDS.addAffordance(new FlyToYavinFour(millFalcDS, this.getEntityManager(), iface));
        millFalcDS.addAffordance(new FlyToTatooine(millFalcDS, this.getEntityManager(), iface));
        
        PrincessLeia leia = PrincessLeia.getPrincessLeia(iface, this, patrolmoves);
        leia.setSymbol("L");
        loc = myGrid.getLocationByCoordinates(9, 9);
        entityManager.setLocation(leia, loc);
 

        DarthVader dv = DarthVader.getDarthVader(iface, this, patrolmoves);
        loc = myGrid.getLocationByCoordinates(4, 4);
        entityManager.setLocation(dv, loc);
        dv.setSymbol("V");
        dv.setHitpoints(1);
        
        StormTrooper st = new StormTrooper(iface, this);

        loc = myGrid.getLocationByCoordinates(2, 2);
        entityManager.setLocation(st, loc);

        StormTrooper st4 = new StormTrooper(iface, this);

        loc = myGrid.getLocationByCoordinates(6, 6);
        entityManager.setLocation(st4, loc);

        StormTrooper st3 = new StormTrooper(iface, this);
        loc = myGrid.getLocationByCoordinates(2, 6);
        entityManager.setLocation(st3, loc);

        StormTrooper st2 = new StormTrooper(iface, this);
        loc = myGrid.getLocationByCoordinates(6, 2);
        entityManager.setLocation(st2, loc);

        StormTrooper st1 = new StormTrooper(iface, this);
        loc = myGrid.getLocationByCoordinates(1, 9);
        entityManager.setLocation(st1, loc);
    }

}

	
