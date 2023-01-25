package artifacts.common.entity;

import artifacts.Artifacts;
import artifacts.common.ModConfig;
import artifacts.common.init.ModLootTables;
import artifacts.common.init.ModSoundEvents;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.scoreboard.Team;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Predicate;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EntityMimic extends EntityLiving implements IMob {

    public int ticksInAir;

    public boolean isDormant;

    public EntityMimic(World world) {
        super(world);
        moveHelper = new MimicMoveHelper(this);
        setSize(14/16F, 14/16F);
        experienceValue = 10;
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setRandomMoveRotation();
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public void setMoveRotation(float rotation, boolean aggressive) {
        ((MimicMoveHelper)moveHelper).setDirection(rotation, aggressive);
    }

    public void setRandomMoveRotation() {
        this.setMoveRotation(rand.nextInt(4) * 90, false);
    }

    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public boolean canDespawn() {
        return false;
    }

    // doesn't seem to have any effect
    @Override
    public boolean canBePushed() {
        return false;
    }

    // prevent mimic from being pushed
    @Override
    protected void collideWithEntity(Entity entity) { }

    // act as a solid block
    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.isEntityAlive() ? this.getEntityBoundingBox() : null;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new AIMimicFloat(this));
        this.tasks.addTask(2, new AIMimicAttack(this));
        this.tasks.addTask(3, new AIMimicDisguise(this));
        this.tasks.addTask(5, new AIMimicHop(this));
        this.targetTasks.addTask(1, new AIMimicFindNearestPlayer(this));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("ticksInAir", ticksInAir);
        compound.setBoolean("isDormant", isDormant);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        ticksInAir = compound.getInteger("ticksInAir");
        isDormant = compound.getBoolean("isDormant");
    }

    @Override
    public void onUpdate() {
        if (!world.isRemote && world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            isDead = true;
        }

        super.onUpdate();

        if (isInWater()) {
            ticksInAir = 0;
            if (isDormant) {
                isDormant = false;
            }
        } else if (!onGround) {
            ticksInAir++;
        } else {
            if (ticksInAir > 0) {
                playSound(getLandingSound(), getSoundVolume(), getSoundPitch());
                ticksInAir = 0;
            }
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if(!this.isDormant && this.getDistanceSq(player) < 1.2 && player.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue())) {
            applyEnchantments(this, player);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isProjectile() && ticksInAir <= 0) {
            playSound(ModSoundEvents.MIMIC_HURT, getSoundVolume(), getSoundPitch());
            return false;
        }
        if (super.attackEntityFrom(source, amount)) {
            if (source.getTrueSource() instanceof EntityPlayer) {
                setAttackTarget((EntityLivingBase) source.getTrueSource());
                isDormant = false;
            }
            return true;
        }
        return false;
    }

    @Override
    public float getEyeHeight() {
        return 0.7F;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.MIMIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.MIMIC_DEATH;
    }

    protected SoundEvent getJumpingSound() {
        return ModSoundEvents.MIMIC_OPEN;
    }

    protected SoundEvent getLandingSound() {
        return ModSoundEvents.MIMIC_CLOSE;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ModLootTables.MIMIC_UNDERGROUND;
    }

    @Override
    protected void jump() {
        this.motionY = 0.5;
        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
    }

    public void setDormant(boolean dormant) {
        this.isDormant = dormant;
    }

    public void setAwakeWithTarget(EntityPlayer player) {
        setAttackTarget(player);
        this.setDormant(false);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if(!player.isCreative()) {
            this.setAwakeWithTarget(player);
            player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Mod.EventBusSubscriber(modid = Artifacts.MODID)
    static class MimicEventHandler {

        @SubscribeEvent(priority = EventPriority.LOW)
        public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
            if(event.getUseBlock() == Event.Result.DENY || event.getWorld().isRemote || event.getEntityPlayer() == null || !ModConfig.general.replaceUnopenedChests) return;
            BlockPos pos = event.getPos();
            World world = event.getWorld();
            TileEntity tile = world.getTileEntity(pos);
            Block block = world.getBlockState(pos).getBlock();
            EntityPlayer player = event.getEntityPlayer();
            if(tile instanceof TileEntityChest && block instanceof BlockChest && !player.isSpectator()) {
                if(world.getBlockState(pos.up()).doesSideBlockChestOpening(world, pos.up(), EnumFacing.DOWN)) return;
                if(((ILootContainer)tile).getLootTable() != null && world.rand.nextFloat() <= ModConfig.general.mimicReplacementChance) {
                    event.setCanceled(true);
                    ((TileEntityChest) tile).fillWithLoot(player);
                    world.destroyBlock(pos, false);
                    EntityMimic mimic = new EntityMimic(world);
                    mimic.setPosition(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
                    mimic.enablePersistence();
                    mimic.setAwakeWithTarget(player);
                    world.spawnEntity(mimic);
                }
            }
        }

        @SubscribeEvent(priority = EventPriority.LOW)
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            if(event.getWorld().isRemote || event.getPlayer() == null || !ModConfig.general.replaceUnopenedChests) return;
            BlockPos pos = event.getPos();
            World world = event.getWorld();
            TileEntity tile = world.getTileEntity(pos);
            Block block = world.getBlockState(pos).getBlock();
            EntityPlayer player = event.getPlayer();
            if(tile instanceof TileEntityChest && block instanceof BlockChest && !player.isSpectator()) {
                if(((ILootContainer)tile).getLootTable() != null && world.rand.nextFloat() <= ModConfig.general.mimicReplacementChance) {
                    event.setCanceled(true);
                    ((TileEntityChest) tile).fillWithLoot(player);
                    world.destroyBlock(pos, false);
                    EntityMimic mimic = new EntityMimic(world);
                    mimic.setPosition(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
                    mimic.enablePersistence();
                    mimic.setAwakeWithTarget(player);
                    world.spawnEntity(mimic);
                }
            }
        }
    }
    static class AIMimicAttack extends EntityAIBase {

        private final EntityMimic mimic;
        private int executeTimeRemaining;

        public AIMimicAttack(EntityMimic mimic) {
            this.mimic = mimic;
            setMutexBits(2);
        }

        @Override
        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = mimic.getAttackTarget();

            if (entitylivingbase == null) {
                return false;
            } else if (!entitylivingbase.isEntityAlive()) {
                return false;
            } else {
                return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage;
            }
        }

        @Override
        public void startExecuting() {
            executeTimeRemaining = 300;
            super.startExecuting();
        }

        @Override
        public boolean shouldContinueExecuting() {
            EntityLivingBase entitylivingbase = mimic.getAttackTarget();

            if (entitylivingbase == null) {
                return false;
            } else if (!entitylivingbase.isEntityAlive()) {
                return false;
            } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
                return false;
            } else {
                return --this.executeTimeRemaining > 0;
            }
        }

        public void updateTask() {
            if (mimic.getAttackTarget() != null) {
                mimic.faceEntity(mimic.getAttackTarget(), 10, 10);
                ((MimicMoveHelper) mimic.getMoveHelper()).setDirection(mimic.rotationYaw, true);
            }
        }
    }

    static class AIMimicDisguise extends EntityAIBase {

        private final EntityMimic mimic;
        private int chosenDegrees;
        private int timeUntilNextFaceRandom;

        public AIMimicDisguise(EntityMimic mimic) {
            this.mimic = mimic;
            setMutexBits(2);
        }

        public boolean shouldExecute() {
            return mimic.getAttackTarget() == null && (mimic.onGround || mimic.isInWater() || mimic.isInLava() || mimic.getActivePotionEffect(MobEffects.LEVITATION) != null);
        }

        public void updateTask() {
            if(--timeUntilNextFaceRandom <= 0) {
                timeUntilNextFaceRandom = 120 + mimic.getRNG().nextInt(80);
                if(mimic.isDormant) {
                    chosenDegrees = Math.round(mimic.rotationYaw / 90) * 90;
                    mimic.setPosition(Math.floor(mimic.posX) + 0.5D, mimic.posY, Math.floor(mimic.posZ) + 0.5D);
                }
                else {
                    chosenDegrees = mimic.getRNG().nextInt(4) * 90;
                    if(mimic.onGround) mimic.setDormant(true);
                }
            }
            ((MimicMoveHelper) mimic.getMoveHelper()).setDirection(chosenDegrees, false);
        }
    }

    static class AIMimicFloat extends EntityAIBase {

        private final EntityMimic mimic;

        public AIMimicFloat(EntityMimic mimic) {
            this.mimic = mimic;
            setMutexBits(5);
            ((PathNavigateGround) mimic.getNavigator()).setCanSwim(true);
        }

        public boolean shouldExecute() {
            return mimic.isInWater() || mimic.isInLava();
        }

        public void updateTask() {
            if (mimic.getRNG().nextFloat() < 0.8F) {
                mimic.getJumpHelper().setJumping();
            }

            ((MimicMoveHelper) mimic.getMoveHelper()).setSpeed(1.5);
        }
    }

    static class AIMimicHop extends EntityAIBase {

        private final EntityMimic mimic;

        public AIMimicHop(EntityMimic mimic) {
            this.mimic = mimic;
            setMutexBits(5);
        }

        public boolean shouldExecute() {
            return !mimic.isDormant;
        }

        public void updateTask() {
            ((MimicMoveHelper) mimic.getMoveHelper()).setSpeed(1.25);
        }
    }

    static class AIMimicFindNearestPlayer extends EntityAIBase {

        private final EntityMimic mimic;
        private final Predicate<Entity> predicate;
        private final EntityAINearestAttackableTarget.Sorter sorter;
        private EntityLivingBase target;

        public AIMimicFindNearestPlayer(EntityMimic mimic) {
            this.mimic = mimic;

            this.predicate = target -> {
                if (!(target instanceof EntityPlayer)) {
                    return false;
                } else if (((EntityPlayer)target).capabilities.disableDamage) {
                    return false;
                } else {
                    return !((double) target.getDistance(AIMimicFindNearestPlayer.this.mimic) > AIMimicFindNearestPlayer.this.startTargetRange()) && EntityAITarget.isSuitableTarget(AIMimicFindNearestPlayer.this.mimic, (EntityLivingBase) target, false, true);
                }
            };
            sorter = new EntityAINearestAttackableTarget.Sorter(mimic);
        }

        public boolean shouldExecute() {
            List<EntityPlayer> list = mimic.world.getEntitiesWithinAABB(EntityPlayer.class, mimic.getEntityBoundingBox().grow(startTargetRange(), 4, startTargetRange()), this.predicate::test);
            list.sort(this.sorter);

            if (list.isEmpty()) {
                return false;
            } else {
                target = list.get(0);
                if (mimic.isDormant) {
                    mimic.isDormant = false;
                }
                return true;
            }
        }

        public boolean shouldContinueExecuting() {
            EntityLivingBase entitylivingbase = mimic.getAttackTarget();

            if (entitylivingbase == null) {
                return false;
            } else if (!entitylivingbase.isEntityAlive()) {
                return false;
            } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
                return false;
            } else {
                Team team = mimic.getTeam();
                Team team1 = entitylivingbase.getTeam();

                if (team != null && team1 == team) {
                    return false;
                } else {
                    double targetRange = maxTargetRange();

                    if (mimic.getDistanceSq(entitylivingbase) > targetRange * targetRange) {
                        return false;
                    } else {
                        return !(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).interactionManager.isCreative();
                    }
                }
            }
        }

        public void startExecuting() {
            mimic.setAttackTarget(target);
            super.startExecuting();
        }

        public void resetTask() {
            mimic.setAttackTarget(null);
            super.startExecuting();
        }

        protected double maxTargetRange() {
            return mimic.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        }

        protected double startTargetRange() {
            return 2;
        }
    }

    static class MimicMoveHelper extends EntityMoveHelper {

        private float rotationDegrees;
        private int jumpDelay;
        private final EntityMimic mimic;
        private boolean isAggressive;

        public MimicMoveHelper(EntityMimic mimic) {
            super(mimic);
            this.mimic = mimic;
            rotationDegrees = 180 * mimic.rotationYaw / (float) Math.PI;
            jumpDelay = mimic.rand.nextInt(320) + 640;
        }

        public void setDirection(float rotation, boolean isAggressive) {
            this.rotationDegrees = rotation;
            this.isAggressive = isAggressive;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
            action = Action.MOVE_TO;
        }

        public void onUpdateMoveHelper() {
            entity.rotationYaw = limitAngle(entity.rotationYaw, rotationDegrees, 90);
            entity.rotationYawHead = entity.rotationYaw;
            entity.renderYawOffset = entity.rotationYaw;

            if (action != Action.MOVE_TO) {
                entity.setMoveForward(0.0F);
            } else {
                action = Action.WAIT;

                if (entity.onGround) {
                    entity.setAIMoveSpeed((float) (speed * entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                    if (jumpDelay-- > 0) {
                        mimic.moveStrafing = 0;
                        mimic.moveForward = 0;
                        entity.setAIMoveSpeed(0);

                        if (isAggressive && jumpDelay > 20) {
                            jumpDelay = mimic.rand.nextInt(10) + 10;
                        }
                    } else {
                        jumpDelay = mimic.rand.nextInt(320) + 640;

                        mimic.getJumpHelper().setJumping();
                        mimic.playSound(mimic.getJumpingSound(), mimic.getSoundVolume(), mimic.getSoundPitch());
                    }

                } else {
                    entity.setAIMoveSpeed((float) (speed * entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                }
            }
        }
    }
}
