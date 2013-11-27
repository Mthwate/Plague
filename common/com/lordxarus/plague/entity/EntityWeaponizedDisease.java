package com.lordxarus.plague.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.lordxarus.plague.Plague;
import com.lordxarus.plague.disease.Disease;
import com.lordxarus.plague.lib.DiseaseHelper;

public class EntityWeaponizedDisease extends EntityThrowable {

	public EntityWeaponizedDisease(World par1World) {
		super(par1World);
	}

	public EntityWeaponizedDisease(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityWeaponizedDisease(World par1World,
			EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}

	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if (par1MovingObjectPosition.entityHit != null) {
			if (par1MovingObjectPosition.entityHit instanceof EntityBlaze) {}

			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 1);
		}

		String diseaseName = getEntityData().getString("disease");

		Disease weaponDisease = DiseaseHelper.getDiseaseFromString(diseaseName);

		double effectiveness = getEntityData().getDouble("effectiveness");
		double radius = effectiveness / 10;

		@SuppressWarnings("unchecked")
		List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(radius, radius, radius));
		for (Entity entity : entities) {
			if (weaponDisease != null && Plague.rand.nextInt(100) < effectiveness) {
				DiseaseHelper.addDisease(entity, weaponDisease);
			}
		}

		if (!worldObj.isRemote) {
			setDead();
		}
	}
}