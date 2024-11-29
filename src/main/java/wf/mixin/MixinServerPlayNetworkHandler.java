package wf.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {
    @Unique private static final double MAX_ATTACK_DISTANCE = MathHelper.square(10.0);

    @Redirect(method = "onPlayerInteractEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Box;squaredMagnitude(Lnet/minecraft/util/math/Vec3d;)D"))
    private double hookPlayerInteractEntity(Box b, Vec3d pos) {
        double d = Math.max(Math.max(b.minX - pos.x, pos.x - b.maxX), 0.0);
        double e = Math.max(Math.max(b.minY - pos.y, pos.y - b.maxY), 0.0);
        double f = Math.max(Math.max(b.minZ - pos.z, pos.z - b.maxZ), 0.0);
        double sm = MathHelper.squaredMagnitude(d, e, f);
        if (sm < MAX_ATTACK_DISTANCE) {
            return 0;  //通过判定
        }
        return sm;  //不通过
    }
}
