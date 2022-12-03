package tallestegg.illagersweararmor.client.renderer.mod_compat;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.Archer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import tallestegg.illagersweararmor.client.renderer.IllagerBipedRenderer;

public class ArcherBipedRenderer extends IllagerBipedRenderer<Archer> {
    private static final ResourceLocation ARCHER = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/archer.png");

    public ArcherBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Archer archer) {
        return ARCHER;
    }
}
