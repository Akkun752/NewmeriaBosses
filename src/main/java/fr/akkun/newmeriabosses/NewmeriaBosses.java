package fr.akkun.newmeriabosses;

import com.mojang.logging.LogUtils;
import fr.akkun.newmeriabosses.saphira.Saphira;
import fr.akkun.newmeriabosses.saphira.SaphiraSummoning;
import fr.akkun.newmeriacore.NewmeriaCore;
import fr.akkun.newmeriacore.entity.ModEntityTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import org.slf4j.Logger;

/**
 * The bosses of Newmeria: Saphira. Installed on the server and on the players' computers.
 *
 * <p>Like the items of NewmeriaRPG, the bosses are registered by NewmeriaCore (entity type, eggs, names,
 * recipes): this mod only brings their code and look. For Saphira, it tells NewmeriaCore to create a
 * {@link Saphira} for her entity type.
 */
// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NewmeriaBosses.MOD_ID)
public class NewmeriaBosses {
    public static final String MOD_ID = "newmeriabosses";
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Namespace of the ids saved in worlds that this mod took over from NewmeriaCore (the data attachment
     * of {@link SaphiraSummoning}): renaming it would forget a summoning ritual in progress.
     */
    public static final String LEGACY_NAMESPACE = NewmeriaCore.MOD_ID;

    public NewmeriaBosses(IEventBus modEventBus, ModContainer modContainer) {
        ModEntityTypes.setSaphiraFactory(Saphira::new);
        SaphiraSummoning.register(modEventBus);
        modEventBus.addListener(NewmeriaBosses::onEntityAttributeModification);
    }

    private static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(ModEntityTypes.SAPHIRA.get(), Attributes.MAX_HEALTH, Saphira.MAX_HEALTH);
    }
}
