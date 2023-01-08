/*package tallestegg.illagersweararmor.client.renderer.mod_compat;

import baguchan.enchantwithmob.client.ModModelLayers;
import baguchan.enchantwithmob.entity.EnchanterEntity;
import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.Skirmisher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import tallestegg.illagersweararmor.IWAClientEvents;
import tallestegg.illagersweararmor.client.model.IllagerArmorModel;
import tallestegg.illagersweararmor.client.model.mod_compat.EnchanterBipedModel;
import tallestegg.illagersweararmor.client.model.mod_compat.SkirmisherBipedModel;
import tallestegg.illagersweararmor.client.renderer.IllagerBipedRenderer;

public class SkirmisherBipedRenderer extends MobRenderer<Skirmisher, SkirmisherBipedModel<Skirmisher>> {
    private static final ResourceLocation LEGIONER = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/skirmisher.png");

    public SkirmisherBipedRenderer(EntityRendererProvider.Context builder) {
        super(builder, new SkirmisherBipedModel<>(builder.bakeLayer(IWAClientEvents.SKRIMISHER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, builder.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new IllagerArmorModel<>(builder.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_INNER_LAYER)),
                new IllagerArmorModel<>(builder.bakeLayer(IWAClientEvents.BIPEDILLAGER_ARMOR_OUTER_LAYER))));
    }

    @Override
    public ResourceLocation getTextureLocation(Skirmisher legioner) {
        return LEGIONER;
    }
}*/
