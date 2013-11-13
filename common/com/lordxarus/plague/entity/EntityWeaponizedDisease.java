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
import com.mthwate.bookcase.Rand;

public class EntityWeaponizedDisease extends EntityThrowable {
	
	public EntityWeaponizedDisease(World par1World) {
		super(par1World);
	}

	public EntityWeaponizedDisease(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}

	public EntityWeaponizedDisease(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if (par1MovingObjectPosition.entityHit != null) {
			byte b0 = 0;

			if (par1MovingObjectPosition.entityHit instanceof EntityBlaze) {
				b0 = 3;
			}

			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1);
		}
		
		String diseaseName = this.getEntityData().getString("disease");
		
		Disease weaponDisease = null;
		
		for(Disease disease : Plague.diseases) {
			if(disease.getUnlocalizedName() == diseaseName) {
				weaponDisease = disease;
			}
		}
		
		double effectiveness = this.getEntityData().getDouble("effectiveness");
		double radius = effectiveness/10;
		
		
		List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(radius, radius, radius));
		for(Entity entity : entities) {
			if ((weaponDisease != null) && Rand.nextInt(100) < effectiveness) {
				DiseaseHelper.contract(entity, weaponDisease, 1);
			}
		}

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}