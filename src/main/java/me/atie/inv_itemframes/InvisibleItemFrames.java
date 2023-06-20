package me.atie.inv_itemframes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.world.GameRules;


public class InvisibleItemFrames implements ModInitializer {
	public static final GameRules.Key<EnumRule<onItemFramePop>> ON_ITEM_FRAME_POP =
			GameRuleRegistry.register("onInvisibleItemFramePop",
					GameRules.Category.DROPS,
					GameRuleFactory.createEnumRule(onItemFramePop.REVEAL)
			);


	@Override
	public void onInitialize() {
	}
}