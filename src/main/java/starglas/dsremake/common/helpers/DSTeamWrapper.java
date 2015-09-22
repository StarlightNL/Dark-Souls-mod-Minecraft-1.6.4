package starglas.dsremake.common.helpers;

import com.sun.istack.internal.NotNull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.World;
import starglas.dsremake.handler.ExtendedPlayer;

/**
 * Created by Starlight on 20-9-2015.
 */
public class DSTeamWrapper{

    public static void registerPlayerToTeam(EntityPlayer player, String teamName){
        String playerName = player.getDisplayName();
        World world = player.getEntityWorld();
        Scoreboard scoreboard = world.getScoreboard();
        if(scoreboard.getTeam(teamName)==null){
            if(!world.isRemote) {
                scoreboard.createTeam(teamName);
                System.out.println(String.format("Created team %s", teamName));
            }
        }
        else {
            System.out.println("Team " + teamName + " exists");


            if (player.getTeam() != null && !(player.getTeam().getRegisteredName().equals(teamName))) {
                System.out.println("Player is in a Team");
                String currentPlayerTeam = player.getTeam().getRegisteredName();
                System.out.println(player.getTeam().getRegisteredName());
                //if(!world.isRemote) {
                System.out.println(currentPlayerTeam);
                scoreboard.getPlayersTeam(playerName).getMembershipCollection().remove(playerName);
                if(!world.isRemote) {
                    // Following code NEEDS to be in !world.isRemote because else we crash because of a silly reason which is that
                    // scoreboard can't remove the player because it already was removed after first line of the code in this if block
                    scoreboard.removePlayerFromTeams(playerName);
                    scoreboard.func_151392_a(playerName, teamName);
                }
                scoreboard.getTeam(teamName).getMembershipCollection().add(playerName);
                //}
                ModHelper.displayChat("Removed %s from team %s and joined team %s?", player.getDisplayName(), currentPlayerTeam, teamName);
            } else if (player.getTeam() != null && player.getTeam().getRegisteredName().equals(teamName)) {
                ModHelper.displayChat(String.format("Player %s already is in team %s", playerName, teamName));
            } else if (player.getTeam() == null) {
                scoreboard.getTeam(teamName).getMembershipCollection().add(playerName);
                scoreboard.func_151392_a(playerName, teamName); // Here we can use this without !world.isRemote
                ModHelper.displayChat("Added %s to team %s", playerName, teamName);
            }
        }
    }

    public void setPlayerTeam(EntityPlayer player, String teamName){
        // TODO: Create method for wrapper of the registerPlayerToTeam function

    }

    public static void setTeamSuffix(@NotNull ScorePlayerTeam team, String suffix){
        team.setNameSuffix(suffix);
    }
    public static void setTeamPrefix(@NotNull ScorePlayerTeam team, String prefix){
        team.setNamePrefix(prefix);
    }
    public static void setCoopTeam(EntityPlayer player){
        // TODO: Make sure that this method works the first time. Apperently this isn't the case :(
        String playerName = player.getDisplayName();
        String teamName = playerName.hashCode()+"";
        ExtendedPlayer props = ExtendedPlayer.get(player);
        if(!props.getPlayerTeam().equals("none")) {
            props.setPlayerTeam(teamName);
            registerPlayerToTeam(player, teamName);
        }
        ModHelper.displayChat("%s is already in a CO-OP session", playerName);
    }
    public static void removeCoopTeam(EntityPlayer player){ // Removes player from CO-OP team and put player back in default/no team
        // TODO: Make sure this method does not create a error at last line
        ExtendedPlayer props = ExtendedPlayer.get(player);
        String playerTempTeam = props.getPlayerTeam();
        World world = player.getEntityWorld();
        Scoreboard scoreboard = world.getScoreboard();
        if(!world.isRemote) {
            // Following code NEEDS to be in !world.isRemote because else we crash because of a silly reason which is that
            // scoreboard can't remove the player because it already was removed after first line of the code in this if block
            scoreboard.removePlayerFromTeams(player.getDisplayName());
        }
        props.setPlayerTeam("none"); // Make sure that the player data says its none or else it won't save and player can't join other team
    }

    public static void addPlayersToCoopTeam(EntityPlayer player){
        ExtendedPlayer props = ExtendedPlayer.get(player);
        String playerTempTeam = props.getPlayerTeam();
        World world = player.getEntityWorld();
        Scoreboard scoreboard = world.getScoreboard();

    }



}
