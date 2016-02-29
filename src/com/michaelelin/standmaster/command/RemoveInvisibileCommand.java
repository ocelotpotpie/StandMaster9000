package com.michaelelin.standmaster.command;

import java.util.Deque;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.michaelelin.standmaster.StandMasterException;

public class RemoveInvisibileCommand extends AbstractCommand {

	
	/**
	 * Construct a command to remove all nearby invisible armor stands
	 * 
     * @param name the command's name
     * @param description the command's description
	 */
	public RemoveInvisibileCommand(String name, String description) {
        super(name, description);
    }

	@Override
	public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }
        
        // Parse the arguments
        if (args.size() != 1) {
        	throw new StandMasterException(
        			String.format("USAGE: standmaster9000 %s RADIUS", this.getName()));
        }
        double radius = 0.0;
        try {
        	radius = Double.parseDouble(args.pop());
        } catch (NumberFormatException e) {
        	StandMasterException sme = new StandMasterException("The radius must be a number.");
        	sme.addSuppressed(e);
        	throw sme;
        }
        if (radius > 25.0 || radius < 0.0) {
        	throw new StandMasterException("Remove all armor stands in a radius of " + radius + "? LOL. No.");
        }
       
        Player player = (Player) sender;
        World world = player.getWorld();
        Location loc = player.getLocation();
        double radius2 = radius * radius;

        // Loop through ALL the entities in the entire world and find the closest ones
        // (Maybe this is over kill, but this seems to be the best but bukkit API has to offer
        for (Entity e : world.getEntities()) {
        	if (e instanceof ArmorStand) {
        		ArmorStand a = (ArmorStand)e;
        		if (!a.isVisible()) {
	        		if (a.getLocation().distanceSquared(loc) < radius2) {
	        			a.remove();
	        		}
        		}
        	}
        }
	}
}
