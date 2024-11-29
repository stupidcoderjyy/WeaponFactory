package wf.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import wf.init.IWeapon;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Shadow @Final MinecraftClient client;

    /**
     * @author JYY
     * @reason 实现自定义武器攻击距离
     */
    @Overwrite
    public void updateTargetedEntity(float tickDelta) {
        Entity entity = client.getCameraEntity();
        if (entity != null) {
            if (client.world != null) {
                client.getProfiler().push("pick");
                client.targetedEntity = null;
                double d, d0 = client.interactionManager.getReachDistance();
                if (client.player != null && client.player.getMainHandStack().getItem() instanceof IWeapon w) {
                    d = w.getAttackDistance();
                } else {
                    d = d0;
                }
                client.crosshairTarget = entity.raycast(d, tickDelta, false);
                Vec3d vec3d = entity.getCameraPosVec(tickDelta);
                boolean bl = false;
                double e = d;
                if (client.interactionManager.hasExtendedReach()) {
                    e = 6.0;
                    d = e;
                } else if (d > 3){
                    bl = true;
                }

                e *= e;
                if (client.crosshairTarget != null) {
                    e = client.crosshairTarget.getPos().squaredDistanceTo(vec3d);
                }

                Vec3d vec3d2 = entity.getRotationVec(1.0F);
                Vec3d vec3d3 = vec3d.add(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d);
                Box box = entity.getBoundingBox().stretch(vec3d2.multiply(d)).expand(1.0, 1.0, 1.0);
                EntityHitResult entityHitResult = ProjectileUtil.raycast(entity, vec3d, vec3d3, box, (entityx) -> !entityx.isSpectator() && entityx.canHit(), e);
                if (entityHitResult != null) {
                    Entity entity2 = entityHitResult.getEntity();
                    Vec3d vec3d4 = entityHitResult.getPos();
                    double g = vec3d.squaredDistanceTo(vec3d4);
                    if (bl && g > 100) {
                        client.crosshairTarget = BlockHitResult.createMissed(vec3d4, Direction.getFacing(vec3d2.x, vec3d2.y, vec3d2.z), BlockPos.ofFloored(vec3d4));
                    } else if (g < e || client.crosshairTarget == null) {
                        client.crosshairTarget = entityHitResult;
                        if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrameEntity) {
                            client.targetedEntity = entity2;
                        }
                    }
                } else {
                    client.crosshairTarget = entity.raycast(d0, tickDelta, false);
                }
                client.getProfiler().pop();
            }
        }
    }
}
