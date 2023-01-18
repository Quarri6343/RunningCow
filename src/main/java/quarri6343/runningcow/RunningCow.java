package quarri6343.runningcow;

import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public final class RunningCow extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void EntitySpawnEvent(EntitySpawnEvent event) {
        if(event.getEntity().getType() == EntityType.COW || event.getEntity().getType() == EntityType.MUSHROOM_COW){
            new BukkitRunnable(){
                
                Cow entity = (Cow) event.getEntity();
                @Override
                public void run() {
                    if(entity.isDead()
                            || entity.getHealth() == 0 
                            || entity.getScoreboardTags().contains("cow") 
                            || entity.getScoreboardTags().contains("mooshroom"))
                        cancel();

                    List<Entity> players = entity.getNearbyEntities(10, 10, 10).stream().filter(entity1 -> entity1 instanceof Player).collect(Collectors.toList());
                    if(players.size() == 0)
                        return;
                    
                    entity.damage(0, players.get(0));
                }
            }.runTaskTimer(this, 0, 100);
        }
    }
}
