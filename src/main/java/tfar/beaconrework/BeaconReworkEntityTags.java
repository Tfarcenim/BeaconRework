package tfar.beaconrework;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class BeaconReworkEntityTags {

    public static final TagKey<EntityType<?>> BLACKLISTED = create("blacklisted");
    private static TagKey<EntityType<?>> create(String pName) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, BeaconRework.id(pName));
    }

}
