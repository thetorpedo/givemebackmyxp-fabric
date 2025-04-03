package torpedo.gmbmxp.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class CancelXPDrop {

	@Inject(method = "getExperienceToDrop", at = @At("HEAD"), cancellable = true)
	private void modifyXpDrop(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}
}