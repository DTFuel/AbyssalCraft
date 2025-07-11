/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world;

import com.shinoow.abyssalcraft.api.dimension.IAbyssalWorldProvider;
import com.shinoow.abyssalcraft.client.render.sky.ACSkyRenderer;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAbyssalWasteland extends WorldProvider implements IAbyssalWorldProvider {

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorAbyssalWasteland(world, world.getSeed(), true);
	}

	@Override
	public void init() {
		biomeProvider = new BiomeProviderAbyssalWasteland(world.getSeed(), world.getWorldInfo().getTerrainType());
		setDimension(ACLib.abyssal_wasteland_id);
		//		hasNoSky = true;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	/**
	 * Creates the light to brightness table
	 */
	@Override
	protected void generateLightBrightnessTable() {
		if(ACConfig.hcdarkness_aw && Loader.isModLoaded("hardcoredarkness"))
			super.generateLightBrightnessTable();
		else {
			float f = 0.25F;

			for (int i = 0; i <= 15; ++i) {
				float f1 = 1.0F - i / 15.0F;
				lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
			}
		}
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public void updateWeather()
	{
		world.getWorldInfo().setRaining(false);
		world.rainingStrength = 0;
		world.getWorldInfo().setThundering(false);
		world.thunderingStrength = 0;
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3) {
		return 0.5F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float par1, float par2) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isSkyColored() {
		return true;
	}

	@Override
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		return new Vec3d(0, 4.5, 1.5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return 8.0F;
	}

	@Override
	public String getSaveFolder() {
		return "Abyssal_Wasteland";
	}

	@Override
	public int getAverageGroundLevel() {
		return 50;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public net.minecraftforge.client.IRenderHandler getSkyRenderer()
	{
		return new ACSkyRenderer(new ResourceLocation("abyssalcraft:textures/environment/abyssal_wasteland_sky.png"), ACClientVars.getAbyssalWastelandR(), ACClientVars.getAbyssalWastelandG(), ACClientVars.getAbyssalWastelandB());
	}

	@Override
	public DimensionType getDimensionType() {

		return ACLib.THE_ABYSSAL_WASTELAND;
	}
}
