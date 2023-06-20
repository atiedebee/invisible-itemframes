package me.atie.inv_itemframes.mixin;

import me.atie.inv_itemframes.InvisibleItemFrames;
import me.atie.inv_itemframes.onItemFramePop;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemFrameEntity.class)
public class ItemFrameEntityMixin {

	@Inject(
		method = "interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;setHeldItemStack(Lnet/minecraft/item/ItemStack;)V"),
		locals = LocalCapture.CAPTURE_FAILHARD)
	private void makeItemFrameInvisible(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir, ItemStack itemStack){
		if( player.getPose().equals(EntityPose.CROUCHING) ){
			((ItemFrameEntity)(Object)this).setInvisible(true);
		}
	}
	@Inject(
			method = "removeFromFrame(Lnet/minecraft/item/ItemStack;)V",
			at = @At(value = "TAIL")
	)
	private void onItemFrameDrop(CallbackInfo ci){
		ItemFrameEntity self = (ItemFrameEntity)(Object)this;
		if( self.isInvisible() ) {
			onItemFramePop onPop = self.getEntityWorld().getGameRules().get(InvisibleItemFrames.ON_ITEM_FRAME_POP).get();
			switch( onPop ){
				case DESTROY:
					self.setInvisible(false);// Make sure we dont call this recursively
					self.dropStack(new ItemStack(Items.ITEM_FRAME)); // drop the item frame itself
					self.kill();
					break;
				case REVEAL:
					self.setInvisible(false);
					break;
				case NOTHING:
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + onPop);
			}

		}
	}
}